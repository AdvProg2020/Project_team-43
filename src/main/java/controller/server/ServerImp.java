package controller.server;

import controller.client.BuyerProcessor;
import javafx.util.Pair;
import model.*;
import model.request.Request;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;


public class ServerImp {
    private ArrayList<ClientHandler> allClientsThreads = new ArrayList<>();
    private HashMap<String, User> users = new HashMap<>();
    private HashMap<String, Pair<String, Integer>> filesIPAndPort = new HashMap<>();
    private ServerProcessor serverProcessor = new ServerProcessor();
    private final String shopAccountId = "10001";//TODO
    public static final int PORT = 6666;
    private static int wage;
    private static int minimumBalance;

    public static int getWage() {
        return wage;
    }

    public static int getMinimumBalance() {
        return minimumBalance;
    }

    public static final String IP = "127.0.0.1";
    private final String shopAccountUsername = "a";//TODO
    private final String shopAccountPassword = "a";//TODO

    public ServerImp() {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/java/controller/server/info.txt");
            Scanner scanner = new Scanner(fileInputStream);
            wage = scanner.nextInt();
            minimumBalance = scanner.nextInt();
            scanner.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(6667);
        System.out.println("server is ready");
        System.out.println("waiting for clients ...");
        ServerImp server = this;
        while (true) {
            Socket socket = serverSocket.accept();
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            ClientHandler clientHandler = new ClientHandler(dataInputStream, dataOutputStream, socket, server);
            allClientsThreads.add(clientHandler);
            clientHandler.start();
            System.out.println("connected");
        }
    }

    public User getUser(String token) {
        return users.get(token);
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
            if (users.get(token) instanceof Supporter) {
                ((Supporter) users.get(token)).setOnline(false);
            }
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
            dos.flush();
            String bankToken = dis.readUTF();
            System.out.println("bankToken" + bankToken);
            dos.writeUTF("create_receipt" + " " + bankToken + " " + "move" + " " + amount + " " + accountId + " " + shopAccountId);
            dos.flush();
            String payID = dis.readUTF();
            System.out.println("payId" + payID);
            dos.writeUTF("pay" + " " + payID);
            dos.flush();
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
            if (users.get(token).getBalance() - Integer.parseInt(amount) > minimumBalance) {
                Socket socket = new Socket(IP, PORT);
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                dos.writeUTF("get_token " + shopAccountUsername + " " + shopAccountPassword);
                dos.flush();
                String bankToken = dis.readUTF();
                dos.writeUTF("create_receipt" + " " + bankToken + " " + "move" + " " + amount + " " + shopAccountId + " " + accountId);
                dos.flush();
                String payID = dis.readUTF();
                dos.writeUTF("pay" + " " + payID);
                dos.flush();
                String result = dis.readUTF();
                if (result.equals("done successfully")) {
                    serverProcessor.withdraw(amount, users.get(token));
                }
                socket.close();
                return result;
            }
            return "minimum amount is not enough";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized String createManagerProfile(ArrayList<String> managerInfo, String token) {
        User user = users.get(token);
        try {
            ((Manager) user).createManagerProfile(managerInfo);
        } catch (InvalidCommandException e) {
            return e.getMessage();
        }

        return "done";
    }

    public synchronized String createSupporterProfile(ArrayList<String> supporterInfo, String token) {
        User user = users.get(token);
        try {
            ((Manager) user).createSupporterProfile(supporterInfo);
        } catch (InvalidCommandException e) {
            return e.getMessage();
        }

        return "done";
    }

    public String acceptRequest(Request request, String token) {
        User user = users.get(token);
        synchronized (request) {
            try {
                ((Manager) user).acceptRequest(request);
                return "done";
            } catch (InvalidCommandException e) {
                return "invalidCommandException";
            } catch (ParseException e) {
                return "dateException";
            }
        }
    }

    public void declineRequest(Request request, String token) {
        User user = users.get(token);
        synchronized (request) {
            ((Manager) user).declineRequest(request);
        }
    }

    public synchronized void deleteUser(User user1, String token) {
        User user = users.get(token);
        synchronized (user1) {
            ((Manager) user).deleteUser(user1);
        }
    }

    public void editCategory(Category category, String name, String token) {
        User user = users.get(token);
        synchronized (category) {
            ((Manager) user).editCategoryName(category, name);
        }
    }

    public String addCategoryFeature(Category category, String featureName, String token) {
        User user = users.get(token);
        synchronized (category) {
            try {
                ((Manager) user).addCategoryFeature(category, featureName);
                return "done";
            } catch (InvalidCommandException e) {
                return "invalidCommandException";
            }
        }
    }

    public synchronized String createCodedDiscount(ArrayList<String> codedDiscountInfo, String token) {
        User user = users.get(token);
        try {
            ((Manager) user).createDiscountCoded(codedDiscountInfo);
            return "done";
        } catch (ParseException e) {
            return "dateException";
        }
    }

    public void editCodedDiscount(CodedDiscount code, Date startDate, Date endDate, String amount, String repeat, String token) {
        User user = users.get(token);
        synchronized (code) {
            code.setStartTime(startDate);
            code.setEndTime(endDate);
            code.setDiscountAmount(amount);
            code.setRepeat(repeat);
        }
    }

    public synchronized void removeCodedDiscount(CodedDiscount code, String token) {
        User user = users.get(token);
        synchronized (code) {
            ((Manager) user).removeCodedDiscount(code);
        }
    }

    public synchronized void createCategory(String categoryName, ArrayList<String> categoryInfo, String token) {
        User user = users.get(token);
        ((Manager) user).addCategory(categoryName, categoryInfo);
    }

    public synchronized void removeCategory(Category category, String token) {
        User user = users.get(token);
        synchronized (category) {
            ((Manager) user).removeCategory(category);
        }
    }

    public synchronized void removeProduct(Product product, String token) {
        User user = users.get(token);
        synchronized (product) {
            ((Manager) user).removeProduct(product);
        }
    }

    public String changeFeature(Category category, String oldFeature, String newFeature, String token) {
        User user = users.get(token);
        synchronized (category) {
            try {
                ((Manager) user).editFeatureName(category, oldFeature, newFeature);
                return "changeFeature done";
            } catch (InvalidCommandException e) {
                return "invalidCommandException";
            }
        }
    }

    public void removeFeature(Category category, String feature, String token) {
        User user = users.get(token);
        synchronized (category) {
            ((Manager) user).deleteFeature(category, feature);
        }
    }

    public synchronized void addExistingProduct(String id, String amount, String token) {
        ((Seller) users.get(token)).addExistingProduct(id, Integer.parseInt(amount));
    }

    public synchronized void addNewProduct(String name, String companyName, String categoryName, String priceString, String number, String token, HashMap<String, String> features) {
        ((Seller) users.get(token)).addNewProduct(name, Company.getCompanyByName(companyName), Double.parseDouble(priceString), Category.getCategoryByName(categoryName), Integer.parseInt(number), features);
    }

    public synchronized void editProduct(String id, String field, String newField, String token) {
        Seller seller = (Seller) users.get(token);
        Product product = seller.getProductById(id);
        synchronized (product) {
            seller.editProduct(product, field, newField);
        }
    }

    public void editOff(String id, String field, String newField, String token) {
        Seller seller = (Seller) users.get(token);
        Off off = seller.getOffById(id);
        synchronized (off) {
            seller.editOff(off, field, newField);
        }
    }

    public synchronized void addOff(String startTime, String endTime, String amount, String token, ArrayList<String> productIds) {
        try {
            Seller seller = (Seller) users.get(token);
            Date startTimeDate = new SimpleDateFormat("dd/MM/yyyy").parse(startTime);
            Date endTimeDate = new SimpleDateFormat("dd/MM/yyyy").parse(endTime);
            Double discountAmount = Double.parseDouble(amount);
            seller.addOff(startTimeDate, endTimeDate, discountAmount, productIds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void useCodedDiscount(String discountCode, String token) {
        CodedDiscount codedDiscount = CodedDiscount.getDiscountById(discountCode);
        synchronized (codedDiscount) {
            ((Buyer) users.get(token)).changeRemainDiscount(codedDiscount);
        }
    }

    public boolean purchase(String address, String phoneNumber, String discount, String token, HashMap<Pair<String, String>, Integer> newBuyerCartString) {
        HashMap<Pair<Product, Seller>, Integer> newBuyerCart = new HashMap<>();
        for (Pair<String, String> pair : newBuyerCartString.keySet()) {
            newBuyerCart.put(new Pair<>(Product.getProductById(pair.getKey()), (Seller) User.getUserByUserName(pair.getValue())), newBuyerCartString.get(pair));
        }
        ((Buyer) users.get(token)).setNewBuyerCart(newBuyerCart);
        return ((Buyer) users.get(token)).purchase(Double.parseDouble(discount), address, phoneNumber);
    }

    public void updateUser(String firstName, String lastName, String email, String phoneNumber, String password, String token) {
        serverProcessor.updateUser(firstName, lastName, email, phoneNumber, password, users.get(token));
    }

    public void setOnline(String token) {
        Supporter supporter = (Supporter) users.get(token);
        supporter.setOnline(!supporter.isOnline());
    }

    public Object getOnlineSupporters() {
        ArrayList<String> userNames = new ArrayList<>();
        for (User user : User.getAllUsers()) {
            if (user.getUserType() == UserType.SUPPORTER && ((Supporter) user).isOnline()) {
                userNames.add(user.getUsername());
            }
        }
        return userNames;
    }

    public void acknowledgeSupporter(String username, String token, String message) {
        User user = users.get(token);
        for (ClientHandler clientsThread : allClientsThreads) {
            if (clientsThread.getUsername().equals(username)) {
                clientsThread.acknowledgeChat(user, message);

            }
        }
    }

    public void removeFileSeller(String token, String id) {
        Seller seller = (Seller) users.get(token);
        seller.removeFile(id);
    }

    public void addFileSeller(String name, String company, String category, String price, String token, String path, HashMap<String, String> features) {
        ((Seller) users.get(token)).addFile(name, Integer.parseInt(price), FilenameUtils.getExtension(path), path, company, category, features);
    }

    public void addFileServer(String ip, int port, String token) {
        if (!users.containsKey(token)) {
            return;
        }
        Seller seller = (Seller) users.get(token);
        for (String fileId : seller.getFilesId()) {
            filesIPAndPort.put(fileId, new Pair<>(ip, port));
        }
    }

    public void endServerOfFiles(String token) {
        Seller seller = (Seller) users.get(token);
        for (String fileId : seller.getFilesId()) {
            filesIPAndPort.remove(fileId);
        }
    }

    public String getIP(String fileId, String token) {
        if (!users.containsKey(token)) {
            return "invalid token";
        }
        if (!filesIPAndPort.containsKey(fileId)) {
            return "server is not ready";
        }
        return filesIPAndPort.get(fileId).getKey();
    }

    public int getPort(String fileId) {
        return filesIPAndPort.get(fileId).getValue();

    }

    public HashMap<String, String> getFilesAddresses(String token) {
        if (users.containsKey(token)) {
            HashMap<String, String> filesAddresses = new HashMap<>();
            for (FileProduct file : ((Seller) users.get(token)).getFiles()) {
                filesAddresses.put(file.getProductId(), file.getAddress());
            }
            return filesAddresses;
        }
        return null;
    }

    public synchronized void changeWage(int wage, int minBalance, String token) {
        if (users.containsKey(token)) {
            ServerImp.wage = wage;
            try {
                ServerImp.minimumBalance = minBalance;
                System.out.println(wage + " " + minBalance);
                FileWriter fileWriter = new FileWriter("src/main/java/controller/server/info.txt", false);
                fileWriter.write(Integer.toString(wage));
                fileWriter.write("\n");
                fileWriter.write(Integer.toString(minBalance));
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getOnlineUsers() {
        ArrayList<String> username = new ArrayList<>();
        for (User user : users.values()) {
            username.add(user.getUsername());
        }
        return username;
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