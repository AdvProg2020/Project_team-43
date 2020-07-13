package controller.server;

import controller.client.BuyerProcessor;
import model.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class ServerImp {
    private HashMap<String, User> users = new HashMap<>();
    private ServerProcessor serverProcessor = new ServerProcessor();

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

    public String login(String username, String password) {
        String result = serverProcessor.login(username, password);
        if (result.equals("logged in successful")) {
            String token = serverProcessor.createToken();
            users.put(token, User.getUserByUserName(username));
            new ExpireToken(this, token).start();
            return token;
        }
        return result;
    }

    public void logout(String token) {
        if (users.containsKey(token)) {
            users.remove(token);
        }
    }

    public synchronized String register(String firstName, String lastName, String email, String phone, String password, String username, String companyName) {
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName, lastName, email
                , phone, password);
        return BuyerProcessor.getInstance().register(userPersonalInfo, username, companyName);
    }


}

class ExpireToken extends Thread {
    private ServerImp serverImp;
    private String token;

    public ExpireToken(ServerImp serverImp, String token) {
        this.token = token;
        this.serverImp = serverImp;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        serverImp.logout(token);
    }
}