package Controller.Graphic;

import Controller.console.SellerProcessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import model.Category;
import model.InvalidCommandException;
import model.Seller;
import model.UserPersonalInfo;

import java.util.HashMap;

public class SellerMenuController extends Controller {
    public TextField nameNewProduct;
    public TextField companyNewCompany;
    public TextField priceNewProduct;
    public TextField amountNewProduct;
    public TextField categoryNewProduct;
    SellerProcessor sellerProcessor = SellerProcessor.getInstance();
    public ListView productsList;
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField phoneNumber;
    public TextField existingProductId;
    public TextField amountTextField;
    public TextField company;
    public Text invalidCategory;
    public Text messageText;

    @FXML
    public void initialize() {
        UserPersonalInfo userPersonalInfo = sellerProcessor.getUser().getUserPersonalInfo();
        firstName.setText(userPersonalInfo.getFirstName());
        lastName.setText(userPersonalInfo.getLastName());
        email.setText(userPersonalInfo.getEmail());
        password.setText(userPersonalInfo.getPassword());
        phoneNumber.setText(userPersonalInfo.getPhoneNumber());
        company.setText(((Seller) sellerProcessor.getUser()).getCompany().getName());
    }

    @FXML
    public void update(ActionEvent actionEvent) {
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        sellerProcessor.editField(userPersonalInfo);
    }

    @FXML
    public void addExistingProduct() {
        String message = sellerProcessor.addExistingProduct(existingProductId.getText(), amountTextField.getText());
        messageText.setText(message);
        messageText.setVisible(true);
    }

    @FXML
    public void addNewProduct(ActionEvent event) {
        Category category = Category.getCategoryByName(categoryNewProduct.getText());
        if (category == null) {
            invalidCategory.setVisible(true);
        } else {
            invalidCategory.setVisible(false);
            HashMap<String, String> features = new HashMap<>();
            for (String feature : category.getFeatures()) {
                String value;
                TextInputDialog textInputDialog = new TextInputDialog("");
                textInputDialog.setContentText(feature);
                textInputDialog.setTitle("features");
                textInputDialog.setHeaderText("product features");
                textInputDialog.initModality(Modality.WINDOW_MODAL);
                textInputDialog.initOwner(stage);
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
}
