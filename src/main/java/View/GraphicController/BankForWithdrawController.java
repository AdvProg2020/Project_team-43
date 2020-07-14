package View.GraphicController;

import com.jfoenix.controls.JFXTextField;
import controller.client.BuyerProcessor;
import javafx.scene.input.MouseEvent;

import java.io.BufferedInputStream;

public class BankForWithdrawController extends Controller {
    public JFXTextField amount;
    public JFXTextField accountId;

    public void pay() {
        client.withDraw(amount.getText(),accountId.getText(), BuyerProcessor.getInstance().getUser());
    }
}
