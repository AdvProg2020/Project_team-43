package Controller;

import com.sun.org.apache.bcel.internal.classfile.Code;
import model.*;

import java.util.ArrayList;

public class Processor {
    private BuyOrder buyOrder;
    private boolean isLogin;
    private User user;

    public User getUser() {
        return user;
    }

    public Processor() {
    }

    public ArrayList<Category> viewCategories() {
        return Category.getAllCategories();
        //TODO : send to view //how?? (optional)
    }
    public boolean loginProcess(String username, String password) {
        if (User.hasUserWithUserName(username))
            return User.getUserByUserName(username).getUserPersonalInfo().getPassword().equals(password);
        return false;
    }

    public boolean isUserLoggedIn() {
        return isLogin;
    }

    public void filteringProcess(String command) {


    }

    public void sortingProcess(String command) {


    }

    public void showProducts() {
        ArrayList<Product> products = Product.allProductsInList;


    }

    public void showProductById(String Id) {
        Product product = Product.getProductById(Id);


    }

    public void showDigest(String command, String Id) {
        Product product = Product.getProductById(Id);

    }

    public void showAttributes(String Id) {
        Product product = Product.getProductById(Id);

    }

    public void compareProcess(String firstProductId, String secondProductId) {
        Product firstProduct = Product.getProductById(firstProductId);
        Product secondProduct = Product.getProductById(secondProductId);


    }

    public void showComments(String Id) {
        Product product = Product.getProductById(Id);

    }

    public void showOffs() {
        ArrayList<Off> offs = Off.acceptedOffs;


    }

    public void viewPersonalInfo(String userName) {
        User user = User.getUserByUserName(userName);


    }

    public void editField(String userName, String field) {
        User user = User.getUserByUserName(userName);


    }

    public void viewUser(String userName) {
        User user = User.getUserByUserName(userName);

    }

    public void deleteUser(String userName) {
        User user = User.getUserByUserName(userName);

    }

    public void createManagerProfile() {//voroodi : field hayi ke bayad gerefte shee


    }


    public void removeProduct(String productId) {
        Product product = Product.getProductById(productId);

    }

    public void createDiscountCode() {//vorodi : field hayi ke bayad gerefte she

    }

    public void viewDiscountCodes() {


    }

    public void viewDiscountCode(String discountCode) {
        CodedDiscount discount =CodedDiscount.getDiscountById(discountCode);

    }

    public void editDiscountCode(String discountCode) {
        CodedDiscount discount = CodedDiscount.getDiscountById(discountCode);

    }

    public void removeDiscountCode(String discountCode) {
        CodedDiscount discount = CodedDiscount.getDiscountById(discountCode);

    }

    public void viewRequests() {


    }

    public void viewRequestDetails(String requestId) {
        Request request = Manager.getRequestById(requestId);

    }

    public void acceptRequest(String requestId) {
        Request request = Manager.getRequestById(requestId);

    }

    public void declineRequest(String requestId) {
        Request request = Manager.getRequestById(requestId);

    }


    public void editCategory(String categoryName) {
        Category category = Category.getCategoryByName(categoryName);

    }

    public void addCategory() {//vorodi : field haye marboote

    }

    public void removeCategory(String categoryName) {
        Category category = Category.getCategoryByName(categoryName);


    }


}
