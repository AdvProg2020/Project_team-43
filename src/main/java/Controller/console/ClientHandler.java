package Controller.console;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private ServerImp server;

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
                String result;
                if (command.startsWith("login")) {
                    login(command.split(" ")[1], command.split(" ")[2]);
                } else if (command.startsWith("register")) {
                    String[] commands = command.split(" ");
                    result = server.register(commands[1], commands[2], commands[3], commands[4], commands[5], commands[6], commands[7]);
                    dataOutputStream.writeUTF(result);
                    dataOutputStream.flush();
                }
                System.out.println(command);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void login(String username, String password) throws IOException {
        String result = BuyerProcessor.getInstance().login(username, password);
        dataOutputStream.writeUTF(result);
        dataOutputStream.flush();
        if (result.equals("logged in successful")) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(BuyerProcessor.getInstance().getUser());
            oos.close();
            byte[] rawData = buffer.toByteArray();
            dataOutputStream.write(rawData);
            dataOutputStream.flush();
        }
    }


}
