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

    public void addUser(Buyer buyer) {
        users.add(buyer);
    }

    public static CodedDiscount getDiscountById(String discountCode){

        return null;
    }
}
