package Controller.console;

import model.*;
import model.request.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;

public class ServerImp {
    BossProcessor bossProcessor = BossProcessor.getInstance();
    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(2020);
        ServerImp server = new ServerImp();
        while (true) {
            Socket socket = serverSocket.accept();
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            new ClientHandler(dataInputStream, dataOutputStream, socket, server).start();
            System.out.println("connected");
        }
    }

    public synchronized String register(String firstName, String lastName, String email, String phone, String password, String username, String companyName) {
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName, lastName, email
                , phone, password);
        return BuyerProcessor.getInstance().register(userPersonalInfo, username, companyName);
    }

}
