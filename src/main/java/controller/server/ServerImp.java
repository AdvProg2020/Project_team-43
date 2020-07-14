package controller.server;

import controller.client.BuyerProcessor;
import model.*;
import model.request.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;


public class ServerImp {
    private HashMap<String, User> users = new HashMap<>();
    private ServerProcessor serverProcessor = new ServerProcessor();
    private final String shopAccountId = "";//TODO
    public static final int PORT = 2020;
    public static final String IP = "127.0.0.1";
    private final String shopAccountUsername = "";//TODO
    private final String shopAccountPassword = "";//TODO

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


    public ArrayList<Product> getAllProducts() {
        return Product.getAllProductsInList();
    }

    public ArrayList<Request> getAllRequests() {
        System.out.println("salam");
        return Request.getAllRequests();
    }

    public ArrayList<Off> getAllOffs() {
        return Off.getAcceptedOffs();
    }

    public ArrayList<Category> getAllCategories() {
        return Category.getAllCategories();
    }

    public ArrayList<Company> getAllCompanies() {
        return Company.getAllCompanies();
    }

    public void addComment(String token, String commentText, String isBuy, String productId) {
        Product.getProductById(productId).addComment(new Comment(Product.getProductById(productId),
                commentText, isBuy.equalsIgnoreCase("true"), (Buyer) users.get(token)));
    }

    public String checkToken(String token) {
        if (users.containsKey(token))
            return "valid";
        return "expired";
    }

    public String setToken(String username, String password) {
        String result = serverProcessor.checkUsernamePassword(username, password);
        if (result.equals("valid")) {
            String token = serverProcessor.createToken();
            users.put(token, User.getUserByUserName(username));
            new ExpireToken(this, token).start();
            return token;
        }
        return "invalid info";
    }

    public void rateProduct(String productId, int rating, String token) {
        Product.getProductById(productId).rateProduct(rating, users.get(token));
    }


    public ArrayList<User> getAllUsers() {
        return User.getAllUsers();
    }

    public ArrayList<CodedDiscount> getAllCodedDiscounts() {
        return CodedDiscount.getAllCodedDiscount();
    }

    public synchronized String chargeAccount(String bankUsername, String bankPassword, String amount, String accountId, String token) {
        try {
            Socket socket = new Socket(IP, PORT);
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dos.writeUTF("get_token " + bankUsername + " " + bankPassword);
            String bankToken = dis.readUTF();
            dos.writeUTF("create_receipt" + " " + bankToken + " " + "move" + " " + amount + " " + accountId + " " + shopAccountId);
            String payID = dis.readUTF();
            dos.writeUTF("pay" + " " + payID);
            String result = dis.readUTF();
            if (result.equals("done successfully")) {
                serverProcessor.chargeUser(amount, users.get(token));
            }
            socket.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized String withdraw(String amount, String accountId, String token) {
        try {
            Socket socket = new Socket(IP, PORT);
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dos.writeUTF("get_token " + shopAccountUsername + " " + shopAccountPassword);
            String bankToken = dis.readUTF();
            dos.writeUTF("create_receipt" + " " + bankToken + " " + "move" + " " + amount + " " + shopAccountId + " " + accountId);
            String payID = dis.readUTF();
            dos.writeUTF("pay" + " " + payID);
            String result = dis.readUTF();
            if (result.equals("done successfully")) {
                serverProcessor.withdraw(amount, users.get(token));
            }
            socket.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createManagerProfile(ArrayList<String> managerInfo, String token) {
//        todo check token
        User user = users.get(token);
        ((Manager) user).createManagerProfile(managerInfo);
    }

    public String acceptRequest(Request request, String token) {
        User user = users.get(token);
        try {
            ((Manager) user).acceptRequest(request);
            return "done";
        } catch (InvalidCommandException e) {
            return "invalidCommandException";
        } catch (ParseException e) {
            return "dateException";
        }
    }

    public void declineRequest(Request request, String token) {
        User user = users.get(token);
        ((Manager) user).declineRequest(request);
    }

    public void deleteUser(User user1, String token){
        User user = users.get(token);
        ((Manager) user).deleteUser(user1);
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
            Thread.sleep(300_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        serverImp.logout(token);
    }
}