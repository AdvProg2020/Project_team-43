package Controller;

import javafx.util.Pair;
import View.ShowAndCatch;
import model.*;
import model.filters.FilterManager;
import model.request.SellerRequest;


import java.util.ArrayList;

import java.util.Collections;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Processor {
    protected static boolean isLogin;
    public static User user;
    protected static ShowAndCatch viewManager = ShowAndCatch.getInstance();
    protected FilterManager productFilter;

    public User getUser() {
        return user;
    }

    public Processor() {
    }

    public FilterManager getProductFilter() {
        return productFilter;
    }

    public void newProductFilter() {
        productFilter = new FilterManager();
    }

    public void errorMessage(String message) {
        try {
            throw new InvalidCommandException(message);
        } catch (Exception e) {
            viewManager.showErrorMessage(e.getMessage());
        }
    }

    public static void setIsLogin(boolean isLogin) {
        Processor.isLogin = isLogin;
    }

    public boolean isUserLoggedIn() {
        return isLogin;
    }

    public void viewCategories() {
        ArrayList<Category> categories = Category.getAllCategories();
        viewManager.showCategories(categories);
    }

    public void filteringProcess(String command) {
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
        } else {
            errorMessage("invalid command");
        }
    }

    public void selectCategoryForFilter(String categoryName) {
        if (Category.hasCategoryWithName(categoryName))
            productFilter.setCategory(Category.getCategoryByName(categoryName));
        else
            errorMessage("there is not category with this name");
    }

    public void showAvailableFilter() {
        viewManager.showAvailableFilters();
    }

    public void filter(String selectedFilter) {
        Pattern pricePattern = Pattern.compile("by price from (\\d+) to (\\d+)");
        Matcher priceMatcher = pricePattern.matcher(selectedFilter);
        Pattern namePattern = Pattern.compile("by name (.+)");
        Matcher nameMatcher = namePattern.matcher(selectedFilter);
        Pattern availabilityPattern = Pattern.compile("by availability");
        Matcher availabilityMatcher = availabilityPattern.matcher(selectedFilter);
        Pattern categoryPattern = Pattern.compile("by category features");
        Matcher categoryMatcher = categoryPattern.matcher(selectedFilter);
        if (priceMatcher.matches()) {
            productFilter.addPriceFilter(Double.parseDouble(priceMatcher.group(1)),
                    Double.parseDouble(priceMatcher.group(2)));
        } else if (availabilityMatcher.matches()) {
            productFilter.addAvailabilityFilter();
        } else if (nameMatcher.matches()) {
            productFilter.addNameFilter(nameMatcher.group(1));
        } else if (categoryMatcher.matches()) {
            addFeaturesFilter();

        } else {
            errorMessage("invalid filter");
        }
    }

    public void addFeaturesFilter() {
        if (productFilter.getCategory() == null) {
            errorMessage("please select a category");
        } else {
            productFilter.addFeaturesToCategoryFeaturesFilter(viewManager.addFilterByCategoryFeatures(productFilter));
        }
    }

    public void currentFilter() {
        viewManager.showCurrentFilters(productFilter);
    }

    public void disableFilter(String selectedFilter) {
        Pattern featurePattern = Pattern.compile("feature (\\S+)");
        Matcher featureMatcher = featurePattern.matcher(selectedFilter);
        if (selectedFilter.equalsIgnoreCase("price")) {
            productFilter.disablePriceFilter();
        } else if (selectedFilter.equalsIgnoreCase("availability")) {
            productFilter.disableAvailabilityFilter();
        } else if (selectedFilter.equalsIgnoreCase("name")) {
            productFilter.disableNameFilter();
        } else if (featureMatcher.matches()) {
            if (productFilter.getCategory() != null)
                productFilter.disableFeature(featureMatcher.group(1));
            else
                errorMessage("you did not select a category");
        } else
            errorMessage("there is no filter with this name");
    }

    public void sortingProcess(String command) throws InvalidCommandException {
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
        } else {
            throw new InvalidCommandException("sort with this name doesn't exist");
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
        } else if (selectedSort.equalsIgnoreCase("by price")) {
            Sorting.setSortByPrice();
        } else if (selectedSort.equalsIgnoreCase("by price -d")) {
            Sorting.setSortByPriceDescending();
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
        if (command.equals("back")) {
            return;
        }
        Pattern addToCartPattern = Pattern.compile("add to cart");
        Matcher addToCartMatcher = addToCartPattern.matcher(command);
        if (addToCartMatcher.matches()) {
            Seller seller = viewManager.selectSeller();
            addToCart(productId, seller);
        } else
            errorMessage("invalid command");
    }

    public void showDigest(String productId) throws NullPointerException {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new NullPointerException("product with this Id doesn't exist");
        }
        viewManager.showDigest(product);
    }


    public void addToCart(String productId, Seller seller) {
        Product product;
        if (Product.hasProductWithId(productId)) {
            product = Product.getProductById(productId);
        } else {
            errorMessage("invalid product Id");
            return;
        }
        if (seller.isProductAvailable(product))
            BuyerProcessor.getInstance().addToBuyerCart(new Pair<>(product, seller));
        else
            errorMessage("seller has not any of this product");
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
        ArrayList<String> commentInfo = new ArrayList<>();
        viewManager.getCommentInfo(commentInfo);
        addComment(product,commentInfo);

    }
    public void addComment(Product product,ArrayList<String> commentInfo){
        boolean isBuy = false;
        for (BuyOrder order : ((Buyer) user).getOrders()) {
            if (order.getProducts().containsKey(product)) {
                isBuy = true;
                break;
            }
        }
        Comment comment = new Comment(product, commentInfo.get(1), isBuy, (Buyer) user);
        product.addComment(comment);

    }

    public void showComments(String productId) {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new NullPointerException("product with this Id doesn't exist");
        }
        ArrayList<Comment> comments = new ArrayList<>();
        for (Comment comment : product.getComments()) {
            if (comment.getOpinionState() == State.OpinionState.CONFIRMED) {
                comments.add(comment);
            }
        }
        viewManager.showComments(comments);
    }

    public void showInQueueComments() {
        viewManager.showComments(Comment.getInQueueExpectation());
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
        if (!Objects.requireNonNull(User.getUserByUserName(username)).getUserPersonalInfo().getPassword().equals(password))
            return "incorrect password";
        isLogin = true;
        user = User.getUserByUserName(username);
        if (Objects.requireNonNull(user).getUserType() == UserType.BUYER)
            BuyerProcessor.getInstance().setNewBuyerCart();
        return "logged in successful";
    }

    public void logout() {
        BuyerProcessor.getInstance().logout();
    }

}