package model;

import java.util.ArrayList;

public class Off {
    public static ArrayList<Off> acceptedOffs = new ArrayList<Off>();
    public static ArrayList<Off> inQueueExpectionOffs = new ArrayList<Off>();
    private String offId;
    private Seller seller;
    private ArrayList<Product> products;
    private State.OffState offState;
    private String startTime;
    private String endTime;
    private double discountAmount;

    public Off(String offId, String startTime, String endTime, double discountAmount, Seller seller) {
        this.offId = offId;
        this.offState = State.OffState.creatingProcess;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountAmount = discountAmount;
        this.seller = seller;
        products = new ArrayList<Product>();
        inQueueExpectionOffs.add(this);
    }
    public static Off getOffById(String offId){
        return null;
    }
}
