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

    public ServerForFile(Seller seller, DataOutputStream dataOutputStream) {
        try {
            this.serverSocket = new ServerSocket(0);
            this.port = serverSocket.getLocalPort();
            InetAddress inetAddress = InetAddress.getLocalHost();
            this.IP = inetAddress.getHostAddress();
            dataOutputStream.writeUTF("addFileServer " + IP + " " + port + " " +);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

    }


    static class PeerHandler {

    }

}
