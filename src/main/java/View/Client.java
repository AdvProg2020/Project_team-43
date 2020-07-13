package View;


import controller.client.Processor;

import model.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String token;

    public void run() {
        try {
            Socket socket = new Socket("127.0.0.1", 2020);
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

    public void createManagerProfile(String userName, String firstName, String lastName, String email, String phone, String password) {
        try {
            dataOutputStream.writeUTF("createManagerProfile" + " " + userName + " " + firstName + " " + lastName + " " + email + " " + phone + " " + password);
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptRequest(String requestId) {
        try {
            dataOutputStream.writeUTF("acceptRequest" + " " + requestId);
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void declineRequest(String requestId) {
        try {
            dataOutputStream.writeUTF("declineRequest" + " " + requestId);
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String userName) {
        try {
            dataOutputStream.writeUTF("deleteUser" + " " + userName);
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editCategory(String categoryName, String newName) {
        try {
            dataOutputStream.writeUTF("editCategory" + " " + categoryName + " " + newName);
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
}
