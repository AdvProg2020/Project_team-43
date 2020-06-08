package Controller.Graphic;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

import java.awt.*;

public class LoginMenuController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    public void loginButtonClicked() {
        System.out.println(passwordField.getText());
    }
}
