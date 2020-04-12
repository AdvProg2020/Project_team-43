package model;

import java.util.ArrayList;

public class SellOrder {
    public static ArrayList<SellOrder> allSellOrders = new ArrayList<SellOrder>();
    private String OrderId;
    private String date;
    private double payment;
    private double OffAmount;///////object auction ham mitoone bashe
    private ArrayList<Product> products;
    private Buyer buyer;
    ///////////////vaziat tahvil


    public SellOrder(String orderId, String date, double payment, ArrayList<Product> products, Buyer buyer) {
        OrderId = orderId;
        this.date = date;
        this.payment = payment;
        this.products = products;
        this.buyer = buyer;
    }
    public static SellOrder getSellOrderById(String orderId){
        return null;
    }



}
