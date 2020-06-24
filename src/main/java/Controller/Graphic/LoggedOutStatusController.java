package Controller.Graphic;

import View.graphic.LoginWindow;
import View.graphic.MainWindow;
import View.graphic.RegisterWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoggedOutStatusController extends Controller {
    private Stage stage = MainWindow.getInstance().getStage();
    @FXML
    private Button registerButton;

    @FXML
    public void registerButtonClicked() {
        //Music.getInstance().open();
        RegisterWindow.getInstance().start(stage);
    }

    @FXML
    public void loginButtonClicked() {
        //Music.getInstance().open();
        LoginWindow.getInstance().start(stage);
    }


}
