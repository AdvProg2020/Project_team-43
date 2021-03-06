package model;

import model.database.Loader;
import model.database.Saver;
import model.database.Sqlite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class BuyOrder extends Order implements Serializable {
    private static String fileAddress = "database/BuyOrder.dat";
    private double payment;
    private double codedDiscountAmount;
    private HashMap<Product, Integer> products = new HashMap<>();
    private HashMap<String, Integer> productsId = new HashMap<>();
    private ArrayList<Seller> sellers;
    private ArrayList<String> sellersId = new ArrayList<>();

    public String getAddress() {
        return address;
    }

    private DeliveryStatus deliveryStatus;
    private String address;
    private String phoneNumber;

    public BuyOrder(double payment, double codedDiscountAmount, HashMap<String, Integer> productsId, ArrayList<String> sellersId, DeliveryStatus deliveryStatus, String address, String phoneNumber, Date date, String orderId) {
        super(date);
        this.payment = payment;
        this.productsId = productsId;
        this.codedDiscountAmount = codedDiscountAmount;
        this.sellersId = sellersId;
        this.deliveryStatus = deliveryStatus;
        this.phoneNumber = phoneNumber;
        this.address = address;
        products = new HashMap<>();
        sellers = new ArrayList<>();
        this.orderId = orderId;
        if (constructId <= Integer.parseInt(orderId)) {
            constructId = Integer.parseInt(orderId) + 1;
        }
    }

    public BuyOrder(Date date, double payment, double codedDiscountAmount, HashMap<Product, Integer> products, ArrayList<Seller> sellers, String phoneNumber, String address) {
        super(date);
        this.payment = payment;
        this.products = products;
        this.codedDiscountAmount = codedDiscountAmount;
        this.sellers = sellers;
        this.deliveryStatus = DeliveryStatus.DELIVERING;
        this.phoneNumber = phoneNumber;
        this.address = address;
        productsId = new HashMap<>();
        sellersId = new ArrayList<>();
    }

    @Override
    public void setOrderType() {
        this.orderType = "BuyOrder";
    }


    public HashMap<String, Integer> getProductsId() {
        return productsId;
    }

    public ArrayList<Seller> getSellers() {
        return sellers;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public ArrayList<String> getSellersId() {
        return sellersId;
    }

    public double getPayment() {
        return payment;
    }

    public double getCodedDiscountAmount() {
        return codedDiscountAmount;
    }

    @Override
    public String toString() {
        return "BuyOrder{" +
                "orderId=" + orderId +
                ", payment=" + payment +
                ", codedDiscountAmount=" + codedDiscountAmount +
                ", products=" + products +
                ", sellersId=" + sellersId +
                ", deliveryStatus=" + deliveryStatus +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    private void loadProducts() {
        products = new HashMap<>();
        for (String id : productsId.keySet()) {
            products.put(Product.getProductById(id), productsId.get(id));
        }
    }

    private void saveProducts() {
        productsId.clear();
        for (Product product : products.keySet()) {
            productsId.put(product.getProductId(), products.get(product));
        }
        products = null;
    }

    private static void loadAllProducts() {
        for (Order order : allOrders) {
            if (order.getOrderType().equals("BuyOrder")) {
                ((BuyOrder) order).loadProducts();
            }
        }
    }

    private static void saveAllProducts() {
        for (Order order : allOrders) {
            if (order.getOrderType().equals("BuyOrder")) {
                ((BuyOrder) order).saveProducts();
            }
        }
    }

    private void loadSellers() {
        sellers = new ArrayList<>();
        for (String username : sellersId) {
            sellers.add((Seller) User.getUserByUserName(username));
        }
    }

    private void saveSellers() {
        sellersId.clear();
        for (Seller seller : sellers) {
            sellersId.add(seller.getUsername());
        }
        sellers = null;
    }

    private static void loadAllSellers() {
        for (Order order : allOrders) {
            if (order.getOrderType().equals("BuyOrder")) {
                ((BuyOrder) order).loadSellers();
            }
        }
    }

    private static void saveAllSellers() {
        for (Order order : allOrders) {
            if (order.getOrderType().equals("BuyOrder")) {
                ((BuyOrder) order).saveSellers();
            }
        }
    }

    public static void loadAllFields() {
        loadAllProducts();
        loadAllSellers();
    }

    public static void saveAllFields() {
        saveAllProducts();
        saveAllSellers();
    }

    public static void load() throws FileNotFoundException {
        BuyOrder[] buyOrders = (BuyOrder[]) Loader.load(BuyOrder[].class, fileAddress);
        if (buyOrders != null) {
            Order.addAll(new ArrayList<>(Arrays.asList(buyOrders)));
        }
    }


    public static void save() throws IOException {
        ArrayList<BuyOrder> allBuyOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getOrderType().equals("BuyOrder")) {
                allBuyOrders.add((BuyOrder) order);
            }
        }
        new Sqlite().saveBuyOrder(allBuyOrders);
        //Saver.save(allBuyOrders, fileAddress);
    }
}
