package Controller.Graphic;

import Controller.console.BossProcessor;
import Controller.console.Processor;
import com.sun.org.apache.bcel.internal.classfile.Code;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.*;
import model.request.OffRequest;
import model.request.ProductRequest;
import model.request.Request;
import model.request.SellerRequest;

import java.io.File;
import java.text.ParseException;

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
    public ListView requestsListView;
    public Text requestIdOff;
    public Text requestIdProduct;
    public Text requestIdSeller;
    public Text userNameSeller;
    public Text firstNameSeller;
    public Text lastNameSeller;
    public Text companyNameSeller;
    public Text offId;
    public Text productId;
    ObservableList<String> users;
    ObservableList<String> products;
    ObservableList<String> codedDiscounts;
    ObservableList<String> categories;
    ObservableList<String> createCategoryFeatures;
    ObservableList<String> requests;
    public Pane userInfoPane;
    public Pane productInfoPane;
    public Pane codedDiscountInfoPane;
    public Pane categoryInfoPane;
    public Pane changeFeaturePane;
    public Pane createCategoryPane;
    public Pane sellerRequestPane;
    public Pane offRequestPane;
    public Pane productRequestPane;
    private String selectedFeature;
    private Request selectedRequest;
    private Category selectedCategory;
    private CodedDiscount selectedCodedDiscount;
    private User selectedUser;
    private Product selectedProduct;
    public ImageView profilePhoto;
    private Manager user;

    public ManagerMenuController() {
        users = FXCollections.observableArrayList();
        products = FXCollections.observableArrayList();
        codedDiscounts = FXCollections.observableArrayList();
        categories = FXCollections.observableArrayList();
        createCategoryFeatures = FXCollections.observableArrayList();
        requests = FXCollections.observableArrayList();
    }

    public void showUserInfo() {
        String userName = usersListView.getSelectionModel().getSelectedItem().toString();
        selectedUser = User.getUserByUserName(userName);
        showUser(selectedUser);
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
        String temp =productNameAndId.split(" /")[1].trim();
        String productId = temp.substring(0, temp.length()-1);
        selectedProduct = Product.getAllProductById(productId);
        showProduct(selectedProduct);
    }

    public void showProduct(Product product) {
        productName.setText(product.getName());
        productPrice.setText("" + product.getPrice());
        productScore.setText("" + product.getScore());
        productInfoPane.setVisible(true);
    }

    public void showCodedDiscountInfo() {
        String discountCode = codedDiscountListView.getSelectionModel().getSelectedItems().toString();
        selectedCodedDiscount = CodedDiscount.getDiscountById(discountCode);
        showCodedDiscount(selectedCodedDiscount);
    }

    public void showCodedDiscount(CodedDiscount codedDiscount) {
        //TODO : set date
        discountCode.setPromptText(codedDiscount.getDiscountCode());
        discountAmount.setPromptText("" + codedDiscount.getDiscountAmount());
        repeat.setPromptText("" + codedDiscount.getRepeat());
        codedDiscountInfoPane.setVisible(true);
    }

    public void showRequestInfo() {
        String requestId = requestsListView.getSelectionModel().getSelectedItems().toString();
        selectedRequest = Request.getRequestById(requestId);
        showRequest(selectedRequest);
    }

    public void showRequest(Request request) {
        sellerRequestPane.setVisible(false);
        offRequestPane.setVisible(false);
        productRequestPane.setVisible(false);
        if (request instanceof ProductRequest) {
            ProductRequest productRequest = (ProductRequest) request;
            requestIdProduct.setText(request.getRequestId());
            productId.setText(productRequest.getProduct().getProductId());
            productRequestPane.setVisible(true);
        } else if (request instanceof OffRequest) {
            OffRequest offRequest = (OffRequest) request;
            requestIdOff.setText(request.getRequestId());
            offId.setText(offRequest.getOff().getOffId());
            offRequestPane.setVisible(true);
        } else if (request instanceof SellerRequest) {
            SellerRequest sellerRequest = (SellerRequest) request;
            requestIdSeller.setText(request.getRequestId());
            userNameSeller.setText(sellerRequest.getSeller().getUsername());
            firstNameSeller.setText(sellerRequest.getSeller().getUserPersonalInfo().getFirstName());
            lastNameSeller.setText(sellerRequest.getSeller().getUserPersonalInfo().getLastName());
            companyNameSeller.setText(sellerRequest.getSeller().getCompany().getName());
            sellerRequestPane.setVisible(true);
        }
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

    public void closeRequestInfo() {
        sellerRequestPane.setVisible(false);
        offRequestPane.setVisible(false);
        productRequestPane.setVisible(false);
    }

    public void acceptRequest() throws InvalidCommandException, ParseException {
        ((Manager) Processor.user).acceptRequest(selectedRequest);
        closeRequestInfo();
        updateRequestListView();
    }

    public void declineRequest() {
        ((Manager) Processor.user).declineRequest(selectedRequest);
        closeRequestInfo();
        updateRequestListView();
    }

    public void updateRequestListView() {
        requests.clear();
        for (Request request : Request.getAllRequests()) {
            requests.add(request.getRequestId());
        }
        requestsListView.setItems(requests);
    }

    public void deleteUser() {
        ((Manager) Processor.user).deleteUser(selectedUser);
        closeUserInfo();
        updateUsersListView();
    }

    public void updateUsersListView() {
        users.clear();
        for (User user : User.allUsers) {
            users.add(user.getUsername());
        }
        usersListView.setItems(users);
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

    public void createCategory() {

    }


    public void createCodedDiscount() {

    }

    public void editCodedDiscount() {

    }

    public void removeCodedDiscount() {
        ((Manager) Processor.user).removeCodedDiscount(selectedCodedDiscount);
        closeCodedDiscountInfo();
        updateCodedDiscountListView();
    }

    public void updateCodedDiscountListView() {
        codedDiscounts.clear();
        for (CodedDiscount codedDiscount : CodedDiscount.allCodedDiscount) {
            codedDiscounts.add(codedDiscount.getDiscountCode());
        }
        codedDiscountListView.setItems(codedDiscounts);
    }

    public void removeCategory() {
        ((Manager) Processor.user).removeCategory(selectedCategory);
        closeCategoryInfo();
        updateCategoryListView();
    }

    public void updateCategoryListView() {
        categories.clear();
        for (Category category : Category.getAllCategories()) {
            categories.add(category.getName());
        }
        categoryListView.setItems(categories);
    }

    public void changeFeature() {
        //TODO

        changeFeaturePane.setVisible(false);
    }


    @FXML
    public void initialize() {
        user = (Manager) bossProcessor.getUser();
        UserPersonalInfo userPersonalInfo = user.getUserPersonalInfo();
        firstName.setText(userPersonalInfo.getFirstName());
        lastName.setText(userPersonalInfo.getLastName());
        email.setText(userPersonalInfo.getEmail());
        password.setText(userPersonalInfo.getPassword());
        phoneNumber.setText(userPersonalInfo.getPhoneNumber());
        if (user.getImagePath() != null) {
            profilePhoto.setImage(new Image("file:" + user.getImagePath()));
        }
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
        for (Request request : Request.getAllRequests()) {
            requests.add(request.getRequestId());
        }
        requestsListView.setItems(requests);
    }

    public void update() {
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        bossProcessor.editField(userPersonalInfo);
    }

    public void browsePhotoUser() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            user.setImagePath(file.getAbsolutePath());
            profilePhoto.setImage(new Image(file.toURI().toString()));
        }
    }
}
