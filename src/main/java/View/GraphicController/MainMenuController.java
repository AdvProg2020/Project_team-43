package View.GraphicController;

import controller.client.BuyerProcessor;
import View.graphic.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Product;
import model.UserType;

public class MainMenuController extends Controller {
    private BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
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
        } else if (buyerProcessor.getUser().getUserType()==UserType.Supporter) {
            SupporterUserWindow.getInstance().setParent(MainWindow.getInstance(), null);
            SupporterUserWindow.getInstance().start(stage);
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
            client.logout();
            buyerProcessor.logout();
            buyerProcessor.clearCart();
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
}
