package View;


import Controller.console.Processor;

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

}
