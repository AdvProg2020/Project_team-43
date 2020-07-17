package View.GraphicController;

import View.graphic.BuyerUserWindow;
import View.graphic.MainWindow;
import com.jfoenix.controls.JFXTextField;
import controller.client.BuyerProcessor;
import controller.client.Processor;
import model.Buyer;
import model.CodedDiscount;

import java.util.HashMap;

public class PurchaseWithBankController extends Controller {

    public JFXTextField accountId;
    public JFXTextField bankUsername;
    public JFXTextField bankPassword;
    public String address;
    public String phoneNumber;
    public String codedDiscount;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCodedDiscount(String codedDiscount) {
        this.codedDiscount = codedDiscount;
    }


    public void pay() {
        int amount = (int) BuyerProcessor.getInstance().showTotalPrice();
        String result = client.chargeAccount(bankUsername.getText(), bankPassword.getText(), Integer.toString(amount), accountId.getText(), BuyerProcessor.getInstance().getUser());
        if (!result.equals("done successfully")) {
            back();
            return;
        }
        double discount = 0;
        if (BuyerProcessor.getInstance().checkDiscountCode(codedDiscount)) {
            discount = CodedDiscount.getDiscountById(codedDiscount).getDiscountAmount();
        }
        client.purchaseWithCredit(BuyerProcessor.getInstance().getUser(), address, phoneNumber, discount);
        endPurchase();
    }

    public void endPurchase() {
        String username = Processor.user.getUsername();
        String password = Processor.user.getUserPersonalInfo().getPassword();
        client.logout();
        client.login(username, password);
        ((Buyer) BuyerProcessor.getInstance().getUser()).setNewBuyerCart(new HashMap<>());
        Music.getInstance().backPage();
        BuyerUserWindow.getInstance().start(MainWindow.getInstance().getStage());
    }

    public void back() {
        BuyerUserWindow.getInstance().start(MainWindow.getInstance().getStage());
    }
}
