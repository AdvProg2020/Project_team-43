package View.GraphicController;

import Controller.console.BuyerProcessor;
import View.graphic.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Product;
import model.UserType;

public class MainMenuController extends Controller{
    private BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
    private Stage stage = MainWindow.getInstance().getStage();
    @FXML
    private Button loginButton;

    @FXML
    public void userPanelButtonClicked() {
        Music.getInstance().open();
        if (!(buyerProcessor.isUserLoggedIn())) {
            LoggedOutStatusWindow.getInstance().setParent(MainWindow.getInstance(), null);
            LoggedOutStatusWindow.getInstance().start(stage);
        } else if (buyerProcessor.getUser().getUserType() == UserType.MANAGER) {
            ManagerUserWindow.getInstance().setParent(MainWindow.getInstance(), null);
            ManagerUserWindow.getInstance().start(stage);
        } else if (buyerProcessor.getUser().getUserType() == UserType.SELLER) {
            SellerUserWindow.getInstance().setParent(MainWindow.getInstance(), null);
            SellerUserWindow.getInstance().start(stage);
        } else {
            BuyerUserWindow.getInstance().setParent(MainWindow.getInstance(), null);
            BuyerUserWindow.getInstance().start(stage);
        }
    }

    public void setLoginButtonText() {
        if (buyerProcessor.isUserLoggedIn()) {
            loginButton.setText("Logout");
        } else {
            loginButton.setText("Login");
        }
    }

    @FXML
    public void loginButtonClicked() {
        Music.getInstance().open();
        if (!(buyerProcessor.isUserLoggedIn())) {
            LoginWindow.getInstance().start(stage);
        } else {
            buyerProcessor.logout();
            loginButton.setText("Login");
        }
    }

    @FXML
    public void initialize() {
        setLoginButtonText();
    }

    public void productsPanelButtonClicked() {
        Music.getInstance().open();
        ProductPanelWindow.getInstance().start(stage);
    }

    public void test(ActionEvent actionEvent) {
        ProductWindow.getInstance().setProduct(Product.getProductById("4"), MainWindow.getInstance());
        ProductWindow.getInstance().start(stage);
    }
}