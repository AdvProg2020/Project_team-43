package Controller;

import model.*;
import View.SellerShowAndCatch;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerProcessor extends Processor {
    private static SellerProcessor instance = new SellerProcessor();

    public static SellerProcessor getInstance() {
        return instance;
    }

    private static SellerShowAndCatch sellerShowAndCatch = SellerShowAndCatch.getInstance();

    public void viewPersonalInfo() {
        sellerShowAndCatch.viewUser((Seller) user);
    }

    public void editField(String field) {
        //TODO : zena
    }

    public void viewCompanyInfo() {
        sellerShowAndCatch.showCompanyInfo(((Seller) user).getCompany());
    }

    public void viewSalesHistory() {
        sellerShowAndCatch.showSellOrders(((Seller) user).getOrders());
    }

    public void viewProductInfo(String productId) {
        if (((Seller) user).hasProductWithId(productId)) {
            sellerShowAndCatch.showProductInfo(((Seller) user).getProductById(productId));
        } else {
            sellerShowAndCatch.showInvalidId();
        }
    }

    public void viewProductList() {
        sellerShowAndCatch.showProductList((Seller) user);
    }

    public void viewBuyers(String productId) {
        Seller seller = (Seller) user;
        if (seller.hasProductWithId(productId)) {
            ArrayList<Buyer> buyers = new ArrayList<>();
            ArrayList<SellOrder> sellOrders = seller.getOrders();
            for (SellOrder order : sellOrders) {
                if (order.hasProductWithId(productId)) {
                    buyers.add(order.getBuyer());
                }
            }
            sellerShowAndCatch.showBuyers(buyers);
        } else {
            sellerShowAndCatch.showInvalidId();
        }
    }

    public void editProductInfo(String productId) {
        //TODO : zena
    }

    public String addProduct(String name, String companyName, String categoryName, String priceString) {
        if (Category.hasCategoryWithName(categoryName)) {
            if (Company.hasCompanyWithName(companyName)) {
                Company company = Company.getCompanyByName(companyName);
                Category category = Category.getCategoryByName(categoryName);
                int price = Integer.parseInt(priceString);
                Product product = new Product(name, company, price, category, (Seller) user);
                new ProductRequest(product);
                return "Product add successfully\nWaiting for manger to confirm";
            } else {
                return "No company with this name";
            }
        } else {
            return "No category with this name";
        }
    }

    public void removeProduct(String productId) {
        boolean hasProduct = ((Seller) user).hasProductWithId(productId);
        if (hasProduct) {
            ((Seller) user).removeProduct(((Seller) user).getProductById(productId));
            sellerShowAndCatch.showRemoveProductDone();
        } else {
            sellerShowAndCatch.showInvalidId();
        }

    }

    public void viewCategories() {
        sellerShowAndCatch.showCategories(Category.getAllCategories());
    }

    public void viewBalance() {
        sellerShowAndCatch.showBalance(user.getBalance());
    }

    public void viewOffs() {
        sellerShowAndCatch.showOffs(((Seller) user).getOffs());
    }

    public void viewOff(String offId) {
        boolean hasOff = ((Seller) user).hasOffWithId(offId);
        if (hasOff) {
            sellerShowAndCatch.showOff(((Seller) user).getOffById(offId));
        } else {
            sellerShowAndCatch.showInvalidId();
        }
    }

    public void editOff(String offId) {
        boolean hasOff = ((Seller) user).hasOffWithId(offId);
        if (hasOff) {
            //TODO : zena
        } else {
            sellerShowAndCatch.showInvalidId();
        }
    }

    public void addOff(String startTime, String endTime, Double discountAmount, ArrayList<String> productIds) {
        ArrayList<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            products.add(((Seller) user).getProductById(productId));
        }
        Off off = new Off(startTime, endTime, discountAmount, (Seller) user, products);
        new OffRequest(off);
    }

}
