package Controller.console;

import model.*;
import model.request.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;

public class ServerImp {
    BossProcessor bossProcessor = BossProcessor.getInstance();
    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(2020);
        ServerImp server = new ServerImp();
        while (true) {
            Socket socket = serverSocket.accept();
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            new ClientHandler(dataInputStream, dataOutputStream, socket, server).start();
            System.out.println("connected");

        }
    }

    public synchronized String register(String firstName, String lastName, String email, String phone, String password, String username, String companyName) {
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName, lastName, email
                , phone, password);
        return BuyerProcessor.getInstance().register(userPersonalInfo, username, companyName);
    }

    public void createManagerProfile(ArrayList<String> managerInfo) {
        bossProcessor.createManagerProfileFXML(managerInfo);
    }

    public void acceptRequest(String requestId) {
        Request request = Request.getRequestById(requestId);
        try {
            bossProcessor.acceptRequestFXML(request);
        } catch (InvalidCommandException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void declineRequest(String requestId) {
        Request request = Request.getRequestById(requestId);
        bossProcessor.declineRequestFXML(request);

    }

    public void deleteUser(String userName) {
        User user = User.getUserByUserName(userName);
        bossProcessor.deleteUserFXML(user);

    }

    public void editCategory(String categoryName, String newName) {
        Category category = Category.getCategoryByName(categoryName);
        bossProcessor.editCategoryFXML(category, newName);
    }
}
