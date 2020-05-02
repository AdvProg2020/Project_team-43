package View;

import model.*;
import model.filters.Criteria;
import model.filters.FilterManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ShowAndCatch {
    private static ShowAndCatch ourInstance = new ShowAndCatch();
    private static Scanner scanner = Menu.getScanner();

    public static ShowAndCatch getInstance() {
        return ourInstance;
    }

    private ShowAndCatch() {
    }

    public void getOffProducts(ArrayList<String> products) {
        while (true) {
            //fill products
        }
    }

    public void showOffs(ArrayList<Off> offs) {
        for (int i = 1; i <= offs.size(); i++) {
            System.out.println(i + " : " + "\n" + "Off Id : " + offs.get(i - 1).getOffId());
            System.out.println("Seller user name : " + offs.get(i - 1).getSellerName());
            System.out.println("Discount amount : " + offs.get(i - 1).getDiscountAmount());
            System.out.println("Date : " + offs.get(i - 1).getStartTime() + " - " + offs.get(i - 1).getEndTime());
            System.out.println("Products : ");
            for (Product product : offs.get(i - 1).getProducts()) {
                System.out.println(product.getName());
            }

        }
    }

    public void showOff(Off off) {
        System.out.println("Off Id : " + off.getOffId());
        System.out.println("Seller user name : " + off.getSellerName());
        System.out.println("Discount amount : " + off.getDiscountAmount());
        System.out.println("Date : " + off.getStartTime() + " - " + off.getEndTime());
        System.out.println("Products : ");
        for (Product product : off.getProducts()) {
            System.out.println(product.getName());
        }
    }

    public void getProductInfo(ArrayList<String> productInfo) {
        System.out.print("Id : ");
        String productId = scanner.nextLine();
        productInfo.add(productId);
        System.out.print("name : ");
        String name = scanner.nextLine();
        productInfo.add(name);
        System.out.print("company : ");
        String company = scanner.nextLine();
        productInfo.add(company);
        System.out.print("category : ");
        String category = scanner.nextLine();
        productInfo.add(category);
        System.out.print("price : ");
        String price = scanner.nextLine();
        productInfo.add(price);
    }

    public void getProductFeatures(ArrayList<String> features1, ArrayList<String> features2) {
        for (String feature : features1) {
            System.out.print(feature + " : ");
            String feature2 = scanner.nextLine();
            features2.add(feature2);
        }
    }

    public void showProducts(ArrayList<Product> products) {
        for (Product product : products) {
            System.out.println(product.getName());
        }
    }

    public void showSellOrders(ArrayList<SellOrder> orders) {
        for (SellOrder order : orders) {
            System.out.println(order.getOrderId());
        }
    }

    public void showCompanyInfo(Company company) {
        System.out.println("name : " + company.getName());
        System.out.println("company information : " + company.getInfo());
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
        System.out.print("phone number : ");
        String phoneNumber = Menu.getScanner().nextLine();
        personalInfo.setPhoneNumber(phoneNumber);
        System.out.print("password : ");
        String password = Menu.getScanner().nextLine();
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
        System.out.println("sellers: " + product.getSeller());
        System.out.println("score:" + product.getScore());
        System.out.println("description: " + product.getDescription());
        System.out.println("sale: " + Off.isProductInOff(product));
    }


    public void getOffInfo(ArrayList<String> offInfo) {
        System.out.print("Off Id : ");
        String offId = scanner.nextLine();
        offInfo.add(offId);
        System.out.print("Start time : ");
        String startTime = scanner.nextLine();
        offInfo.add(startTime);
        System.out.print("End time : ");
        String endTime = scanner.nextLine();
        offInfo.add(endTime);
        System.out.print("Off discount amount : ");
        String offDiscountAmount = scanner.nextLine();
        offInfo.add(offDiscountAmount);
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
        return Menu.getScanner().nextLine();
    }

    public void viewPersonalInfo(UserPersonalInfo personalInfo) {
        System.out.println(personalInfo);
    }

    public void viewBuyerOrders(ArrayList<BuyOrder> buyOrders) {
        for (BuyOrder order : buyOrders) {
            System.out.println(order);
        }
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
        System.out.println("filter by [category name] features");
    }

    public HashMap<String, String> filterByCategoryFeatures() {
        System.out.println("please select a category : ");
        String categoryName = Menu.getScanner().nextLine();
        for (String feature : Category.getCategoryByName(categoryName).getFeatures()) {
            System.out.println(feature);
        }
        System.out.println("print features and their value that you want to filter by them. print exit to end this process");
        HashMap<String, String> features = new HashMap<>();
        String feature, value;
        while (!(feature = Menu.getScanner().nextLine()).equalsIgnoreCase("exit")) {
            value = Menu.getScanner().nextLine();
            features.put(feature, value);
        }
        return features;
    }

    public HashMap<String, String> addFilterByCategoryFeatures() {
        Category category = FilterManager.getInstance().getCategory();
        for (String feature : category.getFeatures()) {
            System.out.println(feature);
        }
        System.out.println("print features and their value that you want to filter by them. print exit to end this process");
        HashMap<String, String> features = FilterManager.getInstance().getCriteriaCategoryFeatures().getFeatures();
        String feature, value;
        while (!(feature = Menu.getScanner().nextLine()).equalsIgnoreCase("exit")) {
            value = Menu.getScanner().nextLine();
            features.remove(feature);
            features.put(feature, value);
        }
        return features;
    }

    public void showCurrentFilters() {
        for (Criteria filter : FilterManager.getInstance().getCurrentFilters()) {
            System.out.println(filter.getName());
        }
    }
}