package Controller.Graphic;

import Controller.console.SellerProcessor;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import model.*;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SellerMenuController extends Controller {
    public TextField nameNewProduct;
    public TextField companyNewCompany;
    public TextField priceNewProduct;
    public TextField amountNewProduct;
    public TextField categoryNewProduct;
    public Text invalidIdOff;
    public TextField offIdTextField;
    public Text offSellerText;
    public ListView offProductsListView;
    public Text offAmountText;
    public DatePicker offStartTimeDate;
    public DatePicker offEndTimeDate;
    public Slider offAmount;
    public Label offAmountLabel;
    SellerProcessor sellerProcessor = SellerProcessor.getInstance();
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
    public Text offState;
    public Text productsOffText;
    public ListView<CheckBox> offProducts;
    public ChoiceBox<String> categoryChoiceBox;
    public ListView<HBox> categoryFeaturesListView;
    public Label manageNameLabel;
    public Label managePriceLabel;
    public Label manageProductCategoryLabel;
    public TextField manageNameTextField;
    public TextField managePriceTextField;
    public ChoiceBox<String> manageProductCompanyChoiceBox;
    public Label manageCompanyLabel;
    public Button applyChangesButton;

    private Seller user;
    private String productPhotoPath;
    private Product product;

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
        initializeAddOff();
        initializeAddProduct();
    }

    private void initializeAddProduct() {
        for (Category category : Category.getAllCategories()) {
            categoryChoiceBox.getItems().add(category.getName());
        }
        categoryChoiceBox.setOnAction(event -> {
            categoryFeaturesListView.getItems().clear();
            categoryFeaturesListView.setVisible(true);
            Category category = Category.getCategoryByName(categoryChoiceBox.getValue());
            for (String feature : category.getFeatures()) {
                HBox hBox = new HBox();
                hBox.getChildren().add(new Label(feature + ": "));
                hBox.getChildren().add(new TextField());
                categoryFeaturesListView.getItems().add(hBox);
            }
        });
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
        if (categoryChoiceBox.getValue() == null) {
            invalidCategory.setVisible(true);
        } else {
            invalidCategory.setVisible(false);
            Category category = Category.getCategoryByName(categoryChoiceBox.getValue());
            HashMap<String, String> features = new HashMap<>();
            for (HBox item : categoryFeaturesListView.getItems()) {
                features.put(((Label) item.getChildren().get(0)).getText().split(":")[0], ((TextField) item.getChildren().get(1)).getText());
            }
            try {
                sellerProcessor.addNewProduct(nameNewProduct.getText(), companyNewCompany.getText(), category.getName(), priceNewProduct.getText(),
                        amountNewProduct.getText(), features, productPhotoPath);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Product added successfully");
                alert.setContentText("Waiting for manager to confirm");
                alert.showAndWait();
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

    public void editProduct() {
        String name = manageNameTextField.getText();
        String price = managePriceTextField.getText();
        String companyName = manageProductCompanyChoiceBox.getValue();
        for (String item : productFeaturesList.getItems()) {
            String[] temp = item.split(":");
            String featureName = temp[0].trim();
            String feature = temp[1].trim();
            if (!product.getFeaturesMap().get(featureName).equalsIgnoreCase(feature)) {
                user.editProduct(product, featureName, feature);
            }
        }
        if (!name.equals(product.getName())) {
            user.editProduct(product, "name", name);
        }
        if (!price.equals(String.valueOf(product.getPrice()))) {
            user.editProduct(product, "price", price);
        }
        if (!companyName.equals(product.getCompany().getName())) {
            user.editProduct(product, "company", companyName);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Changes sent to manager");
        alert.showAndWait();
        statusText.setText("Status: " + product.getProductState());
    }

    private void initializeManageProduct(Product product) {
        manageNameTextField.setText(product.getName());
        managePriceTextField.setText(String.valueOf(product.getPrice()));
        manageProductCategoryLabel.setText("Category: " + product.getCategory().getName());
        for (Company company : Company.getAllCompanies()) {
            manageProductCompanyChoiceBox.getItems().add(company.getName());
        }
        manageProductCompanyChoiceBox.setValue(product.getCompany().getName());

        manageProductCompanyChoiceBox.setVisible(true);
        managePriceTextField.setVisible(true);
        manageNameTextField.setVisible(true);
        manageProductCategoryLabel.setVisible(true);
        manageNameLabel.setVisible(true);
        managePriceLabel.setVisible(true);
        manageCompanyLabel.setVisible(true);

    }

    public void editCell(ListView.EditEvent<String> stringEditEvent) {
        productFeaturesList.getItems().set(productFeaturesList.getEditingIndex(), stringEditEvent.getNewValue());
    }

    public void showProduct() {
        String id = productIdTextField.getText();
        if (sellerProcessor.checkProduct(id)) {
            buyersListView.getItems().clear();
            product = user.getProductById(id);
            initializeManageProduct(product);
            showProductFeaturesList(product);

            if (product.getImagePath() != null) {
                productPhoto.setImage(new Image("file:" + product.getImagePath()));
            } else {
                productPhoto.setImage(new Image("file:src/main/resources/product.jpg"));
            }

            for (Buyer buyer : user.getBuyers(product.getProductId())) {
                buyersListView.getItems().add(buyer.toString());
            }

            statusText.setText("Status: " + product.getProductState());

            buyerText.setVisible(true);
            buyersListView.setVisible(true);
            productPhoto.setVisible(true);
            statusText.setVisible(true);
            applyChangesButton.setVisible(true);
            invalidIdProduct.setVisible(false);

        } else {
            invalidIdProduct.setVisible(true);
        }
    }

    private void showProductFeaturesList(Product product) {
        productFeaturesList.getItems().clear();
        productFeaturesList.setEditable(true);
        productFeaturesList.setCellFactory(TextFieldListCell.forListView());
        for (String feature : product.getFeaturesMap().keySet()) {
            productFeaturesList.getItems().add(feature + ": " + product.getFeaturesMap().get(feature));
        }
        productFeaturesList.setVisible(true);
    }

    public void showOff() {
        String id = offIdTextField.getText();
        if (user.hasOffWithId(id)) {
            invalidIdOff.setVisible(false);
            offProductsListView.getItems().clear();
            Off off = user.getOffById(id);
            offSellerText.setText("Seller: " + off.getSeller().getUsername());
            offAmountText.setText("Discount: " + off.getDiscountAmount() + "%");
            offState.setText("State: " + off.getOffState());
            for (Product product : off.getProducts()) {
                offProductsListView.getItems().add("Name: " + product.getName() + "  Price: " + product.getPrice() + "  ID: " + product.getProductId() + "  Category: " + product.getCategory().getName());
            }
            setMenuOffProductListView(off);
            offProductsListView.setVisible(true);
            offSellerText.setVisible(true);
            offAmountText.setVisible(true);
            offState.setVisible(true);
            productsOffText.setVisible(true);
        } else {
            invalidIdOff.setVisible(true);
        }
    }

    private void setMenuOffProductListView(Off off) {
        ContextMenu contextMenu = new ContextMenu();
        Menu editRequest = new Menu("edit request");
        editRequest.setOnAction(event -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setHeaderText("Discount amount: ");
            textInputDialog.setTitle("Edit Request");
            textInputDialog.initModality(Modality.WINDOW_MODAL);
            textInputDialog.initOwner(stage);
            textInputDialog.showAndWait();
            editOffAmount(off, textInputDialog.getEditor().getText());
        });
        contextMenu.getItems().add(editRequest);
        offAmountText.setOnContextMenuRequested(event -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setHeaderText("Discount amount: ");
            textInputDialog.setTitle("Edit Request");
            textInputDialog.initModality(Modality.WINDOW_MODAL);
            textInputDialog.initOwner(stage);
            Optional<String> result = textInputDialog.showAndWait();
            if (result.isPresent()) {
                editOffAmount(off, textInputDialog.getEditor().getText());
            }
        });
    }

    private void editOffAmount(Off off, String text) {
        user.editOff(off, "discountAmount", text);
        offState.setText("State: " + off.getOffState());
    }

    private void initializeAddOff() {
        offAmount.valueProperty().addListener((observableValue, oldValue, newValue) -> offAmountLabel.textProperty().setValue(newValue.intValue() + "%"));
        for (Product product : user.getProductsNumber().keySet()) {
            CheckBox checkBox = new CheckBox();
            checkBox.setText("ID: " + product.getProductId() + "  Name: " + product.getName() + "  Price: " + product.getPrice() + "  Category: " + product.getCategory().getName());
            offProducts.getItems().add(checkBox);
        }
    }


    public void addOff() {
        String startTime = offStartTimeDate.getEditor().getText();
        String endTime = offEndTimeDate.getEditor().getText();
        double amount = Integer.parseInt(offAmountLabel.getText().substring(0, offAmountLabel.getText().length() - 1));
        ArrayList<String> productIds = new ArrayList<>();
        for (CheckBox item : offProducts.getItems()) {
            if (item.isSelected()) {
                productIds.add(item.getText().split(" ")[1]);
            }
        }
        try {
            String result = sellerProcessor.addOff(startTime, endTime, amount, productIds);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(result);
            alert.showAndWait();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
