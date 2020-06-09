package Controller.Graphic;

import Controller.console.BuyerProcessor;
import View.graphic.MainWindow;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    public Label invalidUserName;
    public Label invalidPassWord;
    private BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();

    public void registerButtonClicked(ActionEvent actionEvent) {
        invalidPassWord.setVisible(false);
        invalidUserName.setVisible(false);
        if (passWord.getText().length() < 8) {
            invalidPassWord.setVisible(true);
            return;
        }
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), passWord.getText());
        if (buyerProcessor.register(userPersonalInfo, userName.getText(), companyName.getText()).equals("done")) {
            ((Stage) userName.getScene().getWindow()).close();
            MainWindow.getInstance().start(MainWindow.getInstance().getStage());
        } else {
            invalidUserName.setVisible(true);
        }

    }
}
