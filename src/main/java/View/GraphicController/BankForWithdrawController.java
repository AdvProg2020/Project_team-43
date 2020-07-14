package View.GraphicController;

import View.graphic.BuyerUserWindow;
import View.graphic.MainWindow;
import View.graphic.SellerUserWindow;
import com.jfoenix.controls.JFXTextField;
import controller.client.BuyerProcessor;

import model.UserType;


public class BankForWithdrawController extends Controller {
    public JFXTextField amount;
    public JFXTextField accountId;

    public void pay() {
        client.withDraw(amount.getText(), accountId.getText(), BuyerProcessor.getInstance().getUser());
    }

    public void back() {
        if (BuyerProcessor.getInstance().getUser().getUserType() == UserType.BUYER) {
            BuyerUserWindow.getInstance().start(MainWindow.getInstance().getStage());
        } else {
            SellerUserWindow.getInstance().start(MainWindow.getInstance().getStage());
        }
    }
}
