package View.GraphicController;

import controller.client.BossProcessor;
import controller.client.Processor;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import model.*;
import model.request.Request;

import java.io.IOException;



public class SupporterMenuController extends Controller {
    public BossProcessor bossProcessor = BossProcessor.getInstance();
    public User user;
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField phoneNumber;
    public ImageView profilePhoto;
    public Text userName;
    public ListView usersListView;
    ObservableList<String> users;




    public void initialize(){
        init();
        user = Processor.user;
        firstName.setPromptText(user.getUserPersonalInfo().getFirstName());
        lastName.setPromptText(user.getUserPersonalInfo().getLastName());
        email.setPromptText(user.getUserPersonalInfo().getEmail());
        password.setPromptText(user.getUserPersonalInfo().getPassword());
        phoneNumber.setPromptText(user.getUserPersonalInfo().getPhoneNumber());
        userName.setText(user.getUsername());
        setUserImage(user, profilePhoto);
        usersListView.setItems(users);
    }

    public void updateUsersListView() {
        init();
        users.clear();
        for (String userName : ((Supporter) user).getUsers().keySet()) {
            users.add(userName);
        }
        usersListView.setItems(users);
    }

    private void init() {
        User.setAllUsers(client.getAllUsers());
    }

    public void browsePhotoUser() throws IOException {
        browsePhotoUser(user, profilePhoto);
    }

    public void open() {
        Music.getInstance().open();
    }

}
