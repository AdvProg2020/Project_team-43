package controller.client;


import View.GraphicController.BuyerMenuController;
import View.GraphicController.SupporterMenuController;

import javafx.scene.layout.VBox;
import javafx.util.Pair;
import model.*;
import model.request.Request;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String token;
    private Thread thread;
    private Socket socket;
    private ServerForFile serverForFile;

    public void run() {
        try {
            socket = new Socket("2.tcp.ngrok.io", 17113);
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String login(String username, String password) {
        try {
            dataOutputStream.writeUTF("login" + " " + username + " " + password);
            dataOutputStream.flush();
            String result = dataInputStream.readUTF();
            System.out.println(result);
            if (checkResultForLogin(result)) {
                token = result;
                User user = (User) getObject();
                Processor.setUser(user);
                Processor.setIsLogin(true);
                if (user.getUserType() == UserType.BUYER) {
                    BuyerProcessor.getInstance().setNewBuyerCart();
                }
                if (user.getUserType() == UserType.SELLER) {
                    addFileServer((Seller) user);
                }
            }
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void addFileServer(Seller seller) {
        checkTokenValidation(seller);
        serverForFile = new ServerForFile(seller, token, dataOutputStream, dataInputStream);
    }

    private boolean checkResultForLogin(String result) {
        if (result.equals("incorrect password"))
            return false;
        return !result.equals("there is no user with this username");

    }

    public String register(String firstName, String lastName, String email, String phone, String password, String username, String companyName) throws IOException {
        dataOutputStream.writeUTF("register" + " " + firstName + " " + lastName + " " + email + " " + phone + " " + password + " " + username + " " + companyName);
        dataOutputStream.flush();
        return dataInputStream.readUTF();
    }

    public String updateUser(String firstName, String lastName, String email, String phoneNumber, String password, User user) {

        try {
            checkTokenValidation(user);
            dataOutputStream.writeUTF("update " + firstName + " " + lastName + " " + email + " " + phoneNumber + " " + password + " " + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "connection failed";
    }

    public void logout() {
        try {
            dataOutputStream.writeUTF("logout " + token);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addExistingProduct(String id, String amount, Seller user) {
        try {
            checkTokenValidation(user);
            dataOutputStream.writeUTF("addExistingProduct " + id + " " + amount + " " + token);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewProduct(User user, String name, String companyName, String categoryName, String priceString, String number, HashMap<String, String> features) {
        try {
            checkTokenValidation(user);
            dataOutputStream.writeUTF("addNewProduct " + name + " " + companyName + " " + categoryName + " " + priceString + " " + number + " " + token);
            dataOutputStream.flush();
            sendObject(features);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addOff(Seller user, String startTime, String endTime, double amount, ArrayList<String> productIds) {
        try {
            checkTokenValidation(user);
            dataOutputStream.writeUTF("addOff " + startTime + " " + endTime + " " + amount + " " + token);
            dataOutputStream.flush();
            sendObject(productIds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editProduct(Seller user, String productId, String field, String newField) {
        try {
            checkTokenValidation(user);
            dataOutputStream.writeUTF("editProduct " + productId + " " + field + " " + newField + " " + token);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editOff(Seller user, String offId, String field, String newField) {
        try {
            checkTokenValidation(user);
            dataOutputStream.writeUTF("editOff " + offId + " " + field + " " + newField + " " + token);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendFuckObject(Object object) {
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

    public ArrayList<Product> getAllProducts() {
        try {
            dataOutputStream.writeUTF("getAllProducts");
            dataOutputStream.flush();
            ArrayList<Product> allProducts = (ArrayList<Product>) getObject();
            return allProducts;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Off> getOffs() {
        try {
            dataOutputStream.writeUTF("getAllOffs");
            dataOutputStream.flush();
            ArrayList<Off> allOffs = (ArrayList<Off>) getObject();
            return allOffs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Category> getAllCategories() {
        try {
            dataOutputStream.writeUTF("getAllCategories");
            dataOutputStream.flush();
            ArrayList<Category> allCategories = (ArrayList<Category>) getObject();
            return allCategories;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<User> getAllUsers() {
        try {
            dataOutputStream.writeUTF("getAllUsers");
            dataOutputStream.flush();
            ArrayList<User> allUsers = (ArrayList<User>) getObject();
            return allUsers;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Request> getAllRequests() {
        try {
            dataOutputStream.writeUTF("getAllRequests");
            dataOutputStream.flush();
            ArrayList<Request> allRequests = (ArrayList<Request>) getObject();
            return allRequests;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<CodedDiscount> getAllCodedDiscounts() {
        try {
            dataOutputStream.writeUTF("getAllCodedDiscounts");
            dataOutputStream.flush();
            ArrayList<CodedDiscount> allCodedDiscounts = (ArrayList<CodedDiscount>) getObject();
            return allCodedDiscounts;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Company> getAllCompanies() {
        try {
            dataOutputStream.writeUTF("getAllCompanies");
            dataOutputStream.flush();
            ArrayList<Company> allCompanies = (ArrayList<Company>) getObject();
            return allCompanies;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Object getFuckObject() {
        try {
            byte[] bytes = new byte[30000];
            dataInputStream.read(bytes);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();
        } catch (IOException e) {
            System.out.println("done");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addComment(String commentText, boolean isBuy, Product product, User user) {
        try {
            checkTokenValidation(user);
            dataOutputStream.writeUTF("comment" + "----" + token + "----" + commentText + "----" + isBuy + "----" + product.getProductId());
            dataOutputStream.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkTokenValidation(User user) {
        try {
            dataOutputStream.writeUTF("checkValid " + token);
            dataOutputStream.flush();
            String result = dataInputStream.readUTF();
            if (result.equals("expired")) {
                setNewToken(user.getUsername(), user.getUserPersonalInfo().getPassword());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setNewToken(String username, String password) {
        try {
            dataOutputStream.writeUTF("get_token " + username + " " + password);
            dataOutputStream.flush();
            String result = dataInputStream.readUTF();
            if (!result.equals("invalid info")) {
                token = result;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public String createManagerProfile(String userName, String firstName, String lastName, String email, String phone, String password) {
        try {
            checkTokenValidation(Processor.user);
            dataOutputStream.writeUTF("createManagerProfile" + " " + userName + " " + firstName + " " + lastName + " " + email + " " + phone + " " + password + " " + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "done";
    }

    public String createSupporterProfile(String userName, String firstName, String lastName, String email, String phone, String password) {
        try {
            checkTokenValidation(Processor.user);
            dataOutputStream.writeUTF("createSupporterProfile" + " " + userName + " " + firstName + " " + lastName + " " + email + " " + phone + " " + password + " " + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "done";
    }

    public String acceptRequest(String requestId) {
        try {
            checkTokenValidation(Processor.user);
            dataOutputStream.writeUTF("acceptRequest" + " " + requestId + " " + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "done";

    }

    public void declineRequest(String requestId) {
        try {
            checkTokenValidation(Processor.user);
            dataOutputStream.writeUTF("declineRequest" + " " + requestId + " " + token);
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String userName) {
        try {
            checkTokenValidation(Processor.user);
            dataOutputStream.writeUTF("deleteUser" + " " + userName + " " + token);
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editCategory(String categoryName, String newName) {
        try {
            dataOutputStream.writeUTF("editCategory" + " " + categoryName + " " + newName + " " + token);
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String addCategoryFeature(String categoryName, String featureName) {
        try {
            checkTokenValidation(Processor.user);
            dataOutputStream.writeUTF("addCategoryFeature" + " " + categoryName + " " + featureName + " " + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "done";
    }

    public void createCategory(User user, String categoryName, ArrayList<String> features) {
        checkTokenValidation(user);
        try {
            checkTokenValidation(Processor.user);
            features.add(categoryName);
            dataOutputStream.writeUTF("createCategory " + token);
            dataOutputStream.flush();
            dataInputStream.readUTF();
            sendObject(features);
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editCodedDiscount(String code, String startDate, String endDate, String discountAmount, String repeat) {
        try {
            checkTokenValidation(Processor.user);
            dataOutputStream.writeUTF("editCodedDiscount" + "----" + code + "----" + startDate + "----" + endDate + "----" + discountAmount + "----" + repeat + "----" + token);
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeCodedDiscount(String code) {
        try {
            checkTokenValidation(Processor.user);
            dataOutputStream.writeUTF("removeCodedDiscount" + " " + code + " " + token);
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createCodedDiscount(String startDate, String endDate, String amount, String repeat) {
        try {
            checkTokenValidation(Processor.user);
            dataOutputStream.writeUTF("createCodedDiscount" + " " + startDate + " " + endDate + " " + amount + " " + repeat + " " + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "done";
    }

    public void removeCategory(String categoryName) {
        try {
            checkTokenValidation(Processor.user);
            dataOutputStream.writeUTF("removeCategory" + " " + categoryName + " " + token);
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeProduct(String productId) {
        try {
            checkTokenValidation(Processor.user);
            dataOutputStream.writeUTF("removeProduct" + " " + productId + " " + token);
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String changeFeature(String categoryName, String oldFeature, String newFeature) {
        try {
            checkTokenValidation(Processor.user);
            dataOutputStream.writeUTF("changeFeature" + "----" + categoryName + "----" + oldFeature + "----" + newFeature + "----" + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "done";
    }

    public void removeFeature(String categoryName, String feature) {
        try {
            checkTokenValidation(Processor.user);
            dataOutputStream.writeUTF("removeFeature" + " " + categoryName + " " + feature + " " + token);
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void rateProduct(Product product, int rating, User user) {
        try {
            checkTokenValidation(user);
            dataOutputStream.writeUTF("rate" + " " + product.getProductId() + " " + rating + " " + token);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String chargeAccount(String bankUsername, String bankPassword, String amount, String accountId, User user) {
        try {
            checkTokenValidation(user);
            dataOutputStream.writeUTF("charge " + bankUsername + " " + bankPassword + " " + amount + " " + accountId + " " + token);
            dataOutputStream.flush();
            String result = dataInputStream.readUTF();
            if (result.equals("done successfully"))
                user.setBalance(user.getBalance() + Integer.parseInt(amount));
            System.out.println(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String withDraw(String amount, String accountId, User user) {
        try {
            if (user.getBalance() > Double.parseDouble(amount)) {
                checkTokenValidation(user);
                dataOutputStream.writeUTF("withdraw " + amount + " " + accountId + " " + token);
                dataOutputStream.flush();
                String result = dataInputStream.readUTF();
                if (result.equals("done successfully")) {
                    user.setBalance(user.getBalance() - Integer.parseInt(amount));
                }
                return result;
            }
            return "not enough money in wallet";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void useDiscountCode(User user, String codedDiscount) {
        try {
            checkTokenValidation(user);
            dataOutputStream.writeUTF("useCode " + codedDiscount + " " + token);
            dataOutputStream.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String purchaseWithCredit(User user, String address, String phoneNumber, double discount) {
        try {
            checkTokenValidation(user);
            dataOutputStream.writeUTF("purchase " + address + " " + phoneNumber + " " + discount + " " + token);
            dataOutputStream.flush();
            System.out.println(dataInputStream.readUTF());
            HashMap<Pair<String, String>, Integer> buyerCart = new HashMap<>();
            for (Pair<Product, Seller> pair : ((Buyer) user).getNewBuyerCart().keySet()) {
                buyerCart.put(new Pair<>(pair.getKey().getProductId(), pair.getValue().getUsername()), ((Buyer) user).getNewBuyerCart().get(pair));
            }
            sendObject(buyerCart);
            return dataInputStream.readUTF();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public void setUserOnline(User user) {
        try {
            checkTokenValidation(user);
            dataOutputStream.writeUTF("setOnline " + token);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getAllOnlineSupporters(Buyer user) {
        checkTokenValidation(user);
        try {
            dataOutputStream.writeUTF("getOnlineSupporters " + token);
            dataOutputStream.flush();
            return (ArrayList<String>) getObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendMessage(User user, String userName, String message) {
        checkTokenValidation(user);
        try {
            dataOutputStream.writeUTF("sendMessage " + userName + " " + token + " " + message);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acknowledge(SupporterMenuController supporterMenuController, VBox vBox) {
        thread = new Thread(() -> {
            while (true) {
                try {
                    String command = dataInputStream.readUTF();
                    if (command.startsWith("fuck")) {
                        break;
                    }
                    String username = command.split(" ")[0];
                    String message = command.substring(command.indexOf(" ") + 1);

                    supporterMenuController.updateUsersListView();
                    if (((supporterMenuController.selectedUser != null && supporterMenuController.selectedUser.getUsername().equals(username)) || supporterMenuController.users.isEmpty())) {
                        supporterMenuController.updateChatRoom(username, message, vBox);
                    }
                } catch (Exception e) {
                    System.out.println("done");
                }
            }
        });
        thread.setName("fuck");
        thread.start();
    }

    public void acknowledge(BuyerMenuController buyerMenuController, VBox vBox) {
        thread = new Thread(() -> {
            while (true) {
                try {
                    String command = dataInputStream.readUTF();
                    if (command.startsWith("fuck")) {
                        break;
                    }
                    String username = command.split(" ")[0];
                    String message = command.substring(command.indexOf(" ") + 1);
                    buyerMenuController.updateChatRoom(username, message, vBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setName("fuck");
        thread.start();
    }

    public void fuckThread() {
        if (thread != null && thread.isAlive()) {
            thread.stop();
            thread = null;
        }
    }

    public boolean threadIsNull() {
        return thread == null;
    }

    public void fuck2Thread() {
        if (thread != null && thread.isAlive()) {
            thread = null;
            try {
                dataOutputStream.writeUTF("endInputStream");
                dataOutputStream.flush();
                System.out.println(dataInputStream.readUTF());
                System.out.println("hello");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void removeFile(User user, String absolutePath) {
        checkTokenValidation(user);
        try {
            dataOutputStream.writeUTF("removeFileSeller " + token + " " + absolutePath);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadFile(Buyer user, String fileId) {
        checkTokenValidation(user);
        try {
            dataOutputStream.writeUTF("getIPAndPort " + fileId + " " + token);
            dataOutputStream.flush();
            String result = dataInputStream.readUTF();
            if (result.equals("server is not ready")) {
                return result;
            }
            String IP = result.split(" ")[0];
            int port = Integer.parseInt(result.split(" ")[1]);
            connectToSeller(port, IP, fileId);
            return "your download ends";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void connectToSeller(int port, String IP, String fileId) {
        try {
            Socket socket = new Socket(IP, port);
            DataOutputStream ds = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream di = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            ds.writeUTF(fileId);
            ds.flush();
            String input = di.readUTF();
            Pattern pattern = Pattern.compile("(\\d+) (.+)");
            Matcher matcher = pattern.matcher(input);
            matcher.matches();
            long fileSize = Long.parseLong(matcher.group(1));
            String fileName = matcher.group(2);
            saveFile(socket, fileSize, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveFile(Socket clientSock, long fileSize, String fileName) throws IOException {
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        FileOutputStream fos = new FileOutputStream("src/downloads/" + fileName);
        byte[] buffer = new byte[4096];
        int read = 0;
        int totalRead = 0;
        long remaining = fileSize;
        while ((read = dis.read(buffer, 0, (int) java.lang.Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            System.out.println("read " + totalRead + " bytes.");
            fos.write(buffer, 0, read);
        }
        fos.close();
        dis.close();
    }

    public User updateMe(User user) {
        checkTokenValidation(user);
        try {
            dataOutputStream.writeUTF("fuckMe " + token);
            dataOutputStream.flush();
            User getUser = (User) getObject();
            System.out.println(getUser.getUsername());
            return getUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addFile(User user, String name, String company, String category, String price, HashMap<String, String> features, File file) {
        checkTokenValidation(user);
        try {
            dataOutputStream.writeUTF("addFileSeller " + name + " " + company + " " + category + " " + price + " " + token + " " + file.getAbsolutePath());
            dataOutputStream.flush();
            sendObject(features);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void killServerOfFile(User user) {
        if (user.getUserType() == UserType.SELLER) {
            checkTokenValidation(user);
            serverForFile.killThread();
            try {
                dataOutputStream.writeUTF("serverOfFileEnd " + token);
                dataOutputStream.flush();
                dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getOnlineUsers(User user) {
        checkTokenValidation(user);
        try {
            dataOutputStream.writeUTF("getOnlineUsers " + token);
            dataOutputStream.flush();
            return (ArrayList<String>) getObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("server nadad.");
        return null;
    }

    public void changeWageAndMinBalance(String wage, String minBalance) {
        try {
            dataOutputStream.writeUTF("changeWage " + wage + " " + minBalance + " " + token);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object getObject() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(dataInputStream);
            Object object = objectInputStream.readObject();
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    private void sendObject(Object object) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(dataOutputStream);
            objectOutputStream.writeObject(object);
            System.out.println("end write");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

