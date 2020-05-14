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
    private String address;
    private String phoneNumber;

    public BuyOrder(Date date, double payment, double codedDiscountAmount, HashMap<Product, Integer> products, ArrayList<Seller> sellers, String phoneNumber, String address) {
        super(date);
        this.payment = payment;
        this.products = products;
        this.codedDiscountAmount = codedDiscountAmount;
        this.sellers = sellers;
        this.deliveryStatus = DeliveryStatus.DELIVERING;
        this.phoneNumber = phoneNumber;
        this.address = address;

    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        String string = "BuyOrder{" +
                "payment=" + payment +
                ", codedDiscountAmount=" + codedDiscountAmount +
                ", deliveryStatus=" + deliveryStatus +
                ", sellersUsername=[";
        for (Seller seller : sellers) {
            string = string.concat(seller.getUsername() + ", ");
        }
        string = string.concat("], productsId and numbers=[");
        for (Product product : products.keySet()) {
            string = string.concat("( " + product.getProductId() + ", " + products.get(product) + " )");
        }
        string = string.concat("]}");
        return string;
    }
}
