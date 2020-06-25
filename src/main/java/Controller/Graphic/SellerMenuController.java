package Controller.Graphic;

import Controller.console.SellerProcessor;
import View.graphic.ProductPanelWindow;
import View.graphic.ProductWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.*;

import java.io.File;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SellerMenuController extends Controller {
    public Text companyText;
    public Text companyInfoText;
    public TextField nameNewProduct;
    public TextField priceNewProduct;
    public TextField amountNewProduct;
    public Text offSellerText;
    public ListView<CheckBox> offProductsListView;
    public DatePicker offStartTimeDate;
    public DatePicker offEndTimeDate;
    public Slider offAmount;
    public Label offAmountLabel;
    public Label balance;
    public ChoiceBox<String> productIdChoiceBox;
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField phoneNumber;
    public TextField amountTextField;
    public Text invalidCategory;
    public Text usernameText;
    public ImageView profilePhoto;
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
    public ChoiceBox<String> offIdChoiceBox;
    public Button applyOffChangesButton;
    public Label manageOffStartTimeLabel;
    public Label manageOffEndTimeLabel;
    public DatePicker manageOffStartTime;
    public DatePicker manageOffEndTime;
    public Text manageOffAmountText;
    public Slider manageOffAmount;
    public Label manageOffAmountLabel;
    public ListView<String> orders;
    public ListView<String> order;
    public ImageView closeButton;
    public ChoiceBox<String> existingProductsIds;
    public ChoiceBox<String> addProductCompanyChoiceBox;
    public Button manageProductBrowsePhotoButton;

    SellerProcessor sellerProcessor = SellerProcessor.getInstance();
    private Seller user;
    private String productPhotoPath;
    private Product product;
    private Off off;

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
        companyText.setText("company: " + user.getCompany().getName());
        companyInfoText.setText("company info: " + user.getCompany().getInfo());
        balance.setText(Double.toString(user.getBalance()));
        if (user.getImagePath() != null) {
            profilePhoto.setImage(new Image("file:" + user.getImagePath()));
        }
        initializeAddOff();
        initializeAddProduct();
        initializeViewOrders();
        setProductsIds();
        setOffsIds();
    }

    private void initializeViewOrders() {
        for (SellOrder sellOrder : user.getOrders()) {
            orders.getItems().add("id: " + sellOrder.getOrderId() + "\tprice: " + sellOrder.getPayment());
        }
    }

    private void initializeAddProduct() {
        for (Category category : Category.getAllCategories()) {
            categoryChoiceBox.getItems().add(category.getName());
        }
        for (Company company : Company.getAllCompanies()) {
            addProductCompanyChoiceBox.getItems().add(company.getName());
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
        for (Product product1 : Product.getAllProductsInList()) {
            existingProductsIds.getItems().add(product1.getProductId());
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
        String message = sellerProcessor.addExistingProduct(existingProductsIds.getSelectionModel().getSelectedItem(), amountTextField.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void addNewProduct() {
        if (categoryChoiceBox.getValue() == null) {
            invalidCategory.setVisible(true);
            //   Music.getInstance().error();
        } else {
            invalidCategory.setVisible(false);
            Category category = Category.getCategoryByName(categoryChoiceBox.getValue());
            HashMap<String, String> features = new HashMap<>();
            for (HBox item : categoryFeaturesListView.getItems()) {
                features.put(((Label) item.getChildren().get(0)).getText().split(":")[0], ((TextField) item.getChildren().get(1)).getText());
            }
            try {
                sellerProcessor.addNewProduct(nameNewProduct.getText(), addProductCompanyChoiceBox.getSelectionModel().getSelectedItem(), category.getName(), priceNewProduct.getText(),
                        amountNewProduct.getText(), features, productPhotoPath);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Product added successfully");
                alert.setContentText("Waiting for manager to confirm");
                alert.showAndWait();
                //    Music.getInstance().confirmation();
            } catch (InvalidCommandException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void browsePhotoUser() {
        //  Music.getInstance().open();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            user.setImagePath(file.getAbsolutePath());
            profilePhoto.setImage(new Image(file.toURI().toString()));
        }
    }

    public void browsePhotoProduct() {
        // Music.getInstance().open();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            productPhotoPath = file.getAbsolutePath();
        }
    }

    public void browsePhotoManageProduct() {
        // Music.getInstance().open();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            product.setImagePath(file.getAbsolutePath());
        }
        showProductImage();
    }

    public void editProduct() {
        boolean change = false;
        //  Music.getInstance().confirmation();
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
            change = true;
            user.editProduct(product, "name", name);
        }
        if (!price.equals(String.valueOf(product.getPrice()))) {
            change = true;
            user.editProduct(product, "price", price);
        }
        if (!companyName.equals(product.getCompany().getName())) {
            change = true;
            user.editProduct(product, "company", companyName);
        }
        if (change) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Changes sent to manager");
            alert.showAndWait();
        }
        statusText.setText("Status: " + product.getProductState());
    }

    private void initializeManageProduct(Product product) {
        manageProductCompanyChoiceBox.getItems().clear();
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
        manageProductBrowsePhotoButton.setVisible(true);


    }

    private void setProductsIds() {
        for (Product product1 : user.getProductsNumber().keySet()) {
            productIdChoiceBox.getItems().add(product1.getProductId());
        }
    }

    private void setOffsIds() {
        for (Off off : user.getOffs()) {
            offIdChoiceBox.getItems().add(off.getOffId());
        }
    }

    public void editCell(ListView.EditEvent<String> stringEditEvent) {
        productFeaturesList.getItems().set(productFeaturesList.getEditingIndex(), stringEditEvent.getNewValue());
    }

    private void showProductImage() {
        if (product.getImagePath() != null) {
            productPhoto.setImage(new Image("file:" + product.getImagePath()));
        } else {
            productPhoto.setImage(new Image("file:src/main/resources/product.jpg"));
        }
    }

    public void showProduct() {

        String id = (productIdChoiceBox.getSelectionModel().getSelectedItem());
        // Music.getInstance().open();
        buyersListView.getItems().clear();

        product = user.getProductById(id);
        initializeManageProduct(product);
        showProductFeaturesList(product);
        showProductImage();

        for (Buyer buyer : user.getBuyers(product.getProductId())) {
            buyersListView.getItems().add(buyer.toString());
        }

        statusText.setText("Status: " + product.getProductState());

        buyerText.setVisible(true);
        buyersListView.setVisible(true);
        productPhoto.setVisible(true);
        statusText.setVisible(true);
        applyChangesButton.setVisible(true);
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

    private void initializeManageOff(Off off) {
        offProductsListView.getItems().clear();

        offSellerText.setText("Seller: " + off.getSeller().getUsername());
        offState.setText("State: " + off.getOffState());
        manageOffAmount.setValue(off.getDiscountAmount());
        manageOffAmountLabel.setText((int) off.getDiscountAmount() + "%");
        manageOffAmount.valueProperty().addListener((observableValue, oldValue, newValue) -> manageOffAmountLabel.textProperty().setValue(newValue.intValue() + "%"));
        for (Product product : user.getProductsNumber().keySet()) {
            CheckBox checkBox = new CheckBox();
            checkBox.setText("ID: " + product.getProductId() + "  Name: " + product.getName() + "  Price: " + product.getPrice() + "  Category: " + product.getCategory().getName());
            if (off.hasProduct(product)) {
                checkBox.setSelected(true);
            }
            offProductsListView.getItems().add(checkBox);

        }
        manageOffStartTime.setValue(off.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        manageOffEndTime.setValue(off.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public void showOff() {
        String id = offIdChoiceBox.getSelectionModel().getSelectedItem();
        //Music.getInstance().open();
        off = user.getOffById(id);
        initializeManageOff(off);

        offProductsListView.setVisible(true);
        offSellerText.setVisible(true);
        offState.setVisible(true);
        manageOffAmountLabel.setVisible(true);
        manageOffAmountText.setVisible(true);
        productsOffText.setVisible(true);
        manageOffEndTimeLabel.setVisible(true);
        manageOffStartTimeLabel.setVisible(true);
        manageOffStartTime.setVisible(true);
        manageOffEndTime.setVisible(true);
        manageOffAmount.setVisible(true);
        applyOffChangesButton.setVisible(true);
    }

    public void editOff(ActionEvent event) {
        //Music.getInstance().confirmation();
        boolean change = false;
        String amountString = manageOffAmountLabel.getText();
        double amount = Double.parseDouble(amountString.substring(0, amountString.length() - 1));
        String startTime = manageOffStartTime.getEditor().getText();
        String endTime = manageOffEndTime.getEditor().getText();
        for (CheckBox item : offProductsListView.getItems()) {
            String id = item.getText().split(" ")[1];
            if (item.isSelected()) {
                if (!off.hasProduct(user.getProductById(id))) {
                    change = true;
                    user.editOff(off, "addProduct", id);
                }
            } else {
                if (off.hasProduct(user.getProductById(id))) {
                    change = true;
                    user.editOff(off, "removeProduct", id);
                }
            }
        }
        if (amount != off.getDiscountAmount()) {
            change = true;
            user.editOff(off, "discountAmount", String.valueOf(amount));
        }
        Date dateStart = new Date(startTime);
        Date dateEnd = new Date(endTime);
        if (!dateStart.equals(off.getStartTime())) {
            change = true;
            user.editOff(off, "startTime", startTime);
        }
        if (!dateEnd.equals(off.getEndTime())) {
            change = true;
            user.editOff(off, "endTime", endTime);
        }
        if (change) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Changes sent to manager");
            alert.showAndWait();
        }
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
        //  Music.getInstance().confirmation();
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

    public void showOrder() {
        order.getItems().clear();
        if (orders.getSelectionModel().getSelectedItem() != null) {
            String orderId = orders.getSelectionModel().getSelectedItem().split("\t")[0].split(" ")[1];
            SellOrder sellOrder = (SellOrder) Order.getOrderById(orderId);
            Product product = sellOrder.getProduct();
            order.getItems().add("Product: " + product.getName() + "\tNumber: " + sellOrder.getNumber());
            order.getItems().add("Payment: " + sellOrder.getPayment());
            order.getItems().add("Delivery Status: " + sellOrder.getDeliveryStatus().toString());
            order.getItems().add("Date: " + sellOrder.getDate().toString());
            order.getItems().add("Off Amount: " + sellOrder.getOffAmount());
            order.setVisible(true);
            closeButton.setVisible(true);
        }
    }

    public void closeOrder() {
        order.setVisible(false);
        closeButton.setVisible(false);
    }

    public void goBack() {
        try {
            if (parent instanceof ProductWindow) {
                ProductWindow.getInstance().setProduct(super.product, ProductPanelWindow.getInstance());
                ProductWindow.getInstance().start(stage);
            } else {
                parent.start(stage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}