package Controller.Graphic;

import View.graphic.LoginWindow;
import View.graphic.MainWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoggedOutStatusController {
    private Stage stage = MainWindow.getInstance().getStage();
    @FXML
    private Button registerButton;

    @FXML
    public void registerButtonClicked() {

    }

    @FXML
    public void loginButtonClicked() {
        LoginWindow.getInstance().start(stage);
    }

    @FXML
    public void goBack() {
        MainWindow.getInstance().start(stage);
    }
}
