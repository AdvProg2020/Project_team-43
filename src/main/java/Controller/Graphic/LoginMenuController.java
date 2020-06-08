package Controller.Graphic;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginMenuController {
    @FXML
    public TextField usernameTextField;
    @FXML
    public PasswordField passwordField;

    @FXML
    public void loginButtonClicked() {
        System.out.println(passwordField.getText());
    }

    @FXML
    public void goBack() {

    }
}
