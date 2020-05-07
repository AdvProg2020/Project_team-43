package Controller;

import View.ShowAndCatch;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import model.*;
import model.filters.Criteria;
import model.filters.FilterManager;
import model.request.SellerRequest;


import java.util.ArrayList;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Processor {
    protected static boolean isLogin;
    protected static User user;
    protected static ShowAndCatch viewManager = ShowAndCatch.getInstance();
    protected FilterManager productFilter;

    public User getUser() {
        return user;
    }

    public Processor() {
    }

    public void newProductFilter() {
        productFilter = new FilterManager();
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
        Pattern selectCategoryPattern = Pattern.compile("select category (.+)");
        Matcher selectCategoryMatcher = selectCategoryPattern.matcher(command);
        if (showAvailableFiltersMatcher.matches()) {
            showAvailableFilter();
        } else if (filterMatcher.matches()) {
            filter(filterMatcher.group(1));
        } else if (currentFiltersMatcher.matches()) {
            currentFilter();
        } else if (disableFilterMatcher.matches()) {
            disableFilter(disableFilterMatcher.group(1));
        } else if (selectCategoryMatcher.matches()) {
            selectCategoryForFilter(selectCategoryMatcher.group(1));
        }
    }

    public void selectCategoryForFilter(String categoryName) {
        productFilter.setCategory(Category.getCategoryByName(categoryName));
    }

    public void showAvailableFilter() {
        viewManager.showAvailableFilters();
    }

    public void filter(String selectedFilter) {
        if (selectedFilter.matches("by price from (\\d+) to (\\d+)")) {
            productFilter.addPriceFilter(Double.parseDouble(selectedFilter.split(" ")[3]),
                    Double.parseDouble(selectedFilter.split(" ")[5]));
        } else if (selectedFilter.matches("by availability")) {
            productFilter.addAvailabilityPrice();
        } else if (selectedFilter.matches("by name (.+)")) {
            Pattern pattern = Pattern.compile("by name (.+)");
            Matcher matcher = pattern.matcher(selectedFilter);
            productFilter.addNameFilter(matcher.group(1));
        } else if (selectedFilter.matches("by category features")) {
            if (productFilter.getCategory() == null) {
                try {
                    throw new InvalidCommandException("please select a category");
                } catch (Exception e) {
                    viewManager.showErrorMessage(e.getMessage());
                }
            } else {
                productFilter.addFeaturesToCategoryFeaturesFilter(viewManager.addFilterByCategoryFeatures(productFilter));
            }

        } else {
            //TODO: error handling
        }
    }

    public void currentFilter() {
        viewManager.showCurrentFilters(productFilter);

    }

    public void disableFilter(String selectedFilter) {
        if (selectedFilter.equalsIgnoreCase("price")) {
            productFilter.disablePriceFilter();
        } else if (selectedFilter.equalsIgnoreCase("availability")) {
            productFilter.disableAvailabilityFilter();
        } else if (selectedFilter.equalsIgnoreCase("name")) {
            productFilter.disableNameFilter();
        } else if (selectedFilter.matches("(\\S+)")) {
            Pattern pattern = Pattern.compile("(\\S+)");
            Matcher matcher = pattern.matcher(selectedFilter);
            productFilter.disableFeature(matcher.group(1));
        }
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
        if (selectedSort.equalsIgnoreCase("by view"))
            Sorting.setSortByView();
        //TODO: other sorts

    }

    public void currentSort() {
        viewManager.showSort(Sorting.getComparator());
    }

    public void disableSort() {

    }

    public void showProducts() {
        //TODO : error handling
        ArrayList<Product> products = productFilter.getProductsAfterFilter(Product.getAllProductsInList());
        Collections.sort(products, Sorting.getComparator());
        viewManager.showProducts(products);
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
        if (addToCartMatcher.matches()) {
            //TODO : Seller seller = viewManager.selectSeller();
            addToCart(productId);
        }
    }

    public void showDigest(String productId) {
        //TODO : error handling
        Product product = Product.getProductById(productId);
        viewManager.showDigest(product);
    }

    public void addToCart(String productId) {
        BuyerProcessor.getInstance().addToBuyerCart(Product.getProductById(productId));
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
        if (secondProduct.getCategory() == firstProduct.getCategory())
            viewManager.compare(firstProduct, secondProduct);
    }

    public void manageComments(String command, String productId) throws InvalidCommandException {
        if (!isLogin) {
            throw new InvalidCommandException("user in not login");
        }
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern addCommentPattern = Pattern.compile("add comment");
        Matcher addCommentMatcher = addCommentPattern.matcher(command);
        if (addCommentMatcher.matches()) {
            addComment(Product.getProductById(productId));
        }
    }

    public void addComment(Product product) {
        //TODO : error handling
        ArrayList<String> commentInfo = new ArrayList<String>();
        viewManager.getCommentInfo(commentInfo);
        boolean isBuy = false;
        for (BuyOrder order : ((Buyer) user).getOrders()) {
            if (order.getProducts().containsKey(product)) {
                isBuy = true;
                break;
            }
        }
        new Comment(product, commentInfo.get(1), isBuy);
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
        viewManager.showOffs(offs, productFilter);
    }

    public void viewPersonalInfo() {
        viewManager.viewPersonalInfo(user.getUserPersonalInfo());
    }

    public void editField(String field) {
        //TODO : error handling

    }

    public void manageCart(String userName, String command) throws InvalidCommandException, NullPointerException {
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
        } else {
            throw new InvalidCommandException("invalid command");
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
        if (user.getUserType() == UserType.BUYER)
            BuyerProcessor.getInstance().setBuyerCart();
        return "logged in successful";
    }

    public void logout() {
        user = null;
        isLogin = false;
    }

}