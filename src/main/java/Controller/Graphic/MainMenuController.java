package Controller.Graphic;

import View.graphic.LoginWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {
    @FXML
    public Button loginButton;

    @FXML
    public void userPanelButtonClicked() {

    }

    @FXML
    public void loginButtonClicked() {
        try {
            LoginWindow.getInstance().start((Stage) loginButton.getScene().getWindow());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
