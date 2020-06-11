package Controller.Graphic;

import Controller.console.SellerProcessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import model.Category;
import model.InvalidCommandException;
import model.UserPersonalInfo;

import java.util.HashMap;

public class SellerMenuController {
    public TextField nameNewProduct;
    public TextField companyNewCompany;
    public TextField priceNewProduct;
    public TextField amountNewProduct;
    public TextField categoryNewProduct;
    @FXML
    SellerProcessor sellerProcessor = SellerProcessor.getInstance();
    public ListView productsList;
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField phoneNumber;
    public TextField existingProductId;
    public TextField amountTextField;

    @FXML
    public void initialize() {
        UserPersonalInfo userPersonalInfo = sellerProcessor.getUser().getUserPersonalInfo();
        firstName.setText(userPersonalInfo.getFirstName());
        lastName.setText(userPersonalInfo.getLastName());
        email.setText(userPersonalInfo.getEmail());
        password.setText(userPersonalInfo.getPassword());
        phoneNumber.setText(userPersonalInfo.getPhoneNumber());
    }

    @FXML
    public void update(ActionEvent actionEvent) {
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        sellerProcessor.editField(userPersonalInfo);
    }

    @FXML
    public void addExistingProduct() {
        try {
            sellerProcessor.addExistingProduct(existingProductId.getText(), amountTextField.getText());
        } catch (InvalidCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void addNewProduct(ActionEvent event) {
        Category category = Category.getCategoryByName(categoryNewProduct.getText());
        HashMap<String, String> features = new HashMap<>();
        for (String feature : category.getFeatures()) {
            String value;
            TextInputDialog textInputDialog = new TextInputDialog("");
            textInputDialog.setContentText(feature);
            textInputDialog.showAndWait();
            value = textInputDialog.getEditor().getText();
            features.put(feature, value);
        }
        try {
            sellerProcessor.addNewProduct(nameNewProduct.getText(), companyNewCompany.getText(), category.getName(), priceNewProduct.getText(),
                    amountNewProduct.getText(), features);
        } catch (InvalidCommandException e) {
            System.out.println(e.getMessage());
        }

    }
}
