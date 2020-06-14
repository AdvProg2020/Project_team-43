package Controller.Graphic;

import Controller.console.BuyerProcessor;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class PurchaseMenuController extends Controller {
    public TextField phoneNumber;
    public TextArea address;
    public ImageView paymentButton;
    public JFXTextField discountCode;

    public void initialize() {
        BooleanBinding phoneNumberValid = Bindings.createBooleanBinding(() ->
                (phoneNumber.getText().matches("\\d+")), phoneNumber.textProperty());
        BooleanBinding addressValid = Bindings.createBooleanBinding(() -> address.getText().length() > 0, address.textProperty());
        paymentButton.disableProperty().bind(addressValid.not().or(phoneNumberValid.not()));
    }

}
