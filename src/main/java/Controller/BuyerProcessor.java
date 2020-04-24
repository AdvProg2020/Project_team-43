package Controller;

import View.BuyerShowAndCatch;
import View.ShowAndCatch;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyerProcessor extends Processor {
    private static HashMap<Product,Integer> buyerCart=new HashMap<Product,Integer>();
    private static BuyerShowAndCatch buyerViewManager = BuyerShowAndCatch.getInstance();
    public void viewPersonalInfo() {
        buyerViewManager.viewPersonalInfo(user.getUserPersonalInfo());
    }
    public void editField(String field) {
        //TODO : error handling

    }

    public void viewOrders() {
        ArrayList<BuyOrder> orders = ((Buyer) user).getOrders();
        buyerViewManager.viewBuyerOrders(orders);
    }

    public void manageOrders(String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern showOrderPattern = Pattern.compile("show order (.+)");
        Matcher showOrderMatcher = showOrderPattern.matcher(command);
        Pattern rateProductPattern = Pattern.compile("rate (.+) (\\d)");
        Matcher rateProductMatcher = rateProductPattern.matcher(command);
        if (showOrderMatcher.matches()) {
            this.showOrder(showOrderMatcher.group(1));
        } else if (rateProductMatcher.matches()) {
            this.rateProduct(rateProductMatcher.group(1), Integer.parseInt(rateProductMatcher.group(2)));
        }
    }

    public void showOrder(String orderId) {
        buyerViewManager.showBuyOrder((BuyOrder)Order.getOrderById(orderId));
        //TODO : error handling
    }

    public void rateProduct(String productId, int score) {
        //TODO : error handling
    }

    public void viewBalance() {
        //TODO : error handling
        viewManager.showBalance(user);
    }

    public void viewBuyerDiscountCodes() {
        //TODO : error handling
        buyerViewManager.viewDiscountCodes(((Buyer)user).getDiscounts());
    }
    public void showProductsInCart(){
        buyerViewManager.showProductsInCart(((Buyer)user).getBuyerCart());
    }
    public void increaseProduct(String productId) {
        //TODO : error handling
        ((Buyer)user).increaseCart(productId);

    }
    public void decreaseProduct(String productId){
        ((Buyer)user).decreaseCart(productId);
    }
    public double showTotalPrice(){
        return ((Buyer)user).getCartPrice();
    }
    public boolean checkDiscountCode(String code){
        if (!CodedDiscount.isCodedDiscountWithThisCode(code))
            return false;
        return ((Buyer)user).checkDiscountCode(CodedDiscount.getDiscountById(code));
    }
    public String payment(String address, String phoneNumber,double discount ){
        if (user.getBalance()<((Buyer)user).getCartPrice())
            return "insufficient money";
        ((Buyer)user).purchase();
        buyerCart.clear();
        return "payment done";
    }
    public static void setBuyerCart(){
        ((Buyer)user).setBuyerCart(buyerCart);
    }
}
