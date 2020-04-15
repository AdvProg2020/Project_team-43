package Controller;

import View.ShowAndCatch;
import com.sun.org.apache.bcel.internal.classfile.Code;
import model.*;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Date;

public class Processor {
    private BuyOrder buyOrder;
    private boolean isLogin;
    private User user;
    private ShowAndCatch viewManager = ShowAndCatch.getInstance();

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

    public void createManagerProfile(String userName, String firstName, String lastName, String email, String phoneNumber, String password) {
        //TODO : error handling

        UserPersonalInfo newPersonalInfo = new UserPersonalInfo(firstName, lastName, email, phoneNumber, password);
        User newManager = new Manager(userName, newPersonalInfo);

    }


    public void removeProduct(String productId) {
        Product product = Product.getProductById(productId);
        if (product != null) {
            Product.allProductsInList.remove(product);
        }

    }

    public void createDiscountCode(String discountCode, Date startTime, Date endTime, double discountAmount, int repeat) {
        //TODO : error handling
        CodedDiscount newDiscount = new CodedDiscount(discountCode, startTime, endTime, discountAmount, repeat);

    }

    public void viewDiscountCodes() {
        ArrayList<CodedDiscount> allCodedDiscount = CodedDiscount.allCodedDiscount;

    }

    public void viewDiscountCode(String discountCode) {
        CodedDiscount discount = CodedDiscount.getDiscountById(discountCode);

    }

    public void editDiscountCode(String discountCode) {
        CodedDiscount discount = CodedDiscount.getDiscountById(discountCode);

    }

    public void removeDiscountCode(String discountCode) {
        CodedDiscount codedDiscount = CodedDiscount.getDiscountById(discountCode);
        if (codedDiscount != null) {
            CodedDiscount.allCodedDiscount.remove(codedDiscount);
        }
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

    public void viewProductInCart(String userName) {
        User user = User.getUserByUserName(userName);

    }

    public void increaseProduct(String userName, String productId) {
        User user = User.getUserByUserName(userName);
        Product product = Product.getProductById(productId);

    }

    public void decreaseProduct(String userName, String productId) {
        User user = User.getUserByUserName(userName);
        Product product = Product.getProductById(productId);

    }

    public void showTotalPrice(String userName) {
        User user = User.getUserByUserName(userName);

    }

    public void viewBalance(String userName) {
        User user = User.getUserByUserName(userName);

    }

    public void viewOrders(String userName) {
        User user = User.getUserByUserName(userName);

    }

    public void showOrder(String userName, String orderId) {
        User user = User.getUserByUserName(userName);

    }

    public void rateProduct(String userName, String productId, int score) {
        User user = User.getUserByUserName(userName);


    }

    public void viewDiscountCodes(String userName) {
        User user = User.getUserByUserName(userName);

    }

    public void viewCompanyInfo(String userName) {
        User user = User.getUserByUserName(userName);

    }

    public void viewSalesHistory(String userName) {
        User user = User.getUserByUserName(userName);

    }

    public void viewProducts(String userName) {
        User user = User.getUserByUserName(userName);

    }

    public void addProduct(String userName) {//gereftan field haye product
        User user = User.getUserByUserName(userName);
        ArrayList<String> productInfo = new ArrayList<String>();
        viewManager.getProductInfo(productInfo);

    }

    public void removeProduct(String userName, String productId) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        Product product = Product.getProductById(productId);
        //Product.allProductsInList.remove(product);//agar bekhahim chand foroshande ro handle konim
        ((Seller)user).removeProduct(product);

    }

    public void viewOffs(String userName) {
        User user = User.getUserByUserName(userName);
        ArrayList<Off> offs = ((Seller) user).getOffs();
        viewManager.showOffs(offs);
    }

    public void viewOff(String userName, String offId) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        Off off = ((Seller) user).getOffById(offId);
        viewManager.showOff(off);
    }

    public void editOff(String userName, String offId) {//be soorate darkhast
        User user = User.getUserByUserName(userName);
        Off off = ((Seller) user).getOffById(offId);
        //chegoone edit mishavad
    }

    public void addOff(String userName, String offId, String startTime, String endTime, double discountAmount) {
        User user = User.getUserByUserName(userName);
        ArrayList<Product> products = new ArrayList<Product>();
        viewManager.getOffProducts(products);
        Off off = new Off(offId, startTime, endTime, discountAmount, (Seller)user, products);
        String requestId = "";//chegone taeen mishavad
        Request offRequest = new OffRequest(requestId, off);
        ((Seller) user).addOff(off);


    }


}