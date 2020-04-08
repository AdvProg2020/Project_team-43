package model;

import java.util.ArrayList;

public class Buyer extends User {
    //TODO
    private ArrayList<Product> cart;

    public Buyer(String nickName, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(nickName, firstName, lastName, email, phoneNumber, password);
        this.userType = 1;
        cart = new ArrayList<Product>();
        User.allUsers.add(this);
    }

    public void viewPersonalInfo() {

    }

    public void editFields(String field) {

    }

    //////////viewCart??????

    public void showProducts() {

    }

    public void viewProduct(String productId) {

    }

    public void increaseCart(String productId) {

    }

    public void decreaseCart(String productId){

    }

    public void showTotalPrice(){

    }

    public void purchase(){

    }

    public void viewOrders(){

    }//////view logs

    public void showOrder(String orderId){

    }

    public void rateProduct(String productId, int score){

    }

    public void viewBalance(){

    }

    public void viewDiscounts(){

    }


}
