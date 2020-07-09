package Controller.Graphic;

import Controller.console.BossProcessor;
import Controller.console.Processor;
import View.graphic.ProductPanelWindow;
import View.graphic.ProductWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.*;
import model.request.*;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManagerMenuController extends Controller {
    BossProcessor bossProcessor = BossProcessor.getInstance();
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField phoneNumber;
    public ImageView userProfilePhoto;
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
    public Text discountCode;
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
    public TextField changedFeature;
    public TextField createCategoryName;
    public TextField createCategoryFeature;
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
    public Text requestIdEditOff;
    public Text requestIdEditProduct;
    public Text editProductField;
    public Text editOffField;
    public Text editProductNewValue;
    public Text editOffNewValue;
    public Text editProductId;
    public Text editOffId;
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
    public Pane editOffRequestPane;
    public Pane editProductRequestPane;
    private String selectedFeature;
    private Request selectedRequest;
    private Category selectedCategory;
    private CodedDiscount selectedCodedDiscount;
    private User selectedUser;
    private Product selectedProduct;
    public ImageView profilePhoto;
    public ImageView productImage;
    public ImageView createManagerPhoto;
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
        if (usersListView.getSelectionModel().getSelectedItem() == null) return;
        Music.getInstance().open();
        String userName = usersListView.getSelectionModel().getSelectedItem().toString();
        selectedUser = bossProcessor.getUserFromController(userName);
        if (selectedUser == null) return;
        showUser(selectedUser);
    }

    public void showUser(User user) {
        setUserImage(user, userProfilePhoto);
        userName2.setText(user.getUsername());
        firstName2.setText(user.getUserPersonalInfo().getFirstName());
        lastName2.setText(user.getUserPersonalInfo().getLastName());
        email2.setText(user.getUserPersonalInfo().getEmail());
        phoneNumber2.setText(user.getUserPersonalInfo().getPhoneNumber());
        userInfoPane.setVisible(true);

    }

    public void showProductInfo() {
        if (productsListView.getSelectionModel().getSelectedItem() == null) return;
        Music.getInstance().open();
        String productNameAndId = productsListView.getSelectionModel().getSelectedItem().toString();
        String productId = productNameAndId.split(" / ")[1].trim();
        selectedProduct = bossProcessor.getProductFromController(productId);
        if (selectedProduct == null) return;
        showProduct(selectedProduct);

    }

    public void showProduct(Product product) {

        setProductImage(product, productImage);

        productName.setText(product.getName());
        productPrice.setText("" + product.getPrice());
        productScore.setText("" + product.getScore().getAvgScore());
        productInfoPane.setVisible(true);
    }

    public void showCodedDiscountInfo() {
        if (codedDiscountListView.getSelectionModel().getSelectedItem() == null) return;
        Music.getInstance().open();
        String discountCodePrime = codedDiscountListView.getSelectionModel().getSelectedItem().toString();
//        Pattern pattern = Pattern.compile("\\[(.+)\\]");
//        Matcher matcher = pattern.matcher(discountCodePrime);
//        if (matcher.matches()) {
        selectedCodedDiscount = bossProcessor.getCodedDiscountFromController(discountCodePrime);
        if (selectedCodedDiscount == null) return;
        showCodedDiscount(selectedCodedDiscount);
//        }
    }

    public void showCodedDiscount(CodedDiscount codedDiscount) {
        //TODO : set date
        discountCode.setText(codedDiscount.getDiscountCode());
        discountAmount.setPromptText("" + codedDiscount.getDiscountAmount());
        repeat.setPromptText("" + codedDiscount.getRepeat());
        codedDiscountInfoPane.setVisible(true);
    }

    public void showRequestInfo() {
        if (requestsListView.getSelectionModel().getSelectedItem() == null) return;
        Music.getInstance().open();
        String requestIdPrime = requestsListView.getSelectionModel().getSelectedItem().toString();
//        Pattern pattern = Pattern.compile("\\[(.+)\\]");
//        Matcher matcher = pattern.matcher(requestIdPrime);
//        if (matcher.matches()) {
        selectedRequest = bossProcessor.getRequestFromController(requestIdPrime);
        if (selectedRequest == null) return;
        showRequest(selectedRequest);
//        }

    }

    public void showRequest(Request request) {
        sellerRequestPane.setVisible(false);
        offRequestPane.setVisible(false);
        productRequestPane.setVisible(false);
        editOffRequestPane.setVisible(false);
        editProductRequestPane.setVisible(false);
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
        } else if (request instanceof EditOffRequest) {
            EditOffRequest editOffRequest = (EditOffRequest) request;
            requestIdEditOff.setText(request.getRequestId());
            editOffId.setText(editOffRequest.getOff().getOffId());
            editOffField.setText(editOffRequest.getField());
            editOffNewValue.setText(editOffRequest.getInput());
            editOffRequestPane.setVisible(true);
            System.out.println(editOffRequest.getInput());
        } else if (request instanceof EditProductRequest) {
            EditProductRequest editProductRequest = (EditProductRequest) request;
            requestIdEditProduct.setText(request.getRequestId());
            editProductId.setText(editProductRequest.getProduct().getProductId());
            editProductField.setText(editProductRequest.getField());
            editProductNewValue.setText(editProductRequest.getInput());
            editProductRequestPane.setVisible(true);
            System.out.println(editProductRequest.getInput());
        }
    }

    public void showCategoryInfo() {
        if (categoryListView.getSelectionModel().getSelectedItem() == null) return;
        Music.getInstance().open();
        categoryName.clear();
        newFeature.clear();
        String categoryName = categoryListView.getSelectionModel().getSelectedItem().toString();
        selectedCategory = bossProcessor.getCategoryFromController(categoryName);
        if (selectedCategory == null) return;
        showCategory(selectedCategory);
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
        Music.getInstance().open();
        selectedFeature = featuresListView.getSelectionModel().getSelectedItem().toString();
        if (selectedFeature == null) return;
        changeFeaturePane.setVisible(true);
    }

    public void createManagerProfile() {
        Music.getInstance().confirmation();
        if (!hasEmptyFieldInCreateManager()) {
            ArrayList<String> managerInfo = new ArrayList<>();
            managerInfo.add(userNameCreateManager.getText());
            managerInfo.add(firstNameCreateManager.getText());
            managerInfo.add(lastNameCreateManager.getText());
            managerInfo.add(emailCreateManager.getText());
            managerInfo.add(phoneCreateManager.getText());
            managerInfo.add(passwordCreateManager.getText());
            bossProcessor.createManagerProfileFXML(managerInfo);
        }
        clearCreateManager();
        updateUsersListView();
    }

    private void clearCreateManager() {
        userNameCreateManager.clear();
        firstNameCreateManager.clear();
        lastNameCreateManager.clear();
        emailCreateManager.clear();
        phoneCreateManager.clear();
        passwordCreateManager.clear();
    }

    public boolean hasEmptyFieldInCreateManager() {
        if (userNameCreateManager.getText().isEmpty()) {
            showErrorAlert("please fill the user name field");
            return true;
        }
        if (firstNameCreateManager.getText().isEmpty()) {
            showErrorAlert("please fill the first name field");
            return true;
        }
        if (lastNameCreateManager.getText().isEmpty()) {
            showErrorAlert("please fill the last name field");
            return true;
        }
        if (passwordCreateManager.getText().isEmpty()) {
            showErrorAlert("please fill the password field");
            return true;
        }
        if (emailCreateManager.getText().isEmpty()) {
            showErrorAlert("please fill the email field");
            return true;
        }
        if (phoneCreateManager.getText().isEmpty()) {
            showErrorAlert("please fill the phone field");
            return true;
        }
        return false;
    }

    public void showErrorAlert(String alertMessage) {
        Music.getInstance().error();
        Alert alert = new Alert(Alert.AlertType.ERROR, alertMessage);
        alert.show();
    }

    public void closeProductInfo() {
        Music.getInstance().close();
        productInfoPane.setVisible(false);
    }


    public void closeUserInfo() {
        Music.getInstance().close();
        userInfoPane.setVisible(false);
    }

    public void closeCodedDiscountInfo() {
        Music.getInstance().close();
        codedDiscountInfoPane.setVisible(false);
    }

    public void closeCategoryInfo() {
        Music.getInstance().close();
        categoryInfoPane.setVisible(false);
        createCategoryPane.setVisible(true);
    }

    public void closeChangeTo() {
        Music.getInstance().close();
        changeFeaturePane.setVisible(false);
    }

    public void closeRequestInfo() {
        Music.getInstance().close();
        sellerRequestPane.setVisible(false);
        offRequestPane.setVisible(false);
        productRequestPane.setVisible(false);
        editProductRequestPane.setVisible(false);
        editOffRequestPane.setVisible(false);
    }

    public void acceptRequest() {
        try{
            Music.getInstance().confirmation();
            bossProcessor.acceptRequestFXML(selectedRequest);
        }catch (InvalidCommandException e) {
            showErrorAlert(e.getMessage());
        } catch (ParseException e) {
            showErrorAlert("invalid date");
        }

        /*try {
            ((Manager) Processor.user).acceptRequest(selectedRequest);
            Music.getInstance().confirmation();
        } catch (InvalidCommandException e) {
            showErrorAlert(e.getMessage());
        } catch (ParseException e) {
            showErrorAlert("invalid date");
        }*/
        closeRequestInfo();
        updateRequestListView();
    }

    public void declineRequest() {
        Music.getInstance().confirmation();
        bossProcessor.declineRequestFXML(selectedRequest);
        closeRequestInfo();
        updateRequestListView();
    }

    public void updateRequestListView() {
        requests.clear();
        for (Request request : bossProcessor.requestsFromController()) {
            requests.add(request.getRequestId());
        }
        requestsListView.setItems(requests);
    }


    public void deleteUser() {
        Music.getInstance().confirmation();
        bossProcessor.deleteUserFXML(selectedUser);
        closeUserInfo();
        updateUsersListView();
    }

    public void updateUsersListView() {
        users.clear();
        for (User user : bossProcessor.usersFromController()) {
            users.add(user.getUsername());
        }
        usersListView.setItems(users);
    }

    public void editCategory() {
        Music.getInstance().confirmation();
        if (!categoryName.getText().isEmpty()) {
            bossProcessor.editCategoryFXML(selectedCategory, categoryName.getText());
            categoryName.clear();
            categoryName.setPromptText(selectedCategory.getName());
            updateCategoryListView();
        }
        if (!newFeature.getText().isEmpty()) {
            try {
                for (String s : selectedCategory.getFeatures()) {
                    System.out.println(s);
                }
                bossProcessor.addCategoryFeatureFXML(selectedCategory, newFeature.getText());
            } catch (InvalidCommandException e) {
                showErrorAlert(e.getMessage());
            }
            updateCategoryInfoPaneListView();
            newFeature.clear();
        }
    }

    public void createAddFeature() {
        Music.getInstance().confirmation();
        if (!createCategoryFeature.getText().isEmpty()) {
            String feature = createCategoryFeature.getText();
            if (!hasFeature(feature)) {
                createCategoryFeatures.add(feature);
                createCategoryFeaturesListView.setItems(createCategoryFeatures);
            } else {
                showErrorAlert("already add this feature");
            }
            createCategoryFeature.clear();
        }
    }

    public boolean hasFeature(String feature) {
        if (createCategoryFeatures.isEmpty()) {
            return false;
        }
        for (String categoryFeature : createCategoryFeatures) {
            if (categoryFeature.equals(feature)) {
                return true;
            }
        }
        return false;
    }

    public void createRemoveFeature() {
        Music.getInstance().confirmation();
        String selectedFeature = createCategoryFeaturesListView.getSelectionModel().getSelectedItem().toString();
        createCategoryFeatures.remove(selectedFeature);
        createCategoryFeaturesListView.setItems(createCategoryFeatures);
    }

    public void createCategory() {
        Music.getInstance().confirmation();
        if (createCategoryName.getText().isEmpty()) {
            return;
        }
        ArrayList<String> features = new ArrayList<>();
        for (Object item : createCategoryFeaturesListView.getItems()) {
            features.add(item.toString());
        }
        ((Manager) Processor.user).addCategory(createCategoryName.getText(), features);
        createCategoryName.clear();
        createCategoryFeatures.clear();
        createCategoryFeaturesListView.setItems(createCategoryFeatures);
        updateCategoryListView();
    }


    public void createCodedDiscount() {
        Music.getInstance().confirmation();
        ArrayList<String> codedDiscountInfo = new ArrayList<>();
        codedDiscountInfo.add(createStartDay.getText() + "/" + createStartMonth.getText() + "/" + createStartYear.getText());
        codedDiscountInfo.add(createEndDay.getText() + "/" + createEndMonth.getText() + "/" + createEndYear.getText());
        codedDiscountInfo.add(createDiscountAmount.getText());
        codedDiscountInfo.add(createRepeat.getText());
        try {
            bossProcessor.createCodedDiscount(codedDiscountInfo);
        } catch (InvalidCommandException | ParseException e) {
            showErrorAlert(e.getMessage());
        }
        updateCodedDiscountListView();
        clearCreateCodedDiscount();
    }

    private void clearCreateCodedDiscount() {
        createStartYear.clear();
        createStartMonth.clear();
        createStartDay.clear();
        createEndYear.clear();
        createEndMonth.clear();
        createEndDay.clear();
        createDiscountAmount.clear();
        createRepeat.clear();
    }

    public void editCodedDiscount() {
        Music.getInstance().confirmation();
        String startTime;
        String endTime;
        Date startDate;
        Date endDate;
        String discountAmount;
        String discountRepeat;
        if (!startYear.getText().isEmpty() && !startMonth.getText().isEmpty() && !startDay.getText().isEmpty()) {
            startTime = startDay.getText() + "/" + startMonth.getText() + "/" + startYear.getText();
            try {
                startDate = checkDate(startTime);
            } catch (ParseException e) {
                showErrorAlert("invalid startTime");
                clearCodedDiscountInfo();
                return;
            }
        } else {
            startDate = selectedCodedDiscount.getStartTime();
        }
        if (!endYear.getText().isEmpty() && !endMonth.getText().isEmpty() && !endDay.getText().isEmpty()) {
            endTime = endDay.getText() + "/" + endMonth.getText() + "/" + endYear.getText();
            try {
                endDate = checkDate(endTime);
            } catch (ParseException e) {
                showErrorAlert("invalid endTime");
                clearCodedDiscountInfo();
                return;
            }
        } else {
            endDate = selectedCodedDiscount.getEndTime();
        }
        if (!this.discountAmount.getText().isEmpty()) {
            if (this.discountAmount.getText().matches("^\\d*\\.?\\d*$")) {
                discountAmount = this.discountAmount.getText();
            } else {
                showErrorAlert("invalid discount amount");
                clearCodedDiscountInfo();
                return;
            }
        } else {
            discountAmount = selectedCodedDiscount.getDiscountAmount() + "";
        }
        if (!repeat.getText().isEmpty()) {
            if (repeat.getText().matches("\\d+")) {
                discountRepeat = repeat.getText();
            } else {
                showErrorAlert("invalid repeat");
                clearCodedDiscountInfo();
                return;
            }
        } else {
            discountRepeat = selectedCodedDiscount.getRepeat() + "";
        }
        if (beforeAfterDate(startDate, endDate)) {
            selectedCodedDiscount.setStartTime(startDate);
            selectedCodedDiscount.setEndTime(endDate);
            selectedCodedDiscount.setDiscountAmount(discountAmount);
            selectedCodedDiscount.setRepeat(discountRepeat);
        } else {
            showErrorAlert("startTime should be before endTime");
            return;
        }
    }

    public boolean beforeAfterDate(Date startDate, Date endDate) {
        return startDate.before(endDate);
    }

    public void clearCodedDiscountInfo() {
        startYear.clear();
        startMonth.clear();
        startDay.clear();
        endYear.clear();
        endMonth.clear();
        endDay.clear();
        discountAmount.clear();
        repeat.clear();
    }

    public Date checkDate(String stringDate) throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
        return date;
    }

    public void removeCodedDiscount() {
        Music.getInstance().confirmation();
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

    public void updateProductListView() {
        products.clear();
        for (Product product : Product.allProductsInList) {
            products.add(product.getName() + " / " + product.getAvailableCount());
        }
    }

    public void removeCategory() {
        Music.getInstance().confirmation();
        ((Manager) Processor.user).removeCategory(selectedCategory);
        closeCategoryInfo();
        updateCategoryListView();
    }

    public void removeProduct() {
        Music.getInstance().confirmation();
        bossProcessor.processRemoveProduct(selectedProduct.getProductId());
        closeProductInfo();
        updateProductListView();
    }

    public void updateCategoryListView() {
        categories.clear();
        for (Category category : Category.getAllCategories()) {
            categories.add(category.getName());
        }
        categoryListView.setItems(categories);
    }

    public void changeFeature() {
        Music.getInstance().confirmation();
        if (!changedFeature.getText().isEmpty()) {
            try {
                ((Manager) Processor.user).editFeatureName(selectedCategory, selectedFeature, changedFeature.getText());
            } catch (InvalidCommandException e) {
                showErrorAlert(e.getMessage());
            }
            changeFeaturePane.setVisible(false);
            updateCategoryInfoPaneListView();
        }
    }

    public void removeFeature() {
        Music.getInstance().confirmation();
        ((Manager) Processor.user).deleteFeature(selectedCategory, selectedFeature);
        changedFeature.clear();
        changeFeaturePane.setVisible(false);
        updateCategoryInfoPaneListView();
    }

    public void updateCategoryInfoPaneListView() {
        ObservableList<String> features = FXCollections.observableArrayList();
        for (String feature : selectedCategory.getFeatures()) {
            features.add(feature);
        }
        featuresListView.setItems(features);
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
        setUserImage(user, profilePhoto);
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
        Music.getInstance().confirmation();
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        bossProcessor.editField(userPersonalInfo);
    }

    public void browsePhotoUser() throws IOException {
        browsePhotoUser(user, profilePhoto);
    }

    public void open() {
        Music.getInstance().open();
    }

}
