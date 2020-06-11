package Controller.Graphic;

import Controller.console.BuyerProcessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.UserPersonalInfo;

public class BuyerMenuController extends Controller {
    public ListView products;
    BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField phoneNumber;

    @FXML
    public void initialize() {
        UserPersonalInfo userPersonalInfo = buyerProcessor.getUser().getUserPersonalInfo();
        firstName.setText(userPersonalInfo.getFirstName());
        lastName.setText(userPersonalInfo.getLastName());
        email.setText(userPersonalInfo.getEmail());
        password.setText(userPersonalInfo.getPassword());
        phoneNumber.setText(userPersonalInfo.getPhoneNumber());
        products.getItems().add("heeloo");
    }

    public void update(ActionEvent actionEvent) {
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        buyerProcessor.editField(userPersonalInfo);
    }


}
