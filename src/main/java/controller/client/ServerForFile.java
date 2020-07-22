package controller.client;

import model.FileProduct;
import model.Seller;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerForFile {
    private ServerSocket serverSocket;
    private Thread thread;
    private Seller seller;
    private HashMap<String, String> filesIdToAddress = new HashMap<>();

    public ServerForFile(Seller seller, String token, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        try {
            if (seller.getFilesId().size() > 0) {
                this.seller = seller;
                this.serverSocket = new ServerSocket(0);
                int port = serverSocket.getLocalPort();
                InetAddress inetAddress = InetAddress.getLocalHost();
                String IP = inetAddress.getHostAddress();
                dataOutputStream.writeUTF("addFileServer " + IP + " " + port + " " + token);
                dataOutputStream.flush();
                dataInputStream.readUTF();
                filesIdToAddress = (HashMap<String, String>) getObject(dataInputStream);
                run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object getObject(DataInputStream dataInputStream) {
        try {
            byte[] bytes = new byte[30000];
            dataInputStream.read(bytes);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();
        } catch (IOException e) {
            System.out.println("done");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void run() {
        thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();
                        new PeerHandler(new DataInputStream(new BufferedInputStream(socket.getInputStream())),
                                new DataOutputStream(new BufferedOutputStream(socket.getOutputStream())),
                                filesIdToAddress);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    public void killThread() {
        if (thread != null)
            thread.stop();
    }


    static class PeerHandler {
        private DataInputStream dataInputStream;
        private DataOutputStream dataOutputStream;
        private HashMap<String, String> filesIdToAddress;

        public PeerHandler(DataInputStream dataInputStream, DataOutputStream dataOutputStream, HashMap<String, String> filesIdToAddress) {
            this.dataInputStream = dataInputStream;
            this.filesIdToAddress = filesIdToAddress;
            this.dataOutputStream = dataOutputStream;
            run();
        }

        private void run() {
            try {
                String fileId = dataInputStream.readUTF();
                String address = filesIdToAddress.get(fileId);
                File file = new File(address);
                sendFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private void sendFile(File file) {
            try {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(buffer);
                oos.writeObject(file);
                oos.close();
                byte[] rawData = buffer.toByteArray();
                dataOutputStream.write(rawData);
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
