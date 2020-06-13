package Controller.Graphic;

import Controller.console.BossProcessor;
import com.sun.org.apache.bcel.internal.classfile.Code;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.*;

public class ManagerMenuController extends Controller {
    BossProcessor bossProcessor = BossProcessor.getInstance();
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField phoneNumber;
    public Text userName2;
    public Text firstName2;
    public Text lastName2;
    public Text email2;
    public Text phoneNumber2;
    public Text productName;
    public Text productPrice;
    public Text productScore;
    public TextField userNameCreateManager;
    public TextField firstNameCreateManager;
    public TextField lastNameCreateManager;
    public TextField emailCreateManager;
    public TextField passwordCreateManager;
    public TextField phoneCreateManager;
    public TextField discountCode;
    public TextField startYear;
    public TextField startMonth;
    public TextField startDay;
    public TextField endYear;
    public TextField endMonth;
    public TextField endDay;
    public TextField discountAmount;
    public TextField repeat;
    public TextField createStartYear;
    public TextField createStartMonth;
    public TextField createStartDay;
    public TextField createEndYear;
    public TextField createEndMonth;
    public TextField createEndDay;
    public TextField createDiscountAmount;
    public TextField createRepeat;
    public TextField categoryName;
    public TextField newFeature;
    public TextField createCategoryName;
    public TextField createCategoryAddFeature;
    public ListView usersListView;
    public ListView productsListView;
    public ListView codedDiscountListView;
    public ListView categoryListView;
    public ListView featuresListView;
    public ListView createCategoryFeaturesListView;
    ObservableList<String> users;
    ObservableList<String> products;
    ObservableList<String> codedDiscounts;
    ObservableList<String> categories;
    ObservableList<String> createCategoryFeatures;
    public Pane userInfoPane;
    public Pane productInfoPane;
    public Pane codedDiscountInfoPane;
    public Pane categoryInfoPane;
    public Pane changeFeaturePane;
    public Pane createCategoryPane;
    public String selectedFeature;

    public ManagerMenuController() {
        users = FXCollections.observableArrayList();
        products = FXCollections.observableArrayList();
        codedDiscounts = FXCollections.observableArrayList();
        categories = FXCollections.observableArrayList();
        createCategoryFeatures = FXCollections.observableArrayList();
    }

    public void showUserInfo() {
        String userName = usersListView.getSelectionModel().getSelectedItem().toString();
        showUser(User.getUserByUserName(userName));
    }

    public void showUser(User user) {
        userName2.setText(user.getUsername());
        firstName2.setText(user.getUserPersonalInfo().getFirstName());
        lastName2.setText(user.getUserPersonalInfo().getLastName());
        email2.setText(user.getUserPersonalInfo().getEmail());
        phoneNumber2.setText(user.getUserPersonalInfo().getPhoneNumber());
        userInfoPane.setVisible(true);

    }

    public void showProductInfo() {
        String productNameAndId = productsListView.getSelectionModel().getSelectedItems().toString();
        String productId = productNameAndId.split(" / ")[1];
        Product product = Product.getProductById(productId);
        showProduct(product);
    }

    public void showProduct(Product product) {
        productName.setText(product.getName());
        productPrice.setText("" + product.getPrice());
        productScore.setText("" + product.getScore());
        productInfoPane.setVisible(true);
    }

    public void showCodedDiscountInfo() {
        String discountCode = codedDiscountListView.getSelectionModel().getSelectedItems().toString();
        CodedDiscount codedDiscount = CodedDiscount.getDiscountById(discountCode);
        showCodedDiscount(codedDiscount);
    }

    public void showCodedDiscount(CodedDiscount codedDiscount) {
        //TODO : set date
        discountCode.setPromptText(codedDiscount.getDiscountCode());
        discountAmount.setPromptText("" + codedDiscount.getDiscountAmount());
        repeat.setPromptText("" + codedDiscount.getRepeat());
        codedDiscountInfoPane.setVisible(true);
    }

    public void showCategoryInfo() {
        String categoryName = categoryListView.getSelectionModel().getSelectedItems().toString();
        Category category = Category.getCategoryByName(categoryName);
        showCategory(category);
    }

    public void showCategory(Category category) {
        categoryName.setPromptText(category.getName());
        ObservableList<String> features = FXCollections.observableArrayList();
        for (String feature : category.getFeatures()) {
            features.add(feature);
        }
        featuresListView.setItems(features);
        createCategoryPane.setVisible(false);
        categoryInfoPane.setVisible(true);
    }

    public void showChangeToPane() {
        selectedFeature = featuresListView.getSelectionModel().getSelectedItems().toString();
        changeFeaturePane.setVisible(true);

    }

    public void createManagerProfile() {

    }

    public void closeProductInfo() {
        productInfoPane.setVisible(false);
    }


    public void closeUserInfo() {
        userInfoPane.setVisible(false);
    }

    public void closeCodedDiscountInfo() {
        codedDiscountInfoPane.setVisible(false);
    }

    public void closeCategoryInfo() {
        categoryInfoPane.setVisible(false);
        createCategoryPane.setVisible(true);
    }

    public void closeChangeTo() {
        changeFeaturePane.setVisible(false);
    }

    public void deleteUser() {

    }

    public void editCategory() {

    }

    public void createAddFeature() {
        String feature = createCategoryAddFeature.getText();
        createCategoryFeatures.add(feature);
        createCategoryFeaturesListView.setItems(createCategoryFeatures);
        createCategoryAddFeature.clear();
    }

    public void createRemoveFeature() {
        String selectedFeature = createCategoryFeaturesListView.getSelectionModel().getSelectedItems().toString();
        createCategoryFeatures.remove(selectedFeature);
        createCategoryFeaturesListView.setItems(createCategoryFeatures);
    }

    public void createCategory(){

    }


    public void createCodedDiscount() {

    }

    public void editCodedDiscount() {

    }

    public void removeCodedDiscount() {

    }

    public void removeCategory() {

    }

    public void changeFeature() {
        //TODO

        changeFeaturePane.setVisible(false);
    }


    @FXML
    public void initialize() {
        UserPersonalInfo userPersonalInfo = bossProcessor.getUser().getUserPersonalInfo();
        firstName.setText(userPersonalInfo.getFirstName());
        lastName.setText(userPersonalInfo.getLastName());
        email.setText(userPersonalInfo.getEmail());
        password.setText(userPersonalInfo.getPassword());
        phoneNumber.setText(userPersonalInfo.getPhoneNumber());
        for (User user : User.allUsers) {
            users.add(user.getUsername());
        }
        usersListView.setItems(users);
        for (Product product : Product.allProductsInList) {
            products.add(product.getName() + " / " + product.getProductId());
        }
        productsListView.setItems(products);
        for (CodedDiscount discount : CodedDiscount.allCodedDiscount) {
            codedDiscounts.add(discount.getDiscountCode());
        }
        codedDiscountListView.setItems(codedDiscounts);
        for (Category category : Category.getAllCategories()) {
            categories.add(category.getName());
        }
        categoryListView.setItems(categories);
    }

    /*public void update() {
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        bossProcessor.editField(userPersonalInfo);
    }*/
}
