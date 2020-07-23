package controller.server;

import javafx.util.Pair;
import model.*;
import model.request.Request;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientHandler extends Thread {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private ServerImp server;
    private String username;

    public ClientHandler(DataInputStream dataInputStream, DataOutputStream dataOutputStream, Socket socket, ServerImp server) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.socket = socket;
        this.server = server;
        username = "null";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void run() {
        handleClient();
    }

    public void handleClient() {
        try {
            while (true) {
                String command = dataInputStream.readUTF();
                System.out.println(command);
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
                } else if (command.startsWith("createManagerProfile")) {
                    createManagerProfile(command);
                } else if (command.startsWith("createSupporterProfile")) {
                    createSupporterProfile(command);
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
                } else if (command.startsWith("createCategory")) {
                    createCategory(command);
                } else if (command.startsWith("removeCategory")) {
                    removeCategory(command);
                } else if (command.startsWith("removeProduct")) {
                    removeProduct(command);
                } else if (command.startsWith("changeFeature")) {
                    changeFeature(command);
                } else if (command.startsWith("removeFeature")) {
                    removeFeature(command);
                } else if (command.startsWith("editProduct")) {
                    editProduct(command.split(" "));
                } else if (command.startsWith("editOff")) {
                    editOff(command.split(" "));
                } else if (command.startsWith("addOff")) {
                    addOff(command.split(" "));
                } else if (command.startsWith("useCode")) {
                    useCodedDiscount(command);
                } else if (command.startsWith("purchase")) {
                    purchase(command);
                } else if (command.startsWith("setOnline")) {
                    setOnline(command);
                } else if (command.startsWith("getOnlineSupporters")) {
                    getOnlineSupporters();
                } else if (command.startsWith("sendMessage")) {
                    sendMessage(command);
                } else if (command.startsWith("endInputStream")) {
                    endInputStream();
                } else if (command.startsWith("addFileSeller")) {
                    addFileSeller(command);
                } else if (command.startsWith("removeFileSeller")) {
                    removeFileSeller(command);
                } else if (command.startsWith("fuckMe")) {
                    updateMe(command);
                } else if (command.startsWith("addFileServer")) {
                    addFileServer(command);
                } else if (command.startsWith("serverOfFileEnd")) {
                    endServerOfFile(command);
                } else if (command.startsWith("getIPAndPort")) {
                    getIPAndPort(command);
                } else if (command.startsWith("getOnlineUsers")) {
                    getOnlineUsers(command);
                }
                else {
                    System.out.println("What the fuck command");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void getOnlineUsers(String command) {
        String token = command.split(" ")[1];
        sendObject(server.getOnlineUsers());
    }

    private void getIPAndPort(String command) {
        try {
            String fileId = command.split(" ")[1];
            String token = command.split(" ")[2];
            String IP = server.getIP(fileId, token);
            int port;
            if (!(IP.equals("server is not ready") || IP.equals("invalid token"))) {
                port = server.getPort(fileId);
                dataOutputStream.writeUTF(IP + " " + port);
            } else {
                dataOutputStream.writeUTF("server is not ready");
            }
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void endServerOfFile(String command) {
        String token = command.split(" ")[1];
        server.endServerOfFiles(token);
        try {
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addFileServer(String command) {
        String[] commands = command.split(" ");
        String IP = commands[1];
        int port = Integer.parseInt(commands[2]);
        String token = commands[3];
        server.addFileServer(IP, port, token);
        try {
            dataOutputStream.writeUTF("files added successfully");
            dataOutputStream.flush();
            sendObject(server.getFilesAddresses(token));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateMe(String command) {
        String token = command.split(" ")[1];
        sendObject(server.getUser(token));
    }

    private void removeFileSeller(String command) {
        Pattern pattern = (Pattern.compile("(\\S+) (\\S+) (.+)"));
        Matcher matcher = pattern.matcher(command);
        matcher.find();
        server.removeFileSeller(matcher.group(2), matcher.group(3));
    }

    private void addFileSeller(String command) {
        HashMap<String, String> features = (HashMap<String, String>) getObject();
        Pattern pattern = (Pattern.compile("(\\S+) (\\S+) (\\S+) (\\S+) (\\S+) (\\S+) (.+)"));
        Matcher matcher = pattern.matcher(command);
        matcher.find();
        server.addFileSeller(matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5), matcher.group(6), matcher.group((7)), features);
    }

    private void endInputStream() {
        try {
            dataOutputStream.writeUTF("fuck1");
            dataOutputStream.flush();
            dataOutputStream.writeUTF("fuck2");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String command) {
        Pattern pattern = (Pattern.compile("(\\S+) (\\S+) (\\S+) (.+)"));
        Matcher matcher = pattern.matcher(command);
        matcher.find();
        String username = matcher.group(2);
        String token = matcher.group(3);
        String message = matcher.group(4);
        server.acknowledgeSupporter(username, token, message);
    }

    private void getOnlineSupporters() {
        sendObject(server.getOnlineSupporters());
    }

    private void setOnline(String command) {
        server.setOnline(command.split(" ")[1]);
    }

    private void addOff(String[] commands) {
        ArrayList<String> productIds = (ArrayList<String>) getObject();
        server.addOff(commands[1], commands[2], commands[3], commands[4], productIds);
    }

    private void editOff(String[] commands) {
        server.editOff(commands[1], commands[2], commands[3], commands[4]);
    }

    private void editProduct(String[] commands) {
        server.editProduct(commands[1], commands[2], commands[3], commands[4]);
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

    private void purchase(String command) {
        String[] commands = command.split(" ");
        String address = commands[1];
        String phoneNumber = commands[2];
        String discount = commands[3];
        String token = commands[4];
        try {
            dataOutputStream.writeUTF("ready for get cart");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<Pair<Product, Seller>, Integer> newBuyerCart = (HashMap<Pair<Product, Seller>, Integer>) getObject();
        boolean result = server.purchase(address, phoneNumber, discount, token, newBuyerCart);
        try {
            if (result)
                dataOutputStream.writeUTF("done");
            else
                dataOutputStream.writeUTF("fail");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void useCodedDiscount(String command) {
        String discountCode = command.split(" ")[1];
        String token = command.split(" ")[2];
        server.useCodedDiscount(discountCode, token);
    }

    private void withdraw(String command) {
        String[] commands = command.split(" ");
        String amount = commands[1];
        String accountId = commands[2];
        String token = commands[3];
        String result = server.withdraw(amount, accountId, token);
        try {
            dataOutputStream.writeUTF(result);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


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
        username = "null";
        server.logout(command.split(" ")[1]);
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
            setUsername(username);
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
        String result;
        String[] commands = command.split(" ");
        result = server.register(commands[1], commands[2], commands[3], commands[4], commands[5], commands[6], commands[7]);
        dataOutputStream.writeUTF(result);
        dataOutputStream.flush();

    }

    private void update(String command) throws IOException {
        String result;
        String[] commands = command.split(" ");
        String firstName = commands[1];
        String lastName = commands[2];
        String email = commands[3];
        String phoneNumber = commands[4];
        String password = commands[5];
        String token = commands[6];
        server.updateUser(firstName, lastName, email, phoneNumber, password, token);
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

        try {
            dataOutputStream.writeUTF(server.createManagerProfile(managerInfo, token));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSupporterProfile(String command) {
        String username = command.split(" ")[1];
        String firstName = command.split(" ")[2];
        String lastName = command.split(" ")[3];
        String email = command.split(" ")[4];
        String phone = command.split(" ")[5];
        String password = command.split(" ")[6];
        String token = command.split(" ")[7];
        ArrayList<String> supporterInfo = new ArrayList<>();
        supporterInfo.add(username);
        supporterInfo.add(firstName);
        supporterInfo.add(lastName);
        supporterInfo.add(email);
        supporterInfo.add(phone);
        supporterInfo.add(password);
        try {
            dataOutputStream.writeUTF(server.createSupporterProfile(supporterInfo, token));
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

    private void createCategory(String command) {
        try {
            dataOutputStream.writeUTF("ready to get");
            dataOutputStream.flush();
            ArrayList<String> categoryInfo = (ArrayList<String>) getObject();
            String categoryName = categoryInfo.remove(categoryInfo.size() - 1);
            String token = command.split(" ")[1];
            server.createCategory(categoryName, categoryInfo, token);
            dataOutputStream.writeUTF("createCategory done");
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
            dataOutputStream.writeUTF(server.changeFeature(category, command.split("----")[2], command.split("----")[3], command.split("----")[4]));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String removeFeature(String command) {
        Category category = Category.getCategoryByName(command.split(" ")[1]);
        server.removeFeature(category, command.split(" ")[2], command.split(" ")[3]);
        try {
            dataOutputStream.writeUTF("removeFeature done");
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "done";
    }


    public void acknowledgeChat(User user, String message) {
        try {
            if (User.getUserByUserName(username) instanceof Supporter) {
                checkUserInSupporter(user, message);
            } else {
                checkSupporterInUser((Supporter) user, message);
            }
            dataOutputStream.writeUTF(user.getUsername() + " " + message);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkUserInSupporter(User user, String message) {
        Supporter supporter = (Supporter) (User.getUserByUserName(username));
        if (!supporter.getUsers().containsKey(user.getUsername())) {
            ArrayList<String> messages = new ArrayList<>();
            messages.add(user.getUsername() + " : " + message);
            supporter.getUsers().put(user.getUsername(), messages);
        } else {
            supporter.getUsers().get(user.getUsername()).add(user.getUsername() + " : " + message);
        }
    }

    public void checkSupporterInUser(Supporter supporter, String message) {
        if (!supporter.getUsers().containsKey(username)) {
            ArrayList<String> messages = new ArrayList<>();
            messages.add(supporter.getUsername() + " : " + message);
            supporter.getUsers().put(username, messages);
        } else {
            supporter.getUsers().get(username).add(supporter.getUsername() + " : " + message);
        }
    }
}
