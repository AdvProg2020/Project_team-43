package model;

import java.util.ArrayList;
import java.util.Date;

public class SellOrder extends Order {
    private double payment;
    private double offAmount;
    private ArrayList<Product> products;
    private Buyer buyer;
    private DeliveryStatus deliveryStatus;

    public SellOrder(double offAmount, Date date, double payment, Product product, Buyer buyer) {
        super(date);
        this.payment = payment;
        this.products.add(product);
        this.buyer = buyer;
        this.offAmount = offAmount;
        this.deliveryStatus = DeliveryStatus.DELIVERING;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean hasProductWithId(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    @Override
    public String toString() {
        String string = "SellOrder{" +
                "payment=" + payment +
                ", offAmount=" + offAmount +
                ", buyer=" + buyer +
                ", deliveryStatus=" + deliveryStatus +
                ", productsId=[";
        for (Product product : products) {
            string = string.concat(product.getProductId() + ", ");
        }
        string = string.concat("]}");
        return string;
    }
}
