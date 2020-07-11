package Controller.console;

import javax.xml.soap.SOAPConnection;
import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ObjectOutputStream objectOutputStream;
    private Socket socket;
    private Server server;

    public ClientHandler(DataInputStream dataInputStream, DataOutputStream dataOutputStream, Socket socket, Server server) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.socket = socket;
    }

    @Override
    public void run() {
        handleClient();
    }

    private void handleClient() {
        try {
            while (true) {
                String command = dataInputStream.readUTF();
                String result = null;
                if (command.startsWith("login")) {
                    System.out.println("1"+command.split(" ")[1]);
                    result = BuyerProcessor.getInstance().login(command.split(" ")[1], command.split(" ")[2]);
                }
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(buffer);
                oos.writeObject(BuyerProcessor.getInstance().getUser());
                oos.close();
                byte[] rawData = buffer.toByteArray();
                dataOutputStream.writeUTF(result);
                dataOutputStream.flush();
                if (result.equals("logged in successful")){
                    dataOutputStream.write(rawData);
                    dataOutputStream.flush();
                }
                //TODO : if zena
                System.out.println(command);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
