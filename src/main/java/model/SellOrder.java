package model;

import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SellOrder extends Order  implements Serializable {
    private static String fileAddress = "database/SellOrder.dat";
    private double payment;
    private double offAmount;
    private Product product;
    private String productId;
    private Buyer buyer;
    private String buyerUsername;
    private DeliveryStatus deliveryStatus;
    private int number;

    public SellOrder(double offAmount, Date date, double payment, Product product, Buyer buyer) {
        super(date);
        this.payment = payment;
        this.product = product;
        this.buyer = buyer;
        this.offAmount = offAmount;
        this.deliveryStatus = DeliveryStatus.DELIVERING;
        number = 1;
    }

    public SellOrder(double offAmount, Date date, double payment, Product product, Buyer buyer, int number) {
        super(date);
        this.payment = payment;
        this.product = product;
        this.buyer = buyer;
        this.offAmount = offAmount;
        this.deliveryStatus = DeliveryStatus.DELIVERING;
        this.number = number;
    }

    @Override
    public void setOrderType() {
        this.orderType = "SellOrder";
    }

    public Product getProduct() {
        return product;
    }

    public boolean hasProductWithId(String productId) {
        return product.getProductId().equals(productId);
    }

    public Buyer getBuyer() {
        return buyer;
    }

    @Override
    public String getOrderId() {
        return super.getOrderId();
    }


    @Override
    public String toString() {
        return "SellOrder{" +
                "orderId=" + orderId +
                ", payment=" + payment +
                ", offAmount=" + offAmount +
                ", product=" + product +
                ", buyerUsername='" + buyerUsername + '\'' +
                ", deliveryStatus=" + deliveryStatus +
                ", orderType='" + orderType + '\'' +
                '}';
    }

    private void loadBuyer() {
        buyer = (Buyer) User.getUserByUserName(buyerUsername);
    }

    private void loadProduct() {
        product = Product.getProductById(productId);
    }

    private void saveProduct() {
        productId = product.getProductId();
        product = null;
    }

    private void saveBuyer() {
        buyerUsername = buyer.getUsername();
        buyer = null;
    }

    private static void loadAllBuyers() {
        for (Order order : allOrders) {
            if (order.getOrderType().equals("SellOrder")) {
                ((SellOrder) order).loadBuyer();
            }
        }
    }

    private static void saveAllBuyers() {
        for (Order order : allOrders) {
            if (order.getOrderType().equals("SellOrder")) {
                ((SellOrder) order).saveBuyer();
            }
        }
    }

    private static void loadAllProducts() {
        for (Order order : allOrders) {
            if (order.getOrderType().equals("SellOrder")) {
                ((SellOrder) order).loadProduct();
            }
        }
    }

    private static void saveAllProducts() {
        for (Order order : allOrders) {
            if (order.getOrderType().equals("SellOrder")) {
                ((SellOrder) order).saveProduct();
            }
        }
    }

    public static void loadAllFields() {
        loadAllProducts();
        loadAllBuyers();
    }

    public static void saveAllFields() {
        saveAllProducts();
        saveAllBuyers();
    }

    public static void load() throws FileNotFoundException {
        SellOrder[] sellOrders = (SellOrder[]) Loader.load(SellOrder[].class, fileAddress);
        if (sellOrders != null) {
            Order.addAll(new ArrayList<>(Arrays.asList(sellOrders)));
        }
    }


    public static void save() throws IOException {
        ArrayList<SellOrder> allSellOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getOrderType().equals("SellOrder")) {
                allSellOrders.add((SellOrder) order);
            }
        }
        Saver.save(allSellOrders, fileAddress);
    }

    public double getPayment() {
        return payment;
    }

    public double getOffAmount() {
        return offAmount;
    }


    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public int getNumber() {
        return number;
    }
}
