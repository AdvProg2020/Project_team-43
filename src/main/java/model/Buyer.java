package model;

import Controller.Processor;

import java.util.ArrayList;
import java.util.HashMap;

public class Buyer extends User {
    //TODO
    private ArrayList<CodedDiscount> discounts;
    private ArrayList<Product> cart;
    private HashMap<Product, Integer> buyerCart;

    public HashMap<Product, Integer> getBuyerCart() {
        return buyerCart;
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    private ArrayList<BuyOrder> orders;

    public Buyer(String username, UserPersonalInfo userPersonalInfo) {
        super(username, userPersonalInfo);
        discounts = new ArrayList<CodedDiscount>();
        cart = new ArrayList<Product>();
        orders = new ArrayList<BuyOrder>();
        buyerCart = new HashMap<>();
        allUsers.add(this);
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
        buyerCart.replace(Product.getProductById(productId),
                buyerCart.get(Product.getProductById(productId)),
                buyerCart.get(Product.getProductById(productId)) + 1);
    }

    public void decreaseCart(String productId) {
        if (buyerCart.get(Product.getProductById(productId)) == 1) {
            buyerCart.remove(Product.getProductById(productId));
        } else {
            buyerCart.replace(Product.getProductById(productId),
                    buyerCart.get(Product.getProductById(productId)),
                    buyerCart.get(Product.getProductById(productId)) - 1);
        }
    }

    public int getCartPrice() {
        int price=0;
        for (Product product : buyerCart.keySet()){
            price+=buyerCart.get(product)*product.getPrice();
        }
        return price;
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

    public static void addBuyer(UserPersonalInfo personalInfo, String username) {
        new Buyer(username, personalInfo);
    }

}
