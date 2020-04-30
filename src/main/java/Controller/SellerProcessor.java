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
        System.out.println("What tha fuck????");
    }

    public void viewCompanyInfo() {
        sellerShowAndCatch.showCompanyInfo(((Seller) user).getCompany());
    }

    public void viewSalesHistory() {
        sellerShowAndCatch.showSellOrders(((Seller) user).getOrders());
    }

    public void viewProductInfo(String productId) {
        sellerShowAndCatch.showProductInfo((Seller) user, productId);
    }

    public void viewProductList() {
        sellerShowAndCatch.showProductList((Seller) user);
    }

    public void viewBuyers(String productId) {
        Seller seller = (Seller) user;
        ArrayList<Buyer> buyers = new ArrayList<>();
        ArrayList<SellOrder> sellOrders = seller.getOrders();
        for (SellOrder order : sellOrders) {
            if (order.hasProductWithId(productId)) {
                buyers.add(order.getBuyer());
            }
        }
        sellerShowAndCatch.showBuyers(buyers, seller.hasProductWithId(productId));
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
        }
        sellerShowAndCatch.removeProduct(hasProduct);
    }

    public void viewCategories() {
        sellerShowAndCatch.showCategories(Category.getAllCategories());
    }

    public void viewBalance() {
        sellerShowAndCatch.showBalance(user.getBalance());
    }

}
