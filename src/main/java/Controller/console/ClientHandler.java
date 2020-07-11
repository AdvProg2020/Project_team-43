package Controller.console;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private final Server server;

    public ClientHandler(DataInputStream dataInputStream, DataOutputStream dataOutputStream, Socket socket, Server server) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        handleClient();
    }

    private void handleClient() {
        try {
            while (true) {
                String command = dataInputStream.readUTF();
                String result;
                if (command.startsWith("login")) {
                    login(command.split(" ")[1], command.split(" ")[2]);
                } else if (command.startsWith("register")) {
                    result = server.register(command.split(" ")[1], command.split(" ")[2], command.split(" ")[3],
                            command.split(" ")[4]
                            , command.split(" ")[5]
                            , command.split(" ")[6], command.split(" ")[7]);
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
