package View.GraphicController;

import View.graphic.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Product;

public class LoggedOutStatusController extends Controller {
    private Stage stage = MainWindow.getInstance().getStage();
    private Application parent;
    private Product product;


    @FXML
    private Button registerButton;

    @FXML
    public void registerButtonClicked() {
        Music.getInstance().open();
        RegisterWindow.getInstance().start(stage);
    }

    @FXML
    public void loginButtonClicked() {
        Music.getInstance().open();
        LoginWindow.getInstance().start(stage);
    }


}
