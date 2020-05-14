package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BuyOrder extends Order {
    private double payment;
    private double codedDiscountAmount;
    private HashMap<Product, Integer> products;
    private ArrayList<Seller> sellers;
    private DeliveryStatus deliveryStatus;

    public BuyOrder(String orderId, Date date, double payment, double codedDiscountAmount, HashMap<Product, Integer> products, ArrayList<Seller> sellers) {
        super(date);
        this.payment = payment;
        this.products = products;
        this.codedDiscountAmount = codedDiscountAmount;
        this.sellers = sellers;
        this.deliveryStatus = DeliveryStatus.DELIVERING;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }
}