package model;

import java.util.ArrayList;

public class Discount {
    private String discountId;
    private String startTime;
    private String endTime;
    private double discountPercent;
    private double maxDiscountAmount;
    private int validityCount;
    private ArrayList<User> users;

    public Discount(String discountId, String startTime, String endTime, double discountPercent, double maxDiscountAmount, int validityCount) {
        this.discountId = discountId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercent = discountPercent;
        this.maxDiscountAmount = maxDiscountAmount;
        this.validityCount = validityCount;
        users = new ArrayList<User>();
    }

    public static Discount getDiscountById(String discountId){
        return null;
    }
}
