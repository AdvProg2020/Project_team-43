package View;

import model.BuyOrder;
import model.CodedDiscount;
import model.Product;
import model.UserPersonalInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyerShowAndCatch {
    private static BuyerShowAndCatch ourInstance = new BuyerShowAndCatch();
    public static BuyerShowAndCatch getInstance() {
        return ourInstance;
    }
    private BuyerShowAndCatch() {
    }
    public void viewPersonalInfo(UserPersonalInfo personalInfo){
        System.out.println(personalInfo);
    }
    public void viewBuyerOrders(ArrayList<BuyOrder> buyOrders){
        for (BuyOrder order : buyOrders) {
            System.out.println(order);
        }
    }
    public void showBuyOrder(BuyOrder order){
        System.out.println(order);
    }
    public void viewDiscountCodes(ArrayList<CodedDiscount> discounts){
        System.out.println(discounts);
    }
    public void showProductsInCart(HashMap<Product,Integer> products){
        for (Product product : products.keySet()) {
            System.out.println(product+" "+products.get(product));
        }
    }
    public String getNewField(String field){
        System.out.println("new "+field + ":");
        return Menu.getScanner().nextLine();
    }
}
