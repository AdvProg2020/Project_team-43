package controller.client;


import View.GraphicController.BuyerMenuController;
import View.GraphicController.SupporterMenuController;

import controller.server.MyCipher;
import javafx.scene.layout.VBox;
import model.*;
import model.request.Request;

import javax.crypto.Cipher;
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
    private String key;

    public void run() {
        try {
            socket = new Socket("localhost", 9999);
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("giveFirstKey");
            dataOutputStream.flush();
            key = dataInputStream.readUTF();
            System.out.println(2 + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeKey(){
        try {
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("giveNewKey", key));
            dataOutputStream.flush();
            key = MyCipher.getInstance().decryptMessage(dataInputStream.readUTF(), key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String login(String username, String password) {
        try {
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("login" + " " + username + " " + password, key));
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
        changeKey();
        dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("login" + " " + username + " " + password, key));
        dataOutputStream.flush();
        dataOutputStream.writeUTF("register" + " " + firstName + " " + lastName + " " + email + " " + phone + " " + password + " " + username + " " + companyName);
        return dataInputStream.readUTF();
    }

    public String updateUser(String firstName, String lastName, String email, String phoneNumber, String password, User user) {

        try {
            checkTokenValidation(user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("update " + firstName + " " + lastName + " " + email + " " + phoneNumber + " " + password + " " + token,key));
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "connection failed";
    }

    public void logout() {
        try {
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("logout " + token,key));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addExistingProduct(String id, String amount, Seller user) {
        try {
            checkTokenValidation(user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("addExistingProduct " + id + " " + amount + " " + token,key));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewProduct(User user, String name, String companyName, String categoryName, String priceString, String number, HashMap<String, String> features) {
        try {
            checkTokenValidation(user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("addNewProduct " + name + " " + companyName + " " + categoryName + " " + priceString + " " + number + " " + token,key));
            dataOutputStream.flush();
            sendObject(features);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addOff(Seller user, String startTime, String endTime, double amount, ArrayList<String> productIds) {
        try {
            checkTokenValidation(user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("addOff " + startTime + " " + endTime + " " + amount + " " + token,key));
            dataOutputStream.flush();
            sendObject(productIds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editProduct(Seller user, String productId, String field, String newField) {
        try {
            checkTokenValidation(user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("editProduct " + productId + " " + field + " " + newField + " " + token,key));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editOff(Seller user, String offId, String field, String newField) {
        try {
            checkTokenValidation(user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("editOff " + offId + " " + field + " " + newField + " " + token,key));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
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

    public ArrayList<Product> getAllProducts() {
        try {
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("getAllProducts",key));
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("getAllOffs",key));
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("getAllCategories",key));
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("getAllUsers",key));
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
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("getAllRequests",key));
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("getAllCodedDiscounts",key));
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("getAllCompanies",key));
            dataOutputStream.flush();
            ArrayList<Company> allCompanies = (ArrayList<Company>) getObject();
            return allCompanies;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Object getObject() {
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("comment" + "----" + token + "----" + commentText + "----" + isBuy + "----" + product.getProductId(),key));
            dataOutputStream.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkTokenValidation(User user) {
        try {
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("checkValid " + token,key));
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("get_token " + username + " " + password,key));
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("createManagerProfile" + " " + userName + " " + firstName + " " + lastName + " " + email + " " + phone + " " + password + " " + token,key));
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("createSupporterProfile" + " " + userName + " " + firstName + " " + lastName + " " + email + " " + phone + " " + password + " " + token,key));
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("acceptRequest" + " " + requestId + " " + token,key));
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("declineRequest" + " " + requestId + " " + token,key));
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String userName) {
        try {
            checkTokenValidation(Processor.user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("deleteUser" + " " + userName + " " + token,key));
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editCategory(String categoryName, String newName) {
        try {
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("editCategory" + " " + categoryName + " " + newName + " " + token,key));
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String addCategoryFeature(String categoryName, String featureName) {
        try {
            checkTokenValidation(Processor.user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("addCategoryFeature" + " " + categoryName + " " + featureName + " " + token,key));
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "done";
    }

    public void createCategory(String categoryName, ArrayList<String> features) {
        try {
            checkTokenValidation(Processor.user);
            features.add(categoryName);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("createCategory",key));
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("editCodedDiscount" + "----" + code + "----" + startDate + "----" + endDate + "----" + discountAmount + "----" + repeat + "----" + token,key));
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeCodedDiscount(String code) {
        try {
            checkTokenValidation(Processor.user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("removeCodedDiscount" + " " + code + " " + token,key));
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createCodedDiscount(String startDate, String endDate, String amount, String repeat) {
        try {
            checkTokenValidation(Processor.user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("createCodedDiscount" + " " + startDate + " " + endDate + " " + amount + " " + repeat + " " + token,key));
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("removeCategory" + " " + categoryName + " " + token, key));
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeProduct(String productId) {
        try {
            checkTokenValidation(Processor.user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("removeProduct" + " " + productId + " " + token,key));
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String changeFeature(String categoryName, String oldFeature, String newFeature) {
        try {
            checkTokenValidation(Processor.user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("changeFeature" + "----" + categoryName + "----" + oldFeature + "----" + newFeature + "----" + token,key));
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("removeFeature" + " " + categoryName + " " + feature + " " + token,key));
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void rateProduct(Product product, int rating, User user) {
        try {
            checkTokenValidation(user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("rate" + " " + product.getProductId() + " " + rating + " " + token,key));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String chargeAccount(String bankUsername, String bankPassword, String amount, String accountId, User user) {
        try {
            checkTokenValidation(user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("charge " + bankUsername + " " + bankPassword + " " + amount + " " + accountId + " " + token,key));
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
                changeKey();
                dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("withdraw " + amount + " " + accountId + " " + token,key));
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("useCode " + codedDiscount + " " + token,key));
            dataOutputStream.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String purchaseWithCredit(User user, String address, String phoneNumber, double discount) {
        try {
            checkTokenValidation(user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("purchase " + address + " " + phoneNumber + " " + discount + " " + token,key));
            dataOutputStream.flush();
            dataInputStream.readUTF();
            sendObject(((Buyer) user).getNewBuyerCart());
            return dataInputStream.readUTF();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public void setUserOnline(User user) {
        try {
            checkTokenValidation(user);
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("setOnline " + token,key));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getAllOnlineSupporters(Buyer user) {
        checkTokenValidation(user);
        try {
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("getOnlineSupporters " + token,key));
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
            thread.stop();
            thread = null;
            try {
                dataOutputStream.writeUTF("endInputStream");
                dataOutputStream.flush();
                System.out.println(dataInputStream.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void removeFile(User user, String absolutePath) {
        checkTokenValidation(user);
        try {
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("removeFileSeller " + token + " " + absolutePath,key));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadFile(Buyer user, String fileId) {
        checkTokenValidation(user);
        try {
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("getIPAndPort " + fileId + " " + token,key));
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
            changeKey();
            dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("addFileSeller " + name + " " + company + " " + category + " " + price + " " + token + " " + file.getAbsolutePath(),key));
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
                changeKey();
                dataOutputStream.writeUTF(MyCipher.getInstance().encryptMessage("serverOfFileEnd " + token,key));
                dataOutputStream.flush();
                dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

