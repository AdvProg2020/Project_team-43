package Controller.Graphic;

import Controller.console.SellerProcessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import model.*;

import java.awt.*;
import java.io.File;
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
    public Text usernameText;
    public ImageView profilePhoto;
    private Seller user;
    private String productPhotoPath;

    @FXML
    public void initialize() {
        user = (Seller) sellerProcessor.getUser();
        usernameText.setText(user.getUsername());
        UserPersonalInfo userPersonalInfo = user.getUserPersonalInfo();
        firstName.setText(userPersonalInfo.getFirstName());
        lastName.setText(userPersonalInfo.getLastName());
        email.setText(userPersonalInfo.getEmail());
        password.setText(userPersonalInfo.getPassword());
        phoneNumber.setText(userPersonalInfo.getPhoneNumber());
        company.setText(user.getCompany().getName());
        if (user.getImagePath() != null) {
            profilePhoto.setImage(new Image("file:" + user.getImagePath()));
        }
    }

    @FXML
    public void update() {
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
    public void addNewProduct() {
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
                        amountNewProduct.getText(), features, productPhotoPath);
            } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void browsePhotoUser() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            user.setImagePath(file.getAbsolutePath());
            profilePhoto.setImage(new Image(file.toURI().toString()));
        }
    }


    public void browsePhotoProduct() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            productPhotoPath = file.getAbsolutePath();
        }

    }
}
