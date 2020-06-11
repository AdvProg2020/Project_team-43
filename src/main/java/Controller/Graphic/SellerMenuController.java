package Controller.Graphic;

import Controller.console.SellerProcessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Seller;
import model.UserPersonalInfo;

public class SellerMenuController {
    @FXML
    SellerProcessor sellerProcessor = SellerProcessor.getInstance();
    public ListView productsList;
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField phoneNumber;

    @FXML
    public void initialize() {
        UserPersonalInfo userPersonalInfo = sellerProcessor.getUser().getUserPersonalInfo();
        firstName.setText(userPersonalInfo.getFirstName());
        lastName.setText(userPersonalInfo.getLastName());
        email.setText(userPersonalInfo.getEmail());
        password.setText(userPersonalInfo.getPassword());
        phoneNumber.setText(userPersonalInfo.getPhoneNumber());
    }

    public void update(ActionEvent actionEvent) {
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        sellerProcessor.editField(userPersonalInfo);
    }
}
