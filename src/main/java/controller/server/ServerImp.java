package controller.server;

import com.sun.org.apache.bcel.internal.classfile.Code;
import controller.client.BuyerProcessor;
import model.*;
import model.request.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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


    public ArrayList<Product> getAllProducts() {
        return Product.getAllProductsInList();
    }

    public ArrayList<Request> getAllRequests() {
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

    public void addExistingProduct(String id, String amount, String token) {
        ((Seller) users.get(token)).addExistingProduct(id, Integer.parseInt(amount));
    }

    public void addNewProduct(String name, String companyName, String categoryName, String priceString, String number, String token, HashMap<String, String> features) {
        ((Seller)users.get(token)).addNewProduct(name, Company.getCompanyByName(companyName), Double.parseDouble(priceString), Category.getCategoryByName(categoryName), Integer.parseInt(number), features);
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