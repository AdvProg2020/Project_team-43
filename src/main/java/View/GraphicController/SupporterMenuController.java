package View.GraphicController;

import controller.client.Processor;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import model.User;

import java.io.IOException;

public class SupporterMenuController extends Controller {
    public User user;
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField phoneNumber;
    public ImageView profilePhoto;
    public Text userName;




    public void initialize(){
        user = Processor.user;
        firstName.setPromptText(user.getUserPersonalInfo().getFirstName());
        lastName.setPromptText(user.getUserPersonalInfo().getLastName());
        email.setPromptText(user.getUserPersonalInfo().getEmail());
        password.setPromptText(user.getUserPersonalInfo().getPassword());
        phoneNumber.setPromptText(user.getUserPersonalInfo().getPhoneNumber());
        userName.setText(user.getUsername());
        setUserImage(user, profilePhoto);
    }
    public void browsePhotoUser() throws IOException {
        browsePhotoUser(user, profilePhoto);
    }

    public void open() {
        Music.getInstance().open();
    }

}
