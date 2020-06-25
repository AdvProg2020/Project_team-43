package Controller.Graphic;

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

    public void setParent(Application parent, Product product) {
        this.parent = parent;
        this.product = product;
    }

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

    @Override
    public void goBack() {
        try {
            if (parent instanceof ProductWindow) {
                ProductWindow.getInstance().setProduct(product, ProductPanelWindow.getInstance());
                ProductWindow.getInstance().start(stage);
            } else {
                parent.start(stage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
