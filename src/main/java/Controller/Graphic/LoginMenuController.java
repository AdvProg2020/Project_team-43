package Controller.Graphic;

import Controller.console.BuyerProcessor;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginMenuController {
    private BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
    @FXML
    public TextField usernameTextField;
    @FXML
    public PasswordField passwordField;

    @FXML
    public void loginButtonClicked() {
        System.out.println(buyerProcessor.login(usernameTextField.getText(), passwordField.getText()));
    }
    
}
