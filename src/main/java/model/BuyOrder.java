package model;

import java.util.ArrayList;
import java.util.Date;

public class BuyOrder extends Order {
    private double payment;
    private double codedDiscountAmount;///////object auction ham mitoone bashe
    private ArrayList<Product> products;
    private Seller seller;
    private DeliveryStatus deliveryStatus;


    public BuyOrder(String orderId, Date date, double payment, ArrayList<Product> products, Seller seller) {
        super(orderId, date);
        this.payment = payment;
        this.products = products;
        this.seller = seller;
    }

}
