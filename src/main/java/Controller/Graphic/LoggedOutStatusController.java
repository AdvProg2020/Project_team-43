package Controller.Graphic;

import View.graphic.LoginWindow;
import View.graphic.MainWindow;
import View.graphic.RegisterWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoggedOutStatusController {
    private Stage stage = MainWindow.getInstance().getStage();
    @FXML
    private Button registerButton;

    @FXML
    public void registerButtonClicked() {
        RegisterWindow.getInstance().start(stage);
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
