package model;

import java.util.Date;
import java.util.ArrayList;

public class CodedDiscount {
    public static ArrayList<CodedDiscount> allCodedDiscount = new ArrayList<CodedDiscount>();
    private String discountCode;
    private String startTime;
    private String endTime;
    private double discountAmount;
    private int repeat;
    private ArrayList<Buyer> users;

    public CodedDiscount(String discountCode, String startTime, String endTime, double discount, int repeat) {
        this.discountCode = discountCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountAmount = discount;
        this.repeat = repeat;
        users = new ArrayList<Buyer>();
        allCodedDiscount.add(this);
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getRepeat() {
        return repeat;
    }

    //setter ha bayad TODO : error handling
    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDiscountAmount(String discountAmountString) {
        this.discountAmount = Double.parseDouble(discountAmountString);
    }

    public void setRepeat(String repeat) {

        this.repeat = Integer.parseInt(repeat);
    }


    public void addUser(Buyer buyer) {
        users.add(buyer);
    }

    public static CodedDiscount getDiscountById(String discountCode) {

        return null;
    }

    @Override
    public String toString() {
        return "discount code : " + discountCode + "\n"
                + "start time : " + startTime + "\n"
                + "end time : " + endTime + "\n"
                + "discount amount : " + discountAmount + "\n"
                + "remaining time : " + repeat;
    }
}
