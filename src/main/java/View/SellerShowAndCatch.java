package View;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SellerShowAndCatch {
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
            System.out.println("No order to show");
        } else {
            int i = 1;
            for (SellOrder order : orders) {
                System.out.println(i + ". " + order.toString());
                i++;
            }
        }
    }

    public void showProductInfo(Product product) {
        System.out.println(product.toString());
    }

    public void showProductList(HashMap<Product, Integer> products) {
        if (products.size() == 0) {
            System.out.println("No product to show");
        } else {
            System.out.println("Product List:");
            for (Product product : products.keySet()) {
                System.out.println(product.toString() + " Product amount : " + products.get(product));
            }
        }
    }

    public void showBuyers(ArrayList<Buyer> buyers) {
        System.out.println("Buyers of this product(Shown by username)");
        for (Buyer buyer : buyers) {
            System.out.println(buyer.getUsername());
        }

    }

    public void showRemoveProductDone() {
        System.out.println("Product removes successfully");
    }

    public void showCategories(ArrayList<Category> categories) {
        System.out.println("Categories :");
        for (Category category : categories) {
            System.out.println(category.getName());
        }
    }

    public void showBalance(double balance) {
        System.out.println(balance);
    }

    public void showOffs(ArrayList<Off> offs) {
        System.out.println("Offs :");
        for (Off off : offs) {
            showOff(off);
        }
    }

    public void showOff(Off off) {
        System.out.println(off.toString());
    }


    public String getNewField(String field) {
        System.out.println("new " + field + ":");
        return Menu.getScanner().nextLine();
    }

}