package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;

public abstract class Order {
    public static int constructId = 0;
    public static ArrayList<Order> allOrders = new ArrayList<Order>();
    protected String orderId;
    private Date date;
    protected String orderType;

    public Order(Date date) {
        this.orderId = "" + constructId;
        this.date = date;
        allOrders.add(this);
        constructId += 1;
        setOrderType();
    }

    public abstract void setOrderType();

    public static void addAll(ArrayList<Order> orders) {
        allOrders.addAll(orders);
        for (Order order : orders) {
            int id = Integer.parseInt(order.getOrderId());
            if (constructId <= id) {
                constructId = id + 1;
            }
        }
    }

    public String getOrderType() {
        return orderType;
    }

    public String getOrderId() {
        return orderId;
    }

    public static Order getOrderById(String orderId) {
        for (Order order : allOrders) {
            if (order.getOrderId().equals(orderId))
                return order;
        }
        return null;
    }

    public static void load() throws FileNotFoundException {
        BuyOrder.load();
        SellOrder.load();
    }

    public static void save() throws IOException {
        BuyOrder.save();
        SellOrder.save();
    }

    public static void loadAllFields() {
        SellOrder.loadAllFields();
        BuyOrder.loadAllFields();
    }

    public static void saveAllFields() {
        SellOrder.saveAllFields();
        BuyOrder.saveAllFields();
    }

    public static ArrayList<Order> getAllOrders() {
        return allOrders;
    }
}
