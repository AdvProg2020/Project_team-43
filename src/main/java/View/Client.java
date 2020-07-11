package View;


import Controller.console.BuyerProcessor;
import Controller.console.Processor;
import Controller.console.Server;
import model.User;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.net.Socket;

public class Client {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public void run() {
        try {
            Socket socket = new Socket("127.0.0.1", 8585);
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
            byte[] bytes = new byte[30000];
            if (result.equals("logged in successful"))
                dataInputStream.read(bytes);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream is = new ObjectInputStream(in);
            User user = (User)is.readObject();
            Processor.setUser(user);
            Processor.setIsLogin(true);
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }
}
