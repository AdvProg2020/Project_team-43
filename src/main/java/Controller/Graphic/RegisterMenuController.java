package Controller.Graphic;

import Controller.console.BuyerProcessor;
import View.graphic.MainWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
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
    public Text alertMessage;
    public RadioButton buyerChoice;
    public RadioButton sellerChoice;

    private BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();

    public void registerButtonClicked(ActionEvent actionEvent) {
        if (buyerChoice.isSelected()) {
            companyName.setText("");
        }
        alertMessage.setVisible(false);
        if (passWord.getText().length() < 8) {
            Music.getInstance().error();
            alertMessage.setText("password must contains at least 8 characters");
            alertMessage.setVisible(true);
            return;
        }
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), passWord.getText());
        if (buyerProcessor.register(userPersonalInfo, userName.getText(), companyName.getText()).equals("done")) {
            ((Stage) userName.getScene().getWindow()).close();
            MainWindow.getInstance().start(MainWindow.getInstance().getStage());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Registered successfully");
            Music.getInstance().confirmation();
            if (sellerChoice.isSelected()) {
                alert.setContentText("Waiting for manager to confirm");
            }
            alert.show();
        } else {
            Music.getInstance().error();
            alertMessage.setText("username exists please change it");
            alertMessage.setVisible(true);
        }

    }

    @FXML
    public void buyerChoiceAction(ActionEvent event) {
        companyName.setVisible(false);
        sellerChoice.setSelected(false);
    }

    @FXML
    public void sellerChoiceAction(ActionEvent event) {
        buyerChoice.setSelected(false);
        companyName.setVisible(true);
    }
}
