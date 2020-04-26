package Controller;

import View.BuyerShowAndCatch;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyerProcessor extends Processor {
    private static BuyerProcessor instance = new BuyerProcessor();
    public static BuyerProcessor getInstance() {
        return instance;
    }
    private static HashMap<Product,Integer> buyerCart=new HashMap<Product,Integer>();
    private static BuyerShowAndCatch buyerViewManager = BuyerShowAndCatch.getInstance();
    private BuyerProcessor(){}
    public void viewPersonalInfo() {
        buyerViewManager.viewPersonalInfo(user.getUserPersonalInfo());
    }
    public String editBuyerField(String command) {
        Pattern pattern=Pattern.compile("edit (\\S+)");
        Matcher matcher = pattern.matcher(command);
        if (command.equalsIgnoreCase("back"))
            return null;
        if (!matcher.find())
            return "invalid field";
        String field=matcher.group(1);
        String newField=buyerViewManager.getNewField(field);
        ((Buyer)user).editFields(field,newField);
        return (field+" successfully changed to "+newField);
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
        Product.getProductById(productId).rateProduct(score);
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
    public void setBuyerCart(){
        ((Buyer)user).setBuyerCart(buyerCart);
    }
    public void logout(){
        user=null;
        isLogin=false;
        buyerCart.clear();
    }
}
