package model;

import java.util.*;

public abstract class Order {
    private static ArrayList<Order> allOrders = new ArrayList<Order>();
    private String orderId;
    private Date date;

    public Order(String orderId, Date date) {
        this.orderId = orderId;
        this.date = date;
        allOrders.add(this);
    }

}
