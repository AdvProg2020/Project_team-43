package View.GraphicController;

import com.jfoenix.controls.JFXTextField;
import controller.client.Processor;


public class BankForChargeController extends Controller {
    public JFXTextField bankPassword;
    public JFXTextField bankUsername;
    public JFXTextField amount;
    public JFXTextField accountId;

    public void pay() {
        client.chargeAccount(bankUsername.getText(), bankPassword.getText(), amount.getText(), accountId.getText(), Processor.user);
    }
}
