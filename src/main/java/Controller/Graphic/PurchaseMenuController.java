package Controller.Graphic;

import Controller.console.BuyerProcessor;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class PurchaseMenuController extends Controller {
    public TextField phoneNumber;
    BooleanBinding phoneNumberValid = Bindings.createBooleanBinding(() ->
            (phoneNumber.getText().matches("\\d+")), phoneNumber.textProperty());

    public TextField discountCode;
    //BooleanBinding discountCodeValid = Bindings.createBooleanBinding(() ->
    //      BuyerProcessor.getInstance().checkDiscountCode(discountCode.getText()), discountCode.textProperty());

    public TextArea address;
    BooleanBinding addressValid = Bindings.createBooleanBinding(() -> address.getText().length() > 0, address.textProperty());

    public ImageView paymentButton;

    public void initialize() {
        paymentButton.disableProperty().bind(addressValid.not().or(phoneNumberValid.not()));
    }

}
