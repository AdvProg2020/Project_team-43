package View;


import Controller.console.Processor;

import model.Category;
import model.User;

import java.io.*;
import java.net.Socket;

public class Client {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public void run() {
        try {
            Socket socket = new Socket("localhost", 2020);
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
            if (result.equals("logged in successful")) {
                byte[] bytes = new byte[30000];
                dataInputStream.read(bytes);
                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                ObjectInputStream is = new ObjectInputStream(in);
                User user = (User) is.readObject();
                Processor.setUser(user);
                Processor.setIsLogin(true);
            }
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String register(String firstName, String lastName, String email, String phone, String password, String username, String companyName) throws IOException {
        dataOutputStream.writeUTF("register" + " " + firstName + " " + lastName + " " + email + " " + phone + " " + password + " " + username + " " + companyName);
        dataOutputStream.flush();
        String result = dataInputStream.readUTF();
        return result;


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
            dataOutputStream.writeUTF("editCategory" +" " + categoryName +" " + newName);
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
