package Controller;

import View.ShowAndCatch;
import com.sun.org.apache.bcel.internal.classfile.Code;
import model.*;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static model.Category.getAllCategories;

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

    public boolean isUserLoggedIn() {
        return isLogin;
    }

    public void viewCategories() {
        //TODO : error handling
        ArrayList<Category> categories = Category.getAllCategories();
        viewManager.showCategories(categories);
    }

    public boolean loginProcess(String username, String password) {
        if (User.hasUserWithUserName(username))
            return User.getUserByUserName(username).getUserPersonalInfo().getPassword().equals(password);
        return false;
    }

    public void filteringProcess(String command) {
        //TODO : error handling


    }

    public void sortingProcess(String command) {
        //TODO : error handling


    }

    public void showProducts() {
        //TODO : error handling
        ArrayList<Product> products = Product.allProductsInList;


    }

    public void showProductById(String Id) {
        //TODO : error handling
        Product product = Product.getProductById(Id);


    }

    public void showDigest(String command, String Id) {
        //TODO : error handling
        Product product = Product.getProductById(Id);

    }

    public void showAttributes(String Id) {
        //TODO : error handling
        Product product = Product.getProductById(Id);
        viewManager.showProductInfo(product);

    }

    public void compareProcess(String firstProductId, String secondProductId) {
        //TODO : error handling
        Product firstProduct = Product.getProductById(firstProductId);
        Product secondProduct = Product.getProductById(secondProductId);


    }

    public void showComments(String productId) {
        //TODO : error handling
        Product product = Product.getProductById(productId);
        viewManager.showComments(product.getComments());

    }

    public void showOffs() {
        //TODO : error handling
        ArrayList<Off> offs = Off.acceptedOffs;
        viewManager.showOffs(offs);
    }

    public void viewPersonalInfo(String userName) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        viewManager.viewUser(user);

    }

    public void editField(String userName, String field) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);


    }

    public void manageUsers(String command) {
        //TODO : error handling
        if (command.equals("back")){
            return;
        }
        Pattern viewUserPattern = Pattern.compile("view (.+)");
        Matcher viewUserMatcher = viewUserPattern.matcher(command);
        Pattern deleteUserPattern = Pattern.compile("delete user (.+)");
        Matcher deleteUserMatcher = deleteUserPattern.matcher(command);
        Pattern createManagerPattern = Pattern.compile("create manager profile");
        Matcher createManagerMatcher = createManagerPattern.matcher(command);
        if(viewUserMatcher.matches()){
            viewUser(viewUserMatcher.group(1));
        } else if(deleteUserMatcher.matches()){
            deleteUser(deleteUserMatcher.group(1));
        } else if(createManagerMatcher.matches()){
            createManagerProfile();
        }

    }

    public void viewUser(String userName) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        viewManager.viewUser(user);

    }

    public void deleteUser(String userName) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        User.allUsers.remove(user);

    }

    public void createManagerProfile() {
        //TODO : error handling
        ArrayList<String> managerInfo = new ArrayList<String>();
        viewManager.getManagerInfo(managerInfo);
        String userName = managerInfo.get(0);
        String firstName = managerInfo.get(1);
        String lastName = managerInfo.get(2);
        String email = managerInfo.get(3);
        String phoneNumber = managerInfo.get(4);
        String password = managerInfo.get(5);
        UserPersonalInfo newPersonalInfo = new UserPersonalInfo(firstName, lastName, email, phoneNumber, password);
        User newManager = new Manager(userName, newPersonalInfo);

    }

    public void manageAllProducts(String command){
        //TODO : error handling
        if(command.equals("back")){
            return;
        }
        Pattern removeProductPattern = Pattern.compile("remove (.+)");
        Matcher removeProductMatcher = removeProductPattern.matcher(command);
        if(removeProductMatcher.matches()){
            removeProduct(removeProductMatcher.group(1));
        }
    }

    public void removeProduct(String productId) {
        Product product = Product.getProductById(productId);
        Product.allProductsInList.remove(product);

    }

    public void createDiscountCode() {
        //TODO : error handling
        ArrayList<String> discountCodedInfo = new ArrayList<String>();
        viewManager.getDiscountCodedInfo(discountCodedInfo);
        String discountCode = discountCodedInfo.get(0);
        String startTime = discountCodedInfo.get(1);
        String endTime = discountCodedInfo.get(2);
        double discountAmount = Double.parseDouble(discountCodedInfo.get(3));
        int repeat = Integer.parseInt(discountCodedInfo.get(4));
        CodedDiscount newDiscount = new CodedDiscount(discountCode, startTime, endTime, discountAmount, repeat);

    }
    public void manageDiscountCodes(String command){
        //TODO : error handling
        if(command.equals("back")){
            return;
        }
        Pattern viewDiscountCodePattern = Pattern.compile("view discount code (.+)");
        Matcher viewDiscountCodeMatcher = viewDiscountCodePattern.matcher(command);
        Pattern editDiscountCodePattern = Pattern.compile("edit discount code (.+)");
        Matcher editDiscountCodeMatcher = editDiscountCodePattern.matcher(command);
        Pattern removeDiscountCodePattern = Pattern.compile("remove discount code (.+)");
        Matcher removeDiscountCodeMatcher = removeDiscountCodePattern.matcher(command);
        if(viewDiscountCodeMatcher.matches()){
            viewDiscountCode(viewDiscountCodeMatcher.group(1));
        } else if(editDiscountCodeMatcher.matches()){
            editDiscountCode(editDiscountCodeMatcher.group(1));
        } else if(removeDiscountCodeMatcher.matches()){
            removeDiscountCode(removeDiscountCodeMatcher.group(1));
        }
    }
    public void viewBossDiscountCodes() {
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

    public void manageRequests(String command){
        //TODO : error handling
        if(command.equals("back")){
            return;
        }
        Pattern detailsPattern = Pattern.compile("details (.+)");
        Matcher detailsMatcher = detailsPattern.matcher(command);
        Pattern acceptRequestPattern = Pattern.compile("accept (.+)");
        Matcher acceptRequestMatcher = acceptRequestPattern.matcher(command);
        Pattern declineRequestPattern = Pattern.compile("decline (.+)");
        Matcher declineRequestMatcher = declineRequestPattern.matcher(command);
        if(detailsMatcher.matches()){
            viewRequestDetails(detailsMatcher.group(1));
        } else if(acceptRequestMatcher.matches()){
            acceptRequest(acceptRequestMatcher.group(1));
        } else if(declineRequestMatcher.matches()){
            declineRequest(declineRequestMatcher.group(1));
        }
    }

    public void viewRequests() {
        ArrayList<Request> requests = Manager.allRequest;

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

    public void manageCategories(String command){
        //TODO : error handling
        if(command.equals("back")){
            return;
        }
        Pattern editCategoryPattern = Pattern.compile("edit (.+)");
        Matcher editCategoryMatcher = editCategoryPattern.matcher(command);
        Pattern addCategoryPattern = Pattern.compile("add (.+)");
        Matcher addCategoryMatcher = addCategoryPattern.matcher(command);
        Pattern removeCategoryPattern = Pattern.compile("remove (.+)");
        Matcher removeCategoryMatcher = removeCategoryPattern.matcher(command);
        if(editCategoryMatcher.matches()){
            editCategory(editCategoryMatcher.group(1));
        } else if(addCategoryMatcher.matches()){
            addCategory(addCategoryMatcher.group(1));
        } else if(removeCategoryMatcher.matches()){
            removeCategory(removeCategoryMatcher.group(1));
        }
    }

    public void editCategory(String categoryName) {
        //TODO : error handling
        Category category = Category.getCategoryByName(categoryName);

    }

    public void addCategory(String categoryName) {
        //TODO : error handling
        //in sub category o super category ro chejori begirim age khodesh super bashe ya khodesh sub bashe
        viewManager.getCategoryInfo();
    }

    public void removeCategory(String categoryName) {
        //TODO : error handling
        Category category = Category.getCategoryByName(categoryName);



    }

    public void viewProductInCart(String userName) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);

    }

    public void increaseProduct(String userName, String productId) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        Product product = Product.getProductById(productId);

    }

    public void decreaseProduct(String userName, String productId) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        Product product = Product.getProductById(productId);

    }

    public void showTotalPrice(String userName) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);

    }

    public void viewBalance(String userName) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);

    }

    public void viewOrders(String userName) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);

    }

    public void showOrder(String userName, String orderId) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);

    }

    public void rateProduct(String userName, String productId, int score) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);


    }

    public void viewBuyerDiscountCodes(String userName) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);


    }

    public void viewCompanyInfo(String userName) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        Company company = ((Seller) user).getCompany();
        viewManager.showCompanyInfo(company);

    }

    public void viewSalesHistory(String userName) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        ArrayList<SellOrder> orders = ((Seller) user).getOrders();
        viewManager.showSellOrders(orders);
    }

    public void viewSellerProducts(String userName) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        ArrayList<Product> products = ((Seller) user).getProducts();
        viewManager.showProducts(products);
    }

    public void addProduct(String userName) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        ArrayList<String> productInfo = new ArrayList<String>();
        viewManager.getProductInfo(productInfo);
        String Id = productInfo.get(0);
        String name = productInfo.get(1);
        Company company = Company.getCompanyByName(productInfo.get(2));
        Category category = Category.getCategoryByName(productInfo.get(3));
        double price = Double.parseDouble(productInfo.get(4));
        ArrayList<String> features = new ArrayList<String>();
        viewManager.getProductFeatures(category.getFeatures(), features);
        ((Seller) user).addProduct(new Product(Id, name, company, price, category, (Seller) user, features));
    }

    public void removeProduct(String userName, String productId) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        Product product = Product.getProductById(productId);
        //Product.allProductsInList.remove(product);//agar bekhahim chand foroshande ro handle konim
        ((Seller) user).removeProduct(product);

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
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        Off off = ((Seller) user).getOffById(offId);
        //chegoone edit mishavad
    }

    public void addOff(String userName, String offId, String startTime, String endTime, double discountAmount) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        ArrayList<Product> products = new ArrayList<Product>();
        viewManager.getOffProducts(products);
        Off off = new Off(offId, startTime, endTime, discountAmount, (Seller) user, products);
        String requestId = "";//chegone taeen mishavad
        Request offRequest = new OffRequest(requestId, off);
        ((Seller) user).addOff(off);


    }


}