package model;

import java.util.ArrayList;

public class BuyOrder {
    public static ArrayList<BuyOrder> allBuyOrders = new ArrayList<BuyOrder>();
    private String OrderId;
    private String date;
    private double payment;
    private double CodedDiscountAmount;///////object auction ham mitoone bashe
    private ArrayList<Product> products;
    private Seller seller;
    ///////////////vaziat ersal


    public BuyOrder(String orderId, String date, double payment, ArrayList<Product> products, Seller seller) {
        OrderId = orderId;
        this.date = date;
        this.payment = payment;
        this.products = products;
        this.seller = seller;
    }

    public static SellOrder getBuyOrderById(String orderId){
        return null;
    }

}
