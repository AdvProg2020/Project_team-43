package View.GraphicController;

import com.jfoenix.controls.JFXTextField;
import controller.client.BuyerProcessor;
import controller.client.Processor;


public class BankForChargeController extends Controller {
    public static boolean chargeForBuy = false;

    public static void setChargeForBuy(boolean chargeForBuy) {
        BankForChargeController.chargeForBuy = chargeForBuy;
    }

    public JFXTextField bankPassword;
    public JFXTextField bankUsername;
    public JFXTextField amount;
    public JFXTextField accountId;

    public void initialize() {
        if (chargeForBuy) {
            amount.setVisible(false);
            amount.setText(Integer.toString((int) (BuyerProcessor.getInstance().showTotalPrice())));
        }

    }

    public void pay() {
        client.chargeAccount(bankUsername.getText(), bankPassword.getText(), amount.getText(), accountId.getText(), Processor.user);
        chargeForBuy = false;
    }
}
