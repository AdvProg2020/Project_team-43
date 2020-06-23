package View;

import javafx.util.Pair;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BuyerShowAndCatch {
    private static BuyerShowAndCatch ourInstance = new BuyerShowAndCatch();

    public static BuyerShowAndCatch getInstance() {
        return ourInstance;
    }

    private BuyerShowAndCatch() {
    }

    public void viewPersonalInfo(UserPersonalInfo personalInfo) {
        System.out.println(personalInfo);
    }

    public void viewBuyerOrders(ArrayList<BuyOrder> buyOrders) {
        for (BuyOrder order : buyOrders) {
            System.out.println(order);
        }
    }

    public void showBuyOrder(BuyOrder order) {
        System.out.println(order);
    }

    public void viewDiscountCodes(ArrayList<CodedDiscount> discounts) {
        System.out.println(discounts);
    }


    public void showProductsInCart(HashMap<Pair<Product, Seller>, Integer> products) {
        for (Pair<Product, Seller> productSellerPair : products.keySet()) {
            System.out.println(productSellerPair.getKey() + " " +
                    productSellerPair.getValue() + " " + products.get(productSellerPair));
        }
    }

    public String getNewField(String field) {
        System.out.println("new " + field + ":");
        return Menu.getScanner().nextLine().trim();
    }
}
