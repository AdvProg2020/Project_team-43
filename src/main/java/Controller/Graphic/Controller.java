package Controller.Graphic;

import Controller.console.BuyerProcessor;
import View.console.LoggedOutStatus;
import View.graphic.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.Product;
import model.UserType;

public abstract class Controller {
    protected Stage stage = MainWindow.getInstance().getStage();
    protected Application parent;
    protected Product product;

    public void setParent(Application parent, Product product) {
        this.parent = parent;
        this.product = product;
    }


    public void goBack() {
        Music.getInstance().backPage();
        MainWindow.getInstance().start(MainWindow.getInstance().getStage());
    }

    public void userPanelButtonClicked() {
        BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
        Music.getInstance().open();
        if (!(buyerProcessor.isUserLoggedIn())) {
            LoggedOutStatusWindow.getInstance().setParent(parent, null);
            LoggedOutStatusWindow.getInstance().start(stage);
        } else if (buyerProcessor.getUser().getUserType() == UserType.MANAGER) {
            ManagerUserWindow.getInstance().setParent(parent, null);
            ManagerUserWindow.getInstance().start(stage);
        } else if (buyerProcessor.getUser().getUserType() == UserType.SELLER) {
            SellerUserWindow.getInstance().setParent(parent, null);
            SellerUserWindow.getInstance().start(stage);
        } else {
            BuyerUserWindow.getInstance().setParent(parent, null);
            BuyerUserWindow.getInstance().start(stage);
        }
    }
}
