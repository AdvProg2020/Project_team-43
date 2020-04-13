package model;

import java.util.ArrayList;
import java.util.Date;

public class SellOrder extends Order {
    private double payment;
    private double offAmount;///////object auction ham mitoone bashe
    private ArrayList<Product> products;
    private Buyer buyer;
    private DeliveryStatus deliveryStatus;


    public SellOrder(String orderId, Date date, double payment, ArrayList<Product> products, Buyer buyer) {
        super(orderId, date);
        this.payment = payment;
        this.products = products;
        this.buyer = buyer;
    }


}
