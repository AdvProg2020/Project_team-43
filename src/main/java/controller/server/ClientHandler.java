package controller.server;

import controller.client.BuyerProcessor;
import javafx.util.Pair;
import model.*;
import model.request.Request;

import javax.print.DocFlavor;
import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
                } else if (command.matches("getAllRequests")) {
                    getAllRequests();
                } else if (command.matches("getAllCodedDiscounts")) {
                    getAllCodedDiscounts();
                } else if (command.startsWith("charge")) {
                    chargeAccount(command);
                } else if (command.startsWith("withdraw")) {
                    withdraw(command);
                } else if (command.startsWith("createManagerProfile")) {
                    createManagerProfile(command);
                } else if (command.startsWith("acceptRequest")) {
                    acceptRequest(command);
                } else if (command.startsWith("declineRequest")) {
                    declineRequest(command);
                } else if (command.startsWith("deleteUser")) {
                    deleteUser(command);
                } else if (command.startsWith("editCategory")) {
                    editCategory(command);
                } else if (command.startsWith("addCategoryFeature")) {
                    addCategoryFeature(command);
                } else if (command.startsWith("createCodedDiscount")) {
                    createCodedDiscount(command);
                } else if (command.startsWith("editCodedDiscount")) {
                    editCodedDiscount(command);
                } else if (command.startsWith("removeCodedDiscount")) {
                    removeCodedDiscount(command);
                } else if (command.startsWith("removeCategory")) {
                    removeCategory(command);
                } else if (command.startsWith("removeProduct")) {
                    removeProduct(command);
                } else if(command.startsWith("changeFeature")){
                    changeFeature(command);
                } else if(command.startsWith("removeFeature")){
                    removeFeature(command);
                }
                System.out.println(command);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

    private void createManagerProfile(String command) {
        String username = command.split(" ")[1];
        String firstName = command.split(" ")[2];
        String lastName = command.split(" ")[3];
        String email = command.split(" ")[4];
        String phone = command.split(" ")[5];
        String password = command.split(" ")[6];
        String token = command.split(" ")[7];
        ArrayList<String> managerInfo = new ArrayList<>();
        managerInfo.add(username);
        managerInfo.add(firstName);
        managerInfo.add(lastName);
        managerInfo.add(email);
        managerInfo.add(phone);
        managerInfo.add(password);
        server.createManagerProfile(managerInfo, token);
        try {
            dataOutputStream.writeUTF("createManagerProfile done");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptRequest(String command) {
        Request request = Request.getRequestById(command.split(" ")[1]);
        try {
            dataOutputStream.writeUTF(server.acceptRequest(request, command.split(" ")[2]));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void declineRequest(String command) {
        Request request = Request.getRequestById(command.split(" ")[1]);
        server.declineRequest(request, command.split(" ")[2]);
        try {
            dataOutputStream.writeUTF("declineRequest done");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUser(String command) {
        User user = User.getUserByUserName(command.split(" ")[1]);
        server.deleteUser(user, command.split(" ")[2]);
        try {
            dataOutputStream.writeUTF("deleteUser done");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void editCategory(String command) {
        Category category = Category.getCategoryByName(command.split(" ")[1]);
        String newName = command.split(" ")[2];
        server.editCategory(category, newName, command.split(" ")[3]);
        try {
            dataOutputStream.writeUTF("editCategory done");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCategoryFeature(String command) {
        Category category = Category.getCategoryByName(command.split(" ")[1]);
        String feature = command.split(" ")[2];
        server.addCategoryFeature(category, feature, command.split(" ")[3]);
        try {
            dataOutputStream.writeUTF(server.addCategoryFeature(category, feature, command.split(" ")[3]));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createCodedDiscount(String command) {
        String startDate = command.split(" ")[1];
        String endDate = command.split(" ")[2];
        String amount = command.split(" ")[3];
        String repeat = command.split(" ")[4];
        ArrayList<String> codedDiscountInfo = new ArrayList<>();
        codedDiscountInfo.add(startDate);
        codedDiscountInfo.add(endDate);
        codedDiscountInfo.add(amount);
        codedDiscountInfo.add(repeat);
        try {
            dataOutputStream.writeUTF(server.createCodedDiscount(codedDiscountInfo, command.split(" ")[5]));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editCodedDiscount(String command) {
        CodedDiscount codedDiscount = CodedDiscount.getDiscountById(command.split("----")[1]);
        String startDate = command.split("----")[2];
        String endDate = command.split("----")[3];
        String amount = command.split("----")[4];
        String repeat = command.split("----")[5];
        try {
            Date theStartDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(startDate);
            Date theEndDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(endDate);
            server.editCodedDiscount(codedDiscount, theStartDate, theEndDate, amount, repeat, command.split("----")[6]);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            dataOutputStream.writeUTF("editCodedDiscount done");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeCodedDiscount(String command) {
        CodedDiscount code = CodedDiscount.getDiscountById(command.split(" ")[1]);
        server.removeCodedDiscount(code, command.split(" ")[2]);
        try {
            dataOutputStream.writeUTF("removeCodedDiscount done");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeCategory(String command) {
        Category category = Category.getCategoryByName(command.split(" ")[1]);
        server.removeCategory(category, command.split(" ")[2]);
        try {
            dataOutputStream.writeUTF("removeCodedDiscount done");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeProduct(String command) {
        Product product = Product.getAllProductById(command.split(" ")[1]);
        server.removeProduct(product, command.split(" ")[2]);
        try {
            dataOutputStream.writeUTF("removeProduct done");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeFeature(String command) {
        Category category = Category.getCategoryByName(command.split("----")[1]);
        try {
            dataOutputStream.writeUTF(server.changeFeature(category, command.split("----")[2], command.split("----")[3], command.split("----")[4] ));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String removeFeature(String command) {
        Category category = Category.getCategoryByName(command.split(" ")[1]);
        server.removeFeature(category, command.split(" ")[2] ,command.split(" ")[3]);
        try {
            dataOutputStream.writeUTF("removeFeature done");
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "done";
    }
}
