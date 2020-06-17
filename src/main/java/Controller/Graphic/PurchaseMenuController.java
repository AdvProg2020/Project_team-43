package Controller.Graphic;

import Controller.console.BuyerProcessor;
import View.graphic.BuyerUserWindow;
import View.graphic.MainWindow;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CodedDiscount;


public class PurchaseMenuController extends Controller {
    public TextField phoneNumber;
    public TextArea address;
    public ImageView paymentButton;
    public JFXTextField discountCode;
    public Label validLabel;
    public Text totalAmount;
    public Text sale;
    public Text payment;


    public void initialize() {
        totalAmount.setText(Double.toString(BuyerProcessor.getInstance().getRealValueOfCart()));
        payment.setText(Double.toString(BuyerProcessor.getInstance().showTotalPrice()));
        sale.setText(Double.toString(Double.parseDouble(totalAmount.getText()) - Double.parseDouble(payment.getText())));
        BooleanBinding phoneNumberValid = Bindings.createBooleanBinding(() ->
                (phoneNumber.getText().matches("\\d+")), phoneNumber.textProperty());
        BooleanBinding addressValid = Bindings.createBooleanBinding(() -> address.getText().length() > 0, address.textProperty());
        paymentButton.disableProperty().bind(addressValid.not().or(phoneNumberValid.not()));
        validLabel.setVisible(false);
        discountCode.textProperty().addListener((observable) -> {
                    if (!discountCode.getText().equals("")) {
                        validLabel.setVisible(true);
                        if (BuyerProcessor.getInstance().checkDiscountCode(discountCode.getText())) {
                            validLabel.setText("valid");
                            validLabel.setTextFill(Color.GREEN);
                            setDiscount();
                        } else {
                            validLabel.setText("invalid");
                            validLabel.setTextFill(Color.RED);
                            disableDiscount();
                        }
                    } else {
                        disableDiscount();
                        validLabel.setVisible(false);
                    }
                }
        );
    }

    public void setDiscount() {
        payment.setText(Double.toString(Double.parseDouble(payment.getText()) *
                (100 - CodedDiscount.getDiscountById(discountCode.getText()).getDiscountAmount()) / 100));
        sale.setText(Double.toString(Double.parseDouble(totalAmount.getText()) - Double.parseDouble(payment.getText())));
    }

    public void disableDiscount() {
        totalAmount.setText(Double.toString(BuyerProcessor.getInstance().getRealValueOfCart()));
        payment.setText(Double.toString(BuyerProcessor.getInstance().showTotalPrice()));
        sale.setText(Double.toString(Double.parseDouble(totalAmount.getText()) - Double.parseDouble(payment.getText())));
    }


    public void purchase() {
        double discount = 0;
        if (validLabel.getText().equals("valid")) {
            discount = CodedDiscount.getDiscountById(discountCode.getText()).getDiscountAmount();
            BuyerProcessor.getInstance().useDiscountCode(CodedDiscount.getDiscountById(discountCode.getText()));
        }
        String result = BuyerProcessor.getInstance().payment(address.getText(), phoneNumber.getText(), discount);
        new Alert(Alert.AlertType.INFORMATION, result).showAndWait();
        cancelPurchase();
    }

    public void cancelPurchase() {
        BuyerUserWindow.getInstance().start(MainWindow.getInstance().getStage());
    }
}
