package model;

import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class BuyOrder extends Order {
    private static String fileAddress="database/BuyOrder.dat";
    private double payment;
    private double codedDiscountAmount;
    private transient HashMap<Product, Integer> products;
    private HashMap<String, Integer> productsId;
    private transient ArrayList<Seller> sellers;
    private ArrayList<String> sellersId;
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

    @Override
    public void setOrderType(){
        this.orderType = "BuyOrder";
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

    private void loadProducts() {
        products.clear();
        for (String id : productsId.keySet()) {
            products.put(Product.getProductById(id), productsId.get(id));
        }
    }

    private void saveProducts() {
        productsId.clear();
        for (Product product : products.keySet()) {
            productsId.put(product.getProductId(), products.get(product));
        }
    }

    private static void loadAllProducts(){
        for (Order order : allOrders) {
            if (order.getOrderType().equals("BuyOrder")){
                ((BuyOrder)order).loadProducts();
            }
        }
    }

    private static void saveAllProducts(){
        for (Order order : allOrders) {
            if (order.getOrderType().equals("BuyOrder")){
                ((BuyOrder)order).saveProducts();
            }
        }
    }

    private void loadSellers(){
        sellers.clear();
        for (String username : sellersId) {
            sellers.add((Seller)User.getUserByUserName(username));
        }
    }

    private void saveSellers(){
        sellersId.clear();
        for (Seller seller : sellers) {
            sellersId.add(seller.getUsername());
        }
    }

    private static void loadAllSellers(){
        for (Order order : allOrders) {
            if (order.getOrderType().equals("BuyOrder")){
                ((BuyOrder)order).loadSellers();
            }
        }
    }

    private static void saveAllSellers(){
        for (Order order : allOrders) {
            if (order.getOrderType().equals("BuyOrder")){
                ((BuyOrder)order).saveSellers();
            }
        }
    }

    public static void loadAllFields(){
        loadAllProducts();
        loadAllSellers();
    }

    public static void saveAllFields(){
        saveAllProducts();
        saveAllSellers();
    }

    public static void load() throws FileNotFoundException {
        BuyOrder[] buyOrders = (BuyOrder[]) Loader.load(BuyOrder[].class, fileAddress);
        if (buyOrders != null) {
            allOrders.addAll(new ArrayList<>(Arrays.asList(buyOrders)));
        }
    }


    public static void save() throws IOException {
        ArrayList<BuyOrder> allBuyOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getOrderType().equals("BuyOrder")){
                allBuyOrders.add((BuyOrder)order);
            }
        }
        Saver.save(allBuyOrders, fileAddress);
    }
}
