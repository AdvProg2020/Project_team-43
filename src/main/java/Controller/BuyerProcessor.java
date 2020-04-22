package Controller;

import model.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyerProcessor extends Processor {
    public void viewPersonalInfo() {
        viewManager.viewPersonalInfo(User.getUserByUserName(user.getUsername()).getUserPersonalInfo());
    }

    public void editField(String field) {
        //TODO : error handling

    }

    public void viewOrders() {
        ArrayList<BuyOrder> orders = ((Buyer) user).getOrders();
        viewManager.viewBuyerOrders(orders);
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
    }
    public void showProductsInCart(){
        viewManager.showProductsInCart(((Buyer)user).getBuyerCart());
    }
    public void increaseProduct(String productId) {
        //TODO : error handling
        ((Buyer)user).increaseCart(productId);

    }
    public void decreaseProduct(String productId){
        ((Buyer)user).decreaseCart(productId);
    }
    public int showTotalPrice(){
        return ((Buyer)user).getCartPrice();
    }
}
