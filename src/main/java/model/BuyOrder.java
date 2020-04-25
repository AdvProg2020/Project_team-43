package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BuyOrder extends Order {
    private double payment;
    private double codedDiscountAmount;//object ham mitone bashe
    private HashMap<Product,Integer> products;
    private ArrayList<Seller> sellers;
    private DeliveryStatus deliveryStatus;

    public BuyOrder(String orderId, Date date, double payment, HashMap<Product,Integer> products, ArrayList<Seller> sellers) {
        super(orderId, date);
        this.payment = payment;
        this.products = products;
        this.sellers = sellers;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }
}
