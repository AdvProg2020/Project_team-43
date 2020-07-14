package View.GraphicController;

import View.graphic.BankForChargeWindow;
import controller.client.BuyerProcessor;
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
        initCodedDiscounts();
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

    private void initCodedDiscounts() {
        CodedDiscount.setAllCodedDiscount(client.getAllCodedDiscounts());
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


    public void purchaseFromCredit() {
        Music.getInstance().confirmation();
        if (BuyerProcessor.getInstance().getUser().getBalance() < Double.parseDouble(payment.getText())) {
            new Alert(Alert.AlertType.INFORMATION, "not enough money").showAndWait();
            return;
        }
        double discount = checkDiscount();
        client.purchaseWithCredit(BuyerProcessor.getInstance().getUser(), address.getText(), phoneNumber.getText(), discount);
        String result = BuyerProcessor.getInstance().payment(address.getText(), phoneNumber.getText(), discount);
        new Alert(Alert.AlertType.INFORMATION, result).showAndWait();
        cancelPurchase();
    }

    public void purchaseFromBank() {
        BankForChargeController.setChargeForBuy(true);
        BankForChargeWindow.getInstance().start(MainWindow.getInstance().getStage());
        double discount = checkDiscount();
        BankForChargeWindow.getInstance().start(MainWindow.getInstance().getStage());
        client.purchaseWithCredit(BuyerProcessor.getInstance().getUser(), address.getText(), phoneNumber.getText(), discount);
        String result = BuyerProcessor.getInstance().payment(address.getText(), phoneNumber.getText(), discount);
        new Alert(Alert.AlertType.INFORMATION, result).showAndWait();
        cancelPurchase();
    }

    private double checkDiscount() {
        if (validLabel.getText().equals("valid")) {
            double discount = CodedDiscount.getDiscountById(discountCode.getText()).getDiscountAmount();
            client.useDiscountCode(BuyerProcessor.getInstance().getUser(), discountCode.getText());
            BuyerProcessor.getInstance().useDiscountCode(CodedDiscount.getDiscountById(discountCode.getText()));
            return discount;
        }
        return 0;
    }

    public void cancelPurchase() {
        Music.getInstance().backPage();
        BuyerUserWindow.getInstance().start(MainWindow.getInstance().getStage());
    }


}
