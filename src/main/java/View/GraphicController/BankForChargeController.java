package View.GraphicController;

import View.graphic.BuyerUserWindow;
import View.graphic.MainWindow;
import View.graphic.SellerUserWindow;
import com.jfoenix.controls.JFXTextField;
import controller.client.BuyerProcessor;
import controller.client.Processor;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import model.UserType;


public class BankForChargeController extends Controller {


    public JFXTextField bankPassword;
    public JFXTextField bankUsername;
    public JFXTextField amount;
    public JFXTextField accountId;


    public void pay() {
        String message = client.chargeAccount(bankUsername.getText(), bankPassword.getText(), amount.getText(), accountId.getText(), Processor.user);
        new Alert(Alert.AlertType.INFORMATION, message).showAndWait();
        if (BuyerProcessor.getInstance().getUser().getUserType() == UserType.BUYER) {
            BuyerUserWindow.getInstance().start(MainWindow.getInstance().getStage());
        } else {
            SellerUserWindow.getInstance().start(MainWindow.getInstance().getStage());
        }

    }

    public void back() {
        if (BuyerProcessor.getInstance().getUser().getUserType() == UserType.BUYER) {
            BuyerUserWindow.getInstance().start(MainWindow.getInstance().getStage());
        } else {
            SellerUserWindow.getInstance().start(MainWindow.getInstance().getStage());
        }
    }
}
