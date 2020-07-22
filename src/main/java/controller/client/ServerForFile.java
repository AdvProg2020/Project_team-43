package controller.client;

import model.Seller;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerForFile {
    private ServerSocket serverSocket;
    private String IP;
    private int port;
    private Thread thread;
    private Seller seller;
    private HashMap<String, String> filesIdToAddress = new HashMap<>();

    public ServerForFile(Seller seller, String token, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        try {
            if (seller.getFilesId().size() > 0) {
                this.seller = seller;
                this.serverSocket = new ServerSocket(0);
                this.port = serverSocket.getLocalPort();
                InetAddress inetAddress = InetAddress.getLocalHost();
                this.IP = inetAddress.getHostAddress();
                dataOutputStream.writeUTF("addFileServer " + IP + " " + port + " " + token);
                dataOutputStream.flush();
                dataInputStream.readUTF();
                run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                                socket, seller);

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
        private Socket socket;
        private Seller seller;

        public PeerHandler(DataInputStream dataInputStream, DataOutputStream dataOutputStream, Socket socket, Seller seller) {
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            this.socket = socket;
            this.seller = seller;
            run();
        }

        private void run() {
            try {
                String fileId = dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
