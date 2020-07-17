package View;


import controller.client.BuyerProcessor;
import controller.client.Processor;

import model.*;
import model.request.Request;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Client {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String token;

    public void run() {
        try {
            Socket socket = new Socket("127.0.0.1", 2222);
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
            }
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
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

    public String updateUser(String firstName, String lastName, String email, String phoneNumber, String password) {

        try {
            dataOutputStream.writeUTF("update " + firstName + " " + lastName + " " + email + " " + phoneNumber + " " + password);
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

    /*private void sendObject(Object object) {
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
    }*/

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

    public String createSupporterProfile(String userName, String firstName, String lastName, String email, String phone, String password){
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

    public void createCategory(String categoryName, ArrayList<String> features) {
        try {
            checkTokenValidation(Processor.user);
            features.add(categoryName);
            dataOutputStream.writeUTF("createCategory");
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
            if (user.getBalance() < Double.parseDouble(amount)) {
                checkTokenValidation(user);
                dataOutputStream.writeUTF("withdraw " + amount + " " + accountId + " " + token);
                dataOutputStream.flush();
                String result = dataInputStream.readUTF();
                if (result.equals("done successfully")) {
                    user.setBalance(user.getBalance() - Integer.parseInt(amount));
                }
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

    public void purchaseWithCredit(User user, String address, String phoneNumber, double discount) {
        try {
            checkTokenValidation(user);
            dataOutputStream.writeUTF("purchase " + address + " " + phoneNumber + " " + discount + " " + token);
            dataOutputStream.flush();
            sendObject(((Buyer) user).getNewBuyerCart());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
