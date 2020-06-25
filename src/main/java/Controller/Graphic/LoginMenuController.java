package Controller.Graphic;

import Controller.console.BuyerProcessor;
import Controller.console.Processor;
import View.graphic.LoginWindow;
import View.graphic.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginMenuController {

    private BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label wrongUsername;
    @FXML
    private Label wrongPassword;

    @FXML
    public void loginButtonClicked() {
        Music.getInstance().open();
        wrongUsername.setVisible(false);
        wrongPassword.setVisible(false);
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String result = buyerProcessor.login(username, password);
        System.out.println(result);
        if (result.equals("logged in successful")) {
            ((Stage) passwordField.getScene().getWindow()).close();
            MainWindow.getInstance().start(MainWindow.getInstance().getStage());
        } else if (result.equals("there is no user with this username")) {
            wrongUsername.setVisible(true);
        } else if (result.equals("incorrect password")) {
            wrongPassword.setVisible(true);
        }
    }

}
