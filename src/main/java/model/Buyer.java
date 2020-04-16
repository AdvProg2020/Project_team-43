package model;

import java.util.ArrayList;

public class Buyer extends User {
    //TODO
    private ArrayList<CodedDiscount> discounts;
    private ArrayList<Product> cart;
    private ArrayList<BuyOrder> orders;

    public Buyer(String username, UserPersonalInfo userPersonalInfo) {
        super(username, userPersonalInfo);
        discounts = new ArrayList<CodedDiscount>();
        cart = new ArrayList<Product>();
        orders = new ArrayList<BuyOrder>();
    }

    @Override
    public void setUserType() {
        userType = UserType.BUYER;
    }

    public void viewPersonalInfo() {

    }

    public void editFields(String field) {

    }

    //////////viewCart??????


    public ArrayList<BuyOrder> getOrders() {
        return orders;
    }

    public void showProducts() {

    }

    public void viewProduct(String productId) {

    }

    public void increaseCart(String productId) {

    }

    public void decreaseCart(String productId) {

    }

    public void showTotalPrice() {

    }

    public void purchase() {

    }

    public void viewOrders() {

    }//////View logs

    public void showOrder(String orderId) {

    }

    public void rateProduct(String productId, int score) {

    }

    public void viewBalance() {

    }

    public void viewDiscounts() {

    }


}
