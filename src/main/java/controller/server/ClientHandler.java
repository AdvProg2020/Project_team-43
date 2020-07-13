package controller.server;

import controller.client.BuyerProcessor;
import javafx.util.Pair;
import model.*;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler extends Thread {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private ServerImp server;
    private HashMap<Pair<Product, Seller>, Integer> newBuyerCart = new HashMap<>();

    public ClientHandler(DataInputStream dataInputStream, DataOutputStream dataOutputStream, Socket socket, ServerImp server) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        handleClient();
    }

    public void handleClient() {
        try {
            while (true) {
                String command = dataInputStream.readUTF();
                if (command.startsWith("login")) {
                    login(command.split(" ")[1], command.split(" ")[2]);
                } else if (command.startsWith("register")) {
                    register(command);
                } else if (command.startsWith("update")) {
                    update(command);
                } else if (command.startsWith("logout")) {
                    logout(command);
                } else if (command.equals("getAllProducts")) {
                    getAllProducts();
                } else if (command.equals("getAllOffs")) {
                    getAllOffs();
                } else if (command.equals("getAllCategories")) {
                    getAllCategories();
                }
                System.out.println(command);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void getAllCategories() {
        sendObject(server.getAllCategories());
    }

    private void getAllOffs() {
        sendObject(server.getAllOffs());
    }

    private void getAllProducts() {
        sendObject(server.getAllProducts());
    }

    private void logout(String command) {
        server.logout(command.split(" ")[1]);
        newBuyerCart.clear();
    }

    private void login(String username, String password) throws IOException {
        String result = server.login(username, password);
        dataOutputStream.writeUTF(result);
        dataOutputStream.flush();
        if (checkResultForLogin(result)) {
            if (User.getUserByUserName(username).getUserType() == UserType.BUYER) {
                ((Buyer) User.getUserByUserName(username)).setNewBuyerCart(newBuyerCart);
            }
            sendObject(User.getUserByUserName(username));
        }
    }

    private void sendObject(Object object) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(object);
            oos.close();
            byte[] rawData = buffer.toByteArray();
            dataOutputStream.write(rawData);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkResultForLogin(String result) {
        if (result.equals("incorrect password"))
            return false;
        return !result.equals("there is no user with this username");

    }

    private void register(String command) throws IOException {
        String result = null;
        String[] commands = command.split(" ");
        result = server.register(commands[1], commands[2], commands[3], commands[4], commands[5], commands[6], commands[7]);
        dataOutputStream.writeUTF(result);
        dataOutputStream.flush();

    }

    private void update(String command) throws IOException {
        String result = null;
        String[] commands = command.split(" ");
        BuyerProcessor.getInstance().editField(commands[1], commands[2], commands[3], commands[4], commands[5]);
        dataOutputStream.writeUTF("done");
        dataOutputStream.flush();

    }


}
