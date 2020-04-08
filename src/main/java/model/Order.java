package model;

import java.util.ArrayList;

public class Order {
    public static ArrayList<Order> allOrders = new ArrayList<Order>();
    private String OrderId;
    private String date;
    private double payment;
    private double discountAmount;///////object auction ham mitoone bashe
    private ArrayList<Product> products;
    private Seller seller;
    private Buyer buyer;
    ///////////////vaziat tahvil


    public Order(String orderId, String date, double payment, double discountAmount, ArrayList<Product> products, Seller seller, Buyer buyer) {
        OrderId = orderId;
        this.date = date;
        this.payment = payment;
        this.discountAmount = discountAmount;
        this.products = products;
        this.seller = seller;
        this.buyer = buyer;
    }

    public static Order getOrderById(String orderId){
        return null;
    }



}
