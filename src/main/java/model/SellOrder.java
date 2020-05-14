package model;

import java.util.ArrayList;
import java.util.Date;

public class SellOrder extends Order {
    private double payment;
    private double offAmount;
    private Product product;
    private Buyer buyer;
    private DeliveryStatus deliveryStatus;

    public SellOrder(double offAmount, Date date, double payment, Product product, Buyer buyer) {
        super(date);
        this.payment = payment;
        this.product = product;
        this.buyer = buyer;
        this.offAmount = offAmount;
        this.deliveryStatus = DeliveryStatus.DELIVERING;
    }

    public Product getProducts() {
        return product;
    }

    public boolean hasProductWithId(String productId) {
        return product.getProductId().equals(productId);
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
        string = string.concat(product.getProductId() + ", ");
        string = string.concat("]}");
        return string;
    }
}
