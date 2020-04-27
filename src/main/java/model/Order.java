package model;

import java.util.Date;
import java.util.ArrayList;

public abstract class Order {
    public static int constructId = 0;
    private static ArrayList<Order> allOrders = new ArrayList<Order>();
    private String orderId;
    private Date date;

    public Order(Date date) {
        this.orderId = "" + constructId;
        this.date = date;
        allOrders.add(this);
        constructId += 1;
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
}
