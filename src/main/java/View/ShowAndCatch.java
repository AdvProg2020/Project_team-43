package View;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class ShowAndCatch {
    private static ShowAndCatch ourInstance = new ShowAndCatch();
    private Scanner scanner = Menu.scanner;

    public static ShowAndCatch getInstance() {
        return ourInstance;
    }

    private ShowAndCatch() {
    }

    public void getOffProducts(ArrayList<Product> products) {
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

    public void getDiscountCodedInfo(ArrayList<String> discountCodedInfo) {
        System.out.print("Discount code : ");
        String discountCode = scanner.nextLine();
        discountCodedInfo.add(discountCode);
        System.out.print("Start time : ");
        String startTime = scanner.nextLine();
        discountCodedInfo.add(startTime);
        System.out.print("End time : ");
        String endTime = scanner.nextLine();
        discountCodedInfo.add(endTime);
        System.out.print("Discount amount : ");
        String discountAmount = scanner.nextLine();
        discountCodedInfo.add(discountAmount);
        System.out.print("repeat : ");
        String repeat = scanner.nextLine();
        discountCodedInfo.add(repeat);
    }

    public void getManagerInfo(ArrayList<String> managerInfo){
        System.out.print("user name : ");
        String userName = scanner.nextLine();
        managerInfo.add(userName);
        System.out.print("first name : ");
        String firstName = scanner.nextLine();
        managerInfo.add(firstName);
        System.out.print("last name : ");
        String lastName = scanner.nextLine();
        managerInfo.add(lastName);
        System.out.print("email : ");
        String email = scanner.nextLine();
        managerInfo.add(email);
        System.out.print("phone number : ");
        String phoneNumber = scanner.nextLine();
        managerInfo.add(phoneNumber);
        System.out.print("password : ");
        String password = scanner.nextLine();
        managerInfo.add(password);
    }
    public void viewUser(User user){
        System.out.println("user name : "+user.getUsername());
        System.out.println("first name : "+ user.getUserPersonalInfo().getFirstName());
        System.out.println("last name : "+user.getUserPersonalInfo().getLastName());
        System.out.println("email : "+user.getUserPersonalInfo().getEmail());
        System.out.println("phone number : "+user.getUserPersonalInfo().getPhoneNumber());
    }
}