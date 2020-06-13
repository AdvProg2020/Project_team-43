package Controller.Graphic;

import Controller.console.SellerProcessor;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import model.*;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public TextField productIdTextField;
    public Text invalidIdProduct;
    public ImageView productPhoto;
    public ListView<String> productFeaturesList;
    public ListView<String> buyersListView;
    public Text buyerText;
    public Text statusText;
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

    public void showProduct() {
        String id = productIdTextField.getText();
        if (sellerProcessor.checkProduct(id)) {
            buyersListView.getItems().clear();
            Product product = user.getProductById(id);
            showProductFeaturesList(product);

            if (product.getImagePath() != null) {
                productPhoto.setImage(new Image("file:" + product.getImagePath()));
            } else {
                productPhoto.setImage(new Image("file:src/main/resources/user.png"));
            }

            for (Buyer buyer : user.getBuyers(product.getProductId())) {
                buyersListView.getItems().add(buyer.toString());
            }

            statusText.setText("Status: " + product.getProductState());

            buyerText.setVisible(true);
            buyersListView.setVisible(true);
            productPhoto.setVisible(true);
            statusText.setVisible(true);
            invalidIdProduct.setVisible(false);

        } else {
            invalidIdProduct.setVisible(true);
        }
    }

    private void showProductFeaturesList(Product product) {
        productFeaturesList.getItems().clear();
        productFeaturesList.getItems().add("Name: " + product.getName());
        ContextMenu contextMenu = new ContextMenu();
        Menu editRequest = new Menu("edit request");
        editRequest.setOnAction(event -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            String field = productFeaturesList.getSelectionModel().getSelectedItem().split(":")[0];
            textInputDialog.setHeaderText(field + ":");
            textInputDialog.setTitle("Edit Request");
            textInputDialog.initModality(Modality.WINDOW_MODAL);
            textInputDialog.initOwner(stage);
            textInputDialog.showAndWait();
            editProductInfo(product, field, textInputDialog.getEditor().getText());
        });
        contextMenu.getItems().add(editRequest);
        productFeaturesList.setContextMenu(contextMenu);
        productFeaturesList.getItems().add("Price: " + product.getPrice());
        productFeaturesList.getItems().add("Category: " + product.getCategory().getName());
        productFeaturesList.getItems().add("Company: " + product.getCompany().getName());
        for (String feature : product.getFeaturesMap().keySet()) {
            productFeaturesList.getItems().add(feature + ": " + product.getFeaturesMap().get(feature));
        }
        productFeaturesList.setVisible(true);
    }

    private void editProductInfo(Product product, String field, String newField) {
        user.editProduct(product, field, newField);
        System.out.println(field + " successfully changed to " + newField + "\nManager must confirm");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(field + " successfully changed to " + newField + "\nManager must confirm");
        alert.show();
        statusText.setText("Status: " + product.getProductState());
    }
}
