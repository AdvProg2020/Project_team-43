package Controller;

import model.*;
import View.SellerShowAndCatch;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerProcessor extends Processor {
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
        sellerShowAndCatch.showBuyers((Seller) user, productId);
    }

    public void editProductInfo(String productId) {
        //TODO : zena
    }

    public void addProduct() {
        //product ro besazam ya na?
        HashMap<String, String> productInfo = sellerShowAndCatch.getProductInfo();

    }

    public void removeProduct(String productId) {
        boolean hasProduct = ((Seller) user).hasProductWithId(productId);
        if (hasProduct) {
            ((Seller) user).removeProduct(((Seller) user).getProductById(productId));
        }
        sellerShowAndCatch.removeProduct(hasProduct);
    }

}
