package Controller.console;

import View.console.App;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        App.getInstance().open();
        new Server().run();

    }
    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8585);
        while (true) {
            Socket socket = serverSocket.accept();
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            new ClientHandler(dataInputStream, dataOutputStream, socket, this).start();
        }

    }
}
