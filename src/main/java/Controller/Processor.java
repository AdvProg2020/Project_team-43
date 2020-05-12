package Controller;

import javafx.util.Pair;
import View.ShowAndCatch;
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
        viewManager.showAvailableSorts();
    }

    public void sort(String selectedSort) {
        if (selectedSort.equalsIgnoreCase("by view")) {
            Sorting.setSortByView();
        } else if (selectedSort.equalsIgnoreCase("by score")) {
            Sorting.setSortByScore();
        } else if (selectedSort.equalsIgnoreCase("by date")) {
            Sorting.setSortByDate();
        } else {
            viewManager.showErrorMessage("invalid command");
        }
    }

    public void currentSort() {
        viewManager.showSort(Sorting.getComparator());
    }

    public void disableSort() {
        Sorting.setSortByView();
    }

    public void showProducts() {
        //TODO : error handling
        ArrayList<Product> products = productFilter.getProductsAfterFilter(Product.getAllProductsInList());
        Collections.sort(products, Sorting.getComparator());
        viewManager.showProducts(products);
    }

    public void manageDigest(String command, String productId) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern addToCartPattern = Pattern.compile("add to cart");
        Matcher addToCartMatcher = addToCartPattern.matcher(command);
        if (addToCartMatcher.matches()) {
            Seller seller = viewManager.selectSeller();
            addToCart(productId, seller);
        }
    }

    public void showDigest(String productId) throws NullPointerException {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new NullPointerException("product with this Id doesn't exist");
        }
        viewManager.showDigest(product);
    }

    public void addToCart(String productId) {
        BuyerProcessor.getInstance().addToBuyerCart(Product.getProductById(productId));
    }

    public void addToCart(String productId, Seller seller) {
        BuyerProcessor.getInstance().addToBuyerCart(new Pair<Product, Seller>(Product.getProductById(productId), seller));
    }


    public void showAttributes(String Id) throws NullPointerException {
        Product product = Product.getProductById(Id);
        if (product == null) {
            throw new NullPointerException("product with this Id doesn't exist");
        }
        viewManager.showProductInfo(product);

    }

    public void compareProcess(String firstProductId, String secondProductId) throws NullPointerException {
        Product firstProduct = Product.getProductById(firstProductId);
        Product secondProduct = Product.getProductById(secondProductId);
        if (secondProduct == null || firstProduct == null) {
            throw new NullPointerException("product with this Id doesn't exist");
        }
        if (secondProduct.getCategory() == firstProduct.getCategory())
            viewManager.compare(firstProduct, secondProduct);
    }

    public void manageComments(String command, String productId) throws InvalidCommandException {
        if (!isLogin) {
            throw new InvalidCommandException("user in not login");
        }
        if (command.equals("back")) {
            return;
        }
        Pattern addCommentPattern = Pattern.compile("add comment");
        Matcher addCommentMatcher = addCommentPattern.matcher(command);
        if (addCommentMatcher.matches()) {
            addComment(Product.getProductById(productId));
        } else {
            throw new InvalidCommandException("invalid command");
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
        Product product = Product.getProductById(productId);
        if(product == null){
            throw new NullPointerException("product with this Id doesn't exist");
        }
        ArrayList<Comment> comments = product.getComments();
        viewManager.showComments(comments);
    }

    public void showOffs() {
        ArrayList<Off> offs = Off.acceptedOffs;
        viewManager.showOffs(offs, productFilter);
    }

    public void viewPersonalInfo() {
        viewManager.viewPersonalInfo(user.getUserPersonalInfo());
    }

    public String registerUser(String command) {
        Pattern pattern = Pattern.compile("create account (\\S+) (\\S+)");
        Matcher matcher = pattern.matcher(command);
        if (!matcher.find())
            return ("invalid command");
        if (User.hasUserWithUserName(matcher.group(2)))
            return ("there is a user with this username");

        if (matcher.group(1).equalsIgnoreCase("seller")) {
            UserPersonalInfo personalInfo = new UserPersonalInfo();
            viewManager.getPersonalInfo(personalInfo);
            String companyName = viewManager.getCompanyNameMenuFromUser();
            SellerRequest.addSellerRequest(personalInfo, matcher.group(2), companyName);

            //test
            //User.allUsers.add(new Seller(matcher.group(2), personalInfo, companyName));

            return "done";
        } else if (matcher.group(1).equalsIgnoreCase("buyer")) {
            UserPersonalInfo personalInfo = new UserPersonalInfo();
            viewManager.getPersonalInfo(personalInfo);
            Buyer.addBuyer(personalInfo, matcher.group(2));
            return "done";
        } else if (matcher.group(1).equalsIgnoreCase("manager")) {
            if (!User.hasManager()) {
                UserPersonalInfo personalInfo = new UserPersonalInfo();
                viewManager.getPersonalInfo(personalInfo);
                new Manager(matcher.group(2), personalInfo);
                return "done";
            }
            return "there is a manger";
        }
        return ("invalid type");

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