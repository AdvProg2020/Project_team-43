package Controller.console;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

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
                } else if (command.startsWith("createManagerProfile")) {
                    ArrayList<String> managerInfo = new ArrayList<String>();
                    managerInfo.add(command.split(" ")[1]);
                    managerInfo.add(command.split(" ")[2]);
                    managerInfo.add(command.split(" ")[3]);
                    managerInfo.add(command.split(" ")[4]);
                    managerInfo.add(command.split(" ")[5]);
                    managerInfo.add(command.split(" ")[6]);
                    server.createManagerProfile(managerInfo);
                    dataOutputStream.writeUTF("createManagerProfile done");
                    dataOutputStream.flush();
                } else if(command.startsWith("acceptRequest")){
                    server.acceptRequest(command.split(" ")[1]);
                    dataOutputStream.writeUTF("acceptRequest done");
                    dataOutputStream.flush();
                }else if(command.startsWith("declineRequest")){
                    server.declineRequest(command.split(" ")[1]);
                    dataOutputStream.writeUTF("declineRequest done");
                    dataOutputStream.flush();
                } else if(command.startsWith("deleteUser")){
                    server.deleteUser(command.split(" ")[1]);
                    dataOutputStream.writeUTF("deleteUser done");
                    dataOutputStream.flush();
                } else if(command.startsWith("editCategory")){
                    server.editCategory(command.split(" ")[1], command.split(" ")[2]);
                    dataOutputStream.writeUTF("editCategory done");
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
