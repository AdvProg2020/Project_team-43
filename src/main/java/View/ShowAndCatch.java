package View;

import model.*;
import model.filters.Criteria;
import model.filters.FilterManager;

import java.util.*;

public class ShowAndCatch {
    private static ShowAndCatch ourInstance = new ShowAndCatch();
    private static Scanner scanner = Menu.getScanner();

    public static ShowAndCatch getInstance() {
        return ourInstance;
    }

    private ShowAndCatch() {
    }


    public void showOffs(ArrayList<Off> offs, FilterManager filterManager) {
        for (int i = 1; i <= offs.size(); i++) {
            System.out.println(i + " : " + "\n" + "Off Id : " + offs.get(i - 1).getOffId());
            System.out.println("Seller user name : " + offs.get(i - 1).getSellerName());
            System.out.println("Discount amount : " + offs.get(i - 1).getDiscountAmount());
            System.out.println("Date : " + offs.get(i - 1).getStartTime() + " - " + offs.get(i - 1).getEndTime());
            System.out.println("Products : ");
            for (Product product : filterManager.getProductsAfterFilter(offs.get(i - 1).getProducts())) {
                System.out.println(product.getName());
            }
        }
    }


    public void showProducts(ArrayList<Product> products) {
        for (Product product : products) {
            System.out.println(product.getName());
        }
    }


    public void getPersonalInfo(UserPersonalInfo personalInfo) {
        System.out.print("first name : ");
        String firstName = Menu.getScanner().nextLine();
        personalInfo.setFirstName(firstName);
        System.out.print("last name : ");
        String lastName = Menu.getScanner().nextLine();
        personalInfo.setLastName(lastName);
        System.out.print("email : ");
        String email = Menu.getScanner().nextLine();
        personalInfo.setEmail(email);
        System.out.print("phone number : +98");
        String phoneNumber = Menu.getScanner().nextLine();
        while (!phoneNumber.matches("\\+98(\\d){9}")) {
            System.out.println("invalid phone number");
            phoneNumber = Menu.getScanner().nextLine();
        }
        personalInfo.setPhoneNumber(phoneNumber);
        System.out.print("password : (at least 8 character)");
        String password = Menu.getScanner().nextLine();
        while (password.length() < 8) {
            password = Menu.getScanner().nextLine();
        }
        personalInfo.setPassword(password);
    }


    public void showComments(ArrayList<Comment> comments) {
        for (int i = 1; i <= comments.size(); i++) {
            System.out.println(i + " . " + comments.get(i - 1).getCommentText());
        }
    }

    public void showProductInfo(Product product) {
        System.out.println("name : " + product.getName());
        System.out.println("category : " + product.getCategory().getName());
        System.out.println("price : " + product.getPrice());
        System.out.println("company : " + product.getCompany().getName());
        System.out.println("available count : " + product.getAvailableCount());
        for (String feature : product.getCategory().getFeatures()) {
            System.out.println(feature + " : " + product.getFeaturesMap().get(feature));
        }
    }

    public void showDigest(Product product) {
        System.out.println("name: " + product.getName());
        System.out.println("price: " + product.getPrice());
        System.out.println("category: " + product.getCategory());
        System.out.println("sellers: " + product.getSellers());
        System.out.println("score:" + product.getScore());
        System.out.println("description: " + product.getDescription());
        System.out.println("sale: " + Off.isProductInOff(product));
    }


    public void showBalance(User user) {
        System.out.println("user balance : " + user.getBalance());
    }

    public void getCommentInfo(ArrayList<String> commentInfo) {
        System.out.print("Title : ");
        String title = scanner.nextLine();
        commentInfo.add(title);
        System.out.println("Content : ");
        String content = scanner.nextLine();
        commentInfo.add(content);
    }

    public String getCompanyNameMenuFromUser() {
        System.out.print("company name: ");
        String companyName = Menu.getScanner().nextLine();
        while (!Company.hasCompanyWithName(companyName)) {
            System.out.println("invalid company name");
            companyName = Menu.getScanner().nextLine();
        }
        return companyName;
    }

    public void viewPersonalInfo(UserPersonalInfo personalInfo) {
        System.out.println(personalInfo);
    }


    public void showCategories(ArrayList<Category> categories) {
        for (Category category : categories) {
            System.out.println(category.getName());
        }
    }

    public Seller selectSeller() {
        System.out.println("select seller :");
        String sellerName;
        sellerName = Menu.getScanner().nextLine();
        return (Seller) User.getUserByUserName(sellerName);
    }

    public void compare(Product firstProduct, Product secondProduct) {
        System.out.println("product ID : " + firstProduct.getProductId() + " " + secondProduct.getProductId());
        System.out.println("name : " + firstProduct.getName() + " " + secondProduct.getName());
        System.out.println("price : " + firstProduct.getPrice() + " " + secondProduct.getPrice());
        Map<String, String> featuresOfFirstProduct = firstProduct.getFeaturesMap();
        Map<String, String> featuresOfSecondProduct = secondProduct.getFeaturesMap();
        for (String feature : featuresOfFirstProduct.keySet()) {
            System.out.println(feature + " " + featuresOfFirstProduct.get(feature) + " " + featuresOfSecondProduct.get(feature));
        }
    }

    public void showErrorMessage(String message) {
        System.out.println(message);
    }

    public void showAvailableFilters() {
        System.out.println("filter by price from [price] to [price]");
        System.out.println("filter by availability");
        System.out.println("filter by name [name]");
        System.out.println("filter by [category name] features");
    }

    public void showAvailableSorts() {
        System.out.println("sort by view");
        System.out.println("sort by score");
        System.out.println("sort by date");
    }

    public HashMap<String, String> addFilterByCategoryFeatures(FilterManager filterManager) {
        Category category = filterManager.getCategory();
        for (String feature : category.getFeatures()) {
            System.out.println(feature);
        }
        System.out.println("print features and their value that you want to filter by them. print exit to end this process");
        HashMap<String, String> features = filterManager.getCriteriaCategoryFeatures().getFeatures();
        String feature, value;
        while (!(feature = Menu.getScanner().nextLine()).equalsIgnoreCase("exit")) {
            value = Menu.getScanner().nextLine();
            features.remove(feature);
            features.put(feature, value);
        }
        return features;
    }

    public void showCurrentFilters(FilterManager filterManager) {
        for (Criteria filter : filterManager.getCurrentFilters()) {
            System.out.println(filter.getName());
        }
    }

    public void showSort(Comparator<Product> comparator) {
        if (comparator != null)
            System.out.println(comparator);
    }
}