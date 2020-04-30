package View;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SellerShowAndCatch {
    private Scanner scanner = Menu.getScanner();
    private static SellerShowAndCatch ourInstance = new SellerShowAndCatch();

    public static SellerShowAndCatch getInstance() {
        return ourInstance;
    }

    private SellerShowAndCatch() {
    }

    public void viewUser(Seller user) {
        System.out.println(user.getUserPersonalInfo().toString());
    }

    public void showCompanyInfo(Company company) {
        System.out.println(company.toString());
    }

    public void showSellOrders(ArrayList<SellOrder> orders) {
        System.out.println("Your sales history:");
        if (orders.size() == 0) {
            System.out.println("Nothing to show");
        } else {
            int i = 1;
            for (SellOrder order : orders) {
                System.out.println(i + ". " + order.toString());
                i++;
            }
        }
    }

    public void showProductInfo(Seller user, String productId) {
        if (!user.hasProductWithId(productId)) {
            System.out.println("Invalid id");
        } else {
            System.out.println(user.getProductById(productId).toString());
        }
    }

    public void showProductList(Seller user) {
        ArrayList<Product> products = user.getProducts();
        if (products.size() == 0) {
            System.out.println("Nothing to show");
        } else {
            System.out.println("Product List:");
            for (Product product : products) {
                System.out.println(product.toString());
            }
        }
    }

    public void showBuyers(ArrayList<Buyer> buyers, boolean validId) {
        if (validId) {
            System.out.println("Buyers of this product(Shown by username)");
            for (Buyer buyer : buyers) {
                System.out.println(buyer.getUsername());
            }
        } else {
            System.out.println("Invalid id");
        }
    }

    public HashMap<String, String> getProductInfo() {
        HashMap<String, String> productInfo = new HashMap<>();
        System.out.println("Please enter product information");

        System.out.print("name : ");
        productInfo.put("name", scanner.nextLine());
        System.out.print("company : ");
        productInfo.put("company", scanner.nextLine());
        System.out.print("category : ");
        productInfo.put("category", scanner.nextLine());
        System.out.print("price : ");
        productInfo.put("price", scanner.nextLine());
        return productInfo;
    }

    public void removeProduct(boolean hasProduct) {
        if (hasProduct) {
            System.out.println("Product removes successfully");
        } else {
            System.out.println("Invalid id");
        }
    }

    public void showCategories(ArrayList<Category> categories) {
        for (Category category : categories) {
            System.out.println(category.getName());
        }
    }

    public void showBalance(double balance) {
        System.out.println(balance);
    }

}