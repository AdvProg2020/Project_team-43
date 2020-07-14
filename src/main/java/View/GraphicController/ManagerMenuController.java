package View.GraphicController;

import controller.client.BossProcessor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.*;
import model.request.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        init();
        updateUsersListView();
        selectedUser = User.getUserByUserName(userName);
        if (selectedUser == null) {
            return;
        }
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
        init();
        updateProductListView();
        selectedProduct = Product.getAllProductById(productId);
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
        init();
        updateCodedDiscountListView();
        selectedCodedDiscount = CodedDiscount.getDiscountById(discountCodePrime);
        if (selectedCodedDiscount == null) return;
        showCodedDiscount(selectedCodedDiscount);
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
        init();
        updateRequestListView();
        selectedRequest = Request.getRequestById(requestIdPrime);
        if (selectedRequest == null) return;
        showRequest(selectedRequest);
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
        init();
        updateCategoryListView();
        selectedCategory = Category.getCategoryByName(categoryName);
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
//            bossProcessor.createManagerProfileFXML(managerInfo);
            client.createManagerProfile(managerInfo.get(0), managerInfo.get(1), managerInfo.get(2), managerInfo.get(3), managerInfo.get(4), managerInfo.get(5));
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
        Music.getInstance().confirmation();
//            bossProcessor.acceptRequestFXML(selectedRequest);
        String result = client.acceptRequest(selectedRequest.getRequestId());
        if (result.equals("dateException")) {
            showErrorAlert("invalid date");
        }
        closeRequestInfo();
        updateRequestListView();
    }

    public void declineRequest() {
        Music.getInstance().confirmation();
//        bossProcessor.declineRequestFXML(selectedRequest);
        client.declineRequest(selectedRequest.getRequestId());
        closeRequestInfo();
        updateRequestListView();
    }

    public void updateRequestListView() {
        init();
        requests.clear();
        for (Request request : bossProcessor.requestsFromController()) {
            requests.add(request.getRequestId());
        }
        requestsListView.setItems(requests);
    }


    public void deleteUser() {
        Music.getInstance().confirmation();
//        bossProcessor.deleteUserFXML(selectedUser);
        client.deleteUser(selectedUser.getUsername());
        closeUserInfo();
        updateUsersListView();
    }

    public void updateUsersListView() {
        init();
        users.clear();
        for (User user : bossProcessor.usersFromController()) {
            users.add(user.getUsername());
        }
        usersListView.setItems(users);
    }

    public void editCategory() {
        Music.getInstance().confirmation();
        if (!categoryName.getText().isEmpty()) {
//            bossProcessor.editCategoryFXML(selectedCategory, categoryName.getText());
            client.editCategory(selectedCategory.getName(), categoryName.getText());
            categoryName.clear();
            categoryName.setPromptText(selectedCategory.getName());
            updateCategoryListView();
        }
        if (!newFeature.getText().isEmpty()) {

            for (String s : selectedCategory.getFeatures()) {
                System.out.println(s);
            }
//                bossProcessor.addCategoryFeatureFXML(selectedCategory, newFeature.getText());
            String result = client.addCategoryFeature(selectedCategory.getName(), newFeature.getText());
            if (result.equals("invalidCommandException")) {
                showErrorAlert("invalidCommandException");
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
//        bossProcessor.addCategoryFXML(createCategoryName.getText(), features);
        client.createCategory(createCategoryName.getText(), features);
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
//            bossProcessor.createCodedDiscount(codedDiscountInfo);
        String result = client.createCodedDiscount(codedDiscountInfo.get(0), codedDiscountInfo.get(1), codedDiscountInfo.get(2), codedDiscountInfo.get(3));
        if(result.equals("dateException")){
            showErrorAlert("date exception");
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
//            bossProcessor.editCodedDiscountFXML(selectedCodedDiscount, startDate, endDate, discountAmount, discountRepeat);
            client.editCodedDiscount(selectedCodedDiscount.getDiscountCode(), startDate.toString(), endDate.toString(), discountAmount, discountRepeat );
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
//        bossProcessor.removeCodedDiscountFXML(selectedCodedDiscount);
        client.removeCodedDiscount(selectedCodedDiscount.getDiscountCode());
        closeCodedDiscountInfo();
        updateCodedDiscountListView();
    }

    public void updateCodedDiscountListView() {
        init();
        codedDiscounts.clear();
        for (CodedDiscount codedDiscount : bossProcessor.codedDiscountsFromController()) {
            codedDiscounts.add(codedDiscount.getDiscountCode());
        }
        codedDiscountListView.setItems(codedDiscounts);
    }

    public void updateProductListView() {
        init();
        products.clear();
        for (Product product : bossProcessor.productsFromController()) {
            products.add(product.getName() + " / " + product.getAvailableCount());
        }
    }

    public void removeCategory() {
        Music.getInstance().confirmation();
//        bossProcessor.removeCategoryFXML(selectedCategory);
        client.removeCategory(selectedCategory.getName());
        closeCategoryInfo();
        updateCategoryListView();
    }

    public void removeProduct() {
        Music.getInstance().confirmation();
//        bossProcessor.processRemoveProduct(selectedProduct.getProductId());
        closeProductInfo();
        updateProductListView();
    }

    public void updateCategoryListView() {
        init();
        categories.clear();
        for (Category category : bossProcessor.categoriesFromController()) {
            categories.add(category.getName());
        }
        categoryListView.setItems(categories);
    }

    public void changeFeature() {
        Music.getInstance().confirmation();
        if (!changedFeature.getText().isEmpty()) {
            try {
                bossProcessor.changedFeatureFXML(selectedCategory, selectedFeature, changedFeature.getText());
            } catch (InvalidCommandException e) {
                showErrorAlert(e.getMessage());
            }
            changeFeaturePane.setVisible(false);
            updateCategoryInfoPaneListView();
        }
    }

    public void removeFeature() {
        Music.getInstance().confirmation();
        bossProcessor.deleteFeatureFXML(selectedCategory, selectedFeature);
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
        init();
        user = bossProcessor.getUserFromController();
        UserPersonalInfo userPersonalInfo = user.getUserPersonalInfo();
        firstName.setText(userPersonalInfo.getFirstName());
        lastName.setText(userPersonalInfo.getLastName());
        email.setText(userPersonalInfo.getEmail());
        password.setText(userPersonalInfo.getPassword());
        phoneNumber.setText(userPersonalInfo.getPhoneNumber());
        setUserImage(user, profilePhoto);
        for (User user : bossProcessor.usersFromController()) {
            users.add(user.getUsername());
        }
        usersListView.setItems(users);
        for (Product product : bossProcessor.productsFromController()) {
            products.add(product.getName() + " / " + product.getProductId());
        }
        productsListView.setItems(products);
        for (CodedDiscount discount : bossProcessor.codedDiscountsFromController()) {
            codedDiscounts.add(discount.getDiscountCode());
        }
        codedDiscountListView.setItems(codedDiscounts);
        for (Category category : bossProcessor.categoriesFromController()) {
            categories.add(category.getName());
        }
        categoryListView.setItems(categories);
        for (Request request : bossProcessor.requestsFromController()) {
            requests.add(request.getRequestId());
        }
        requestsListView.setItems(requests);
    }

    public void update() {
        Music.getInstance().confirmation();
        client.updateUser(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        bossProcessor.editField(userPersonalInfo);
    }

    private void init() {
        Category.setAllCategories(client.getAllCategories());
        User.setAllUsers(client.getAllUsers());
        Product.setAllProductsInList(client.getAllProducts());
        Request.setAllRequests(client.getAllRequests());
        CodedDiscount.setAllCodedDiscount(client.getAllCodedDiscounts());
    }

    public void browsePhotoUser() throws IOException {
        browsePhotoUser(user, profilePhoto);
    }

    public void open() {
        Music.getInstance().open();
    }

}
