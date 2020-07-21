package controller.client;

import model.Seller;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerForFile {
    private ServerSocket serverSocket;
    private String IP;
    private int port;

    public ServerForFile(Seller seller, String token, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        try {
            if (seller.getFilesId().size() > 0) {
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
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    static class PeerHandler {

    }

}
