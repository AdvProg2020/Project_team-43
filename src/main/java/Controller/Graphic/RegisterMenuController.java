package Controller.Graphic;

import Controller.console.BuyerProcessor;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.DeliveryStatus;
import model.UserPersonalInfo;

public class RegisterMenuController {
    public TextField userName;
    public TextField firstName;
    public TextField lastName;
    public TextField phoneNumber;
    public TextField email;
    public TextField companyName;
    public PasswordField passWord;
    private BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();

    public void register(ActionEvent actionEvent) {
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), passWord.getText());
        buyerProcessor.register(userPersonalInfo, userName.getText(), companyName.getText());

    }
}
