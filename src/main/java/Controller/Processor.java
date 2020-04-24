package Controller;

import View.ShowAndCatch;
import model.*;


import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Processor {
    protected static boolean isLogin;
    protected static User user;
    protected static ShowAndCatch viewManager = ShowAndCatch.getInstance();
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
        if (command.equals("back")) {
            return;
        }
        Pattern showAvailableFiltersPattern = Pattern.compile("show available filters");
        Matcher showAvailableFiltersMatcher = showAvailableFiltersPattern.matcher(command);
        Pattern filterPattern = Pattern.compile("filter (.+)");
        Matcher filterMatcher = filterPattern.matcher(command);
        Pattern currentFiltersPattern = Pattern.compile("current filters");
        Matcher currentFiltersMatcher = currentFiltersPattern.matcher(command);
        Pattern disableFilterPattern = Pattern.compile("disable filter (.+)");
        Matcher disableFilterMatcher = disableFilterPattern.matcher(command);

        if (showAvailableFiltersMatcher.matches()) {
            showAvailableFilter();
        } else if (filterMatcher.matches()) {
            filter(filterMatcher.group(1));
        } else if (currentFiltersMatcher.matches()) {
            currentFilter();
        } else if (disableFilterMatcher.matches()) {
            disableFilter(disableFilterMatcher.group(1));
        }


    }

    public void showAvailableFilter() {

    }

    public void filter(String selectedFilter) {

    }

    public void currentFilter() {

    }

    public void disableFilter(String selectedFilter) {

    }

    public void sortingProcess(String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern showAvailableSortsPattern = Pattern.compile("show available sorts");
        Matcher showAvailableSortsMatcher = showAvailableSortsPattern.matcher(command);
        Pattern sortPattern = Pattern.compile("sort (.+)");
        Matcher sortMatcher = sortPattern.matcher(command);
        Pattern currentSortPattern = Pattern.compile("current sort");
        Matcher currentSortMatcher = currentSortPattern.matcher(command);
        Pattern disableSortPattern = Pattern.compile("disable sort");
        Matcher disableSortMatcher = disableSortPattern.matcher(command);

        if (showAvailableSortsMatcher.matches()) {
            showAvailableSort();
        } else if (sortMatcher.matches()) {
            sort(sortMatcher.group(1));
        } else if (currentSortMatcher.matches()) {
            currentSort();
        } else if (disableSortMatcher.matches()) {
            disableSort();
        }

    }

    public void showAvailableSort() {

    }

    public void sort(String selectedSort) {

    }

    public void currentSort() {

    }

    public void disableSort() {

    }

    public void showProducts() {
        //TODO : error handling
        ArrayList<Product> products = Product.allProductsInList;


    }

    public void showProductById(String Id) {
        //TODO : error handling
        Product product = Product.getProductById(Id);


    }

    public void manageDigest(String command, String productId) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern addToCartPattern = Pattern.compile("add to cart");
        Matcher addToCartMatcher = addToCartPattern.matcher(command);
        Pattern selectSellerPattern = Pattern.compile("select seller (.+)");
        Matcher selectSellerMatcher = selectSellerPattern.matcher(command);

        if (addToCartMatcher.matches()) {
            addToCart(productId);
        } else if (selectSellerMatcher.matches()) {
            selectSeller(selectSellerMatcher.group(1));
        }

    }

    public void showDigest(String productId) {
        //TODO : error handling
        Product product = Product.getProductById(productId);
    }

    public void addToCart(String productId) {

    }

    public void selectSeller(String sellerUserName) {

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

    public void manageComments(String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern addCommentPattern = Pattern.compile("add comment");
        Matcher addCommentMatcher = addCommentPattern.matcher(command);
        if (addCommentMatcher.matches()) {
            addComment();
        }

    }

    public void addComment() {
        //TODO : error handling
        ArrayList<String> commentInfo = new ArrayList<String>();
        viewManager.getCommentInfo(commentInfo);
        //handle commente gerefte shode

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


    public void manageUsers(String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern viewUserPattern = Pattern.compile("view (.+)");
        Matcher viewUserMatcher = viewUserPattern.matcher(command);
        Pattern deleteUserPattern = Pattern.compile("delete user (.+)");
        Matcher deleteUserMatcher = deleteUserPattern.matcher(command);
        Pattern createManagerPattern = Pattern.compile("create manager profile");
        Matcher createManagerMatcher = createManagerPattern.matcher(command);
        if (viewUserMatcher.matches()) {
            viewUser(viewUserMatcher.group(1));
        } else if (deleteUserMatcher.matches()) {
            deleteUser(deleteUserMatcher.group(1));
        } else if (createManagerMatcher.matches()) {
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

    public void manageAllProducts(String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern removeProductPattern = Pattern.compile("remove (.+)");
        Matcher removeProductMatcher = removeProductPattern.matcher(command);
        if (removeProductMatcher.matches()) {
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

    public void manageDiscountCodes(String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern viewDiscountCodePattern = Pattern.compile("view discount code (.+)");
        Matcher viewDiscountCodeMatcher = viewDiscountCodePattern.matcher(command);
        Pattern editDiscountCodePattern = Pattern.compile("edit discount code (.+)");
        Matcher editDiscountCodeMatcher = editDiscountCodePattern.matcher(command);
        Pattern removeDiscountCodePattern = Pattern.compile("remove discount code (.+)");
        Matcher removeDiscountCodeMatcher = removeDiscountCodePattern.matcher(command);
        if (viewDiscountCodeMatcher.matches()) {
            viewDiscountCode(viewDiscountCodeMatcher.group(1));
        } else if (editDiscountCodeMatcher.matches()) {
            editDiscountCode(editDiscountCodeMatcher.group(1));
        } else if (removeDiscountCodeMatcher.matches()) {
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

    public void manageRequests(String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern detailsPattern = Pattern.compile("details (.+)");
        Matcher detailsMatcher = detailsPattern.matcher(command);
        Pattern acceptRequestPattern = Pattern.compile("accept (.+)");
        Matcher acceptRequestMatcher = acceptRequestPattern.matcher(command);
        Pattern declineRequestPattern = Pattern.compile("decline (.+)");
        Matcher declineRequestMatcher = declineRequestPattern.matcher(command);
        if (detailsMatcher.matches()) {
            viewRequestDetails(detailsMatcher.group(1));
        } else if (acceptRequestMatcher.matches()) {
            acceptRequest(acceptRequestMatcher.group(1));
        } else if (declineRequestMatcher.matches()) {
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

    public void manageCategories(String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern editCategoryPattern = Pattern.compile("edit (.+)");
        Matcher editCategoryMatcher = editCategoryPattern.matcher(command);
        Pattern addCategoryPattern = Pattern.compile("add (.+)");
        Matcher addCategoryMatcher = addCategoryPattern.matcher(command);
        Pattern removeCategoryPattern = Pattern.compile("remove (.+)");
        Matcher removeCategoryMatcher = removeCategoryPattern.matcher(command);
        if (editCategoryMatcher.matches()) {
            editCategory(editCategoryMatcher.group(1));
        } else if (addCategoryMatcher.matches()) {
            addCategory(addCategoryMatcher.group(1));
        } else if (removeCategoryMatcher.matches()) {
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

    public void manageCart(String userName, String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern showProductsPattern = Pattern.compile("show products");
        Matcher showProductsMatcher = showProductsPattern.matcher(command);
        Pattern viewProductPattern = Pattern.compile("view (.+)");
        Matcher viewProductMatcher = viewProductPattern.matcher(command);
        Pattern increaseProductPattern = Pattern.compile("increase (.+)");
        Matcher increaseProductMatcher = increaseProductPattern.matcher(command);
        Pattern decreaseProductPattern = Pattern.compile("decrease (.+)");
        Matcher decreaseProductMatcher = decreaseProductPattern.matcher(command);
        Pattern showTotalPricePattern = Pattern.compile("show total price");
        Matcher showTotalPriceMatcher = showTotalPricePattern.matcher(command);
        if (showProductsMatcher.matches()) {
            viewProductInCart(userName);
        } else if (viewProductMatcher.matches()) {
            // handle kardan raftan be safe mahsoolat
        } else if (increaseProductMatcher.matches()) {
            increaseProduct(userName, increaseProductMatcher.group(1));
        } else if (decreaseProductMatcher.matches()) {
            decreaseProduct(userName, decreaseProductMatcher.group(1));
        } else if (showTotalPriceMatcher.matches()) {
            showTotalPrice(userName);
        }

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
        viewManager.showBalance(user);
    }

    public void manageOrders(String userName, String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern showOrderPattern = Pattern.compile("show order (.+)");
        Matcher showOrderMatcher = showOrderPattern.matcher(command);
        Pattern rateProductPattern = Pattern.compile("rate (.+) (\\d)");
        Matcher rateProductMatcher = rateProductPattern.matcher(command);
        if (showOrderMatcher.matches()) {
            showOrder(userName, showOrderMatcher.group(1));
        } else if (rateProductMatcher.matches()) {
            rateProduct(userName, rateProductMatcher.group(1), Integer.parseInt(rateProductMatcher.group(2)));
        }


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

    public void manageOffs(String userName, String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern viewOffPattern = Pattern.compile("view (.+)");
        Matcher viewOffMatcher = viewOffPattern.matcher(command);
        Pattern editOffPattern = Pattern.compile("edit (.+)");
        Matcher editOffMatcher = editOffPattern.matcher(command);
        Pattern addOffPattern = Pattern.compile("add off");
        Matcher addOffMatcher = addOffPattern.matcher(command);
        if (editOffMatcher.matches()) {
            editOff(userName, editOffMatcher.group(1));
        } else if (addOffMatcher.matches()) {
            addOff(userName);
        } else if (viewOffMatcher.matches()) {
            viewOff(userName, viewOffMatcher.group(1));
        }
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

    public void addOff(String userName) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        ArrayList<String> productsInString = new ArrayList<String>();
        ArrayList<String> offInfo = new ArrayList<String>();
        viewManager.getOffInfo(offInfo);
        viewManager.getOffProducts(productsInString);
        ArrayList<Product> products = castStringToProduct(productsInString);
        String offId = offInfo.get(0);
        String startTime = offInfo.get(1);
        String endTime = offInfo.get(2);
        double discountAmount = Double.parseDouble(offInfo.get(3));
        Off off = new Off(offId, startTime, endTime, discountAmount, (Seller) user, products);
        String requestId = "";//chegone taeen mishavad
        Request offRequest = new OffRequest(requestId, off);
        ((Seller) user).addOff(off);


    }

    public ArrayList<Product> castStringToProduct(ArrayList<String> productsInString) {
        //TODO : erro handling
        ArrayList<Product> products = new ArrayList<Product>();
        for (String productId : productsInString) {
            Product product = Product.getProductById(productId);
            products.add(product);
        }
        return products;
    }

    public String registerUser(String command) {
        Pattern pattern = Pattern.compile("create account (\\S+) (\\S+)");
        Matcher matcher = pattern.matcher(command);
        if (!matcher.find())
            return ("invalid command");
        if (User.hasUserWithUserName(matcher.group(2)))
            return ("there is a user with this username");
        UserPersonalInfo personalInfo = new UserPersonalInfo();
        viewManager.getPersonalInfo(personalInfo);
        if (matcher.group(1).equalsIgnoreCase("seller")) {
            String companyName = viewManager.getCompanyNameMenuFromUser();
            SellerRequest.addSellerRequest(personalInfo, matcher.group(2), companyName);
            return "done";
        } else if (matcher.group(1).equalsIgnoreCase("buyer")) {
            Buyer.addBuyer(personalInfo, matcher.group(2));
            return "done";
        } else {
            return ("invalid type");
        }

    }

    public String login(String username, String password) {
        if (!User.hasUserWithUserName(username))
            return "there is no user with this username";
        if (!User.getUserByUserName(username).getUserPersonalInfo().getPassword().equals(password))
            return "incorrect password";
        isLogin = true;
        user = User.getUserByUserName(username);
        BuyerProcessor.setBuyerCart();
        return "logged in successful";
    }

}