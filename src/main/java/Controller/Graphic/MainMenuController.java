package Controller.Graphic;

import Controller.console.BuyerProcessor;
import View.console.LoggedOutStatus;
import View.graphic.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Product;
import model.UserType;

public class MainMenuController {
    private BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
    private Stage stage = MainWindow.getInstance().getStage();
    @FXML
    private Button loginButton;

    @FXML
    public void userPanelButtonClicked() {
        if (!(buyerProcessor.isUserLoggedIn())) {
            LoggedOutStatusWindow.getInstance().start(stage);
        } else if (buyerProcessor.getUser().getUserType() == UserType.MANAGER) {
            ManagerUserWindow.getInstance().start(stage);
        } else if (buyerProcessor.getUser().getUserType() == UserType.SELLER) {
            SellerUserWindow.getInstance().start(stage);
        } else {
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

    public void productsPanelButtonClicked(ActionEvent actionEvent) {
        ProductPanelWindow.getInstance().start(stage);
    }

    public void test(ActionEvent actionEvent) {
        ProductWindow.getInstance().setProduct(Product.getProductById("1"));
        ProductWindow.getInstance().start(stage);
    }
}
