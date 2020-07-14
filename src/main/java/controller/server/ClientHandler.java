package controller.server;

import controller.client.BuyerProcessor;
import javafx.util.Pair;
import model.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientHandler extends Thread {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private ServerImp server;
    //private HashMap<Pair<Product, Seller>, Integer> newBuyerCart = new HashMap<>();

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
                } else if (command.startsWith("comment")) {
                    comment(command);
                } else if (command.startsWith("checkValid")) {
                    checkValidationOfToken(command.split(" ")[1]);
                } else if (command.startsWith("get_token")) {
                    setToken(command.split(" ")[1], command.split(" ")[2]);
                } else if (command.startsWith("rate")) {
                    rateProduct(command);
                } else if (command.equals("getAllCompanies")) {
                    getAllCompanies();
                } else if (command.equals("getAllUsers")) {
                    getAllUsers();
                } else if (command.equals("getAllRequests")) {
                    getAllRequests();
                } else if (command.equals("getAllCodedDiscounts")) {
                    getAllCodedDiscounts();
                } else if (command.startsWith("addExistingProduct")) {
                    addExistingProduct(command.split(" "));
                } else if (command.startsWith("addNewProduct")) {
                    addNewProduct(command.split(" "));
                } else if (command.startsWith("charge")) {
                    chargeAccount(command);
                } else if (command.startsWith("withdraw")) {
                    withdraw(command);
                }
                System.out.println(command);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addNewProduct(String[] commands) {
        HashMap<String, String> features = (HashMap<String, String>) getObject();
        server.addNewProduct(commands[1], commands[2], commands[3], commands[4], commands[5], commands[6], features);
    }

    private void addExistingProduct(String[] commands) {
        String id = commands[1];
        String amount = commands[2];
        String token = commands[3];
        server.addExistingProduct(id, amount, token);
    }

    private void withdraw(String command) {
        String[] commands = command.split(" ");
        String amount = commands[1];
        String accountId = commands[2];
        String token = commands[3];
        String result = server.withdraw(amount, accountId, token);

    }

    private void chargeAccount(String command) {
        String commands[] = command.split(" ");
        String bankUsername = commands[1];
        String bankPassword = commands[2];
        String amount = commands[3];
        String accountId = commands[4];
        String token = commands[5];
        String result = server.chargeAccount(bankUsername, bankPassword, amount, accountId, token);
        try {
            dataOutputStream.writeUTF(result);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setToken(String username, String password) {
        String token = server.setToken(username, password);
        try {
            dataOutputStream.writeUTF(token);
            dataOutputStream.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void rateProduct(String command) {
        String[] commands = command.split(" ");
        String productId = commands[1];
        int rating = Integer.parseInt(commands[2]);
        String token = commands[3];
        server.rateProduct(productId, rating, token);

    }

    private void checkValidationOfToken(String token) {
        try {
            dataOutputStream.writeUTF(server.checkToken(token));
            dataOutputStream.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void getAllCategories() {
        sendObject(server.getAllCategories());
    }

    private void getAllUsers() {
        sendObject(server.getAllUsers());
    }

    private void getAllCompanies() {
        sendObject(server.getAllCompanies());
    }

    private void getAllCodedDiscounts() {
        sendObject(server.getAllCodedDiscounts());
    }

    private void getAllOffs() {
        sendObject(server.getAllOffs());
    }

    private void getAllProducts() {
        sendObject(server.getAllProducts());
    }

    private void getAllRequests() {
        sendObject(server.getAllRequests());
    }

    private void logout(String command) {
        server.logout(command.split(" ")[1]);
        //newBuyerCart.clear();
    }

    private void comment(String command) {
        String[] commands = command.split("----");
        String token = commands[1];
        String commentText = commands[2];
        String isBuy = commands[3];
        String productId = commands[4];
        server.addComment(token, commentText, isBuy, productId);
    }

    private void login(String username, String password) throws IOException {
        String result = server.login(username, password);
        dataOutputStream.writeUTF(result);
        dataOutputStream.flush();
        if (checkResultForLogin(result)) {
            if (User.getUserByUserName(username).getUserType() == UserType.BUYER) {
                //((Buyer) User.getUserByUserName(username)).setNewBuyerCart(newBuyerCart);
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

    private Object getObject() {
        try {
            byte[] bytes = new byte[30000];
            dataInputStream.read(bytes);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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
