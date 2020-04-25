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

    public int getRepeat() {
        return repeat;
    }

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
        buyer.addDiscountCode(this);
    }
    public static boolean isCodedDiscountWithThisCode(String discountCode){
        for (CodedDiscount discount : allCodedDiscount) {
            if(discount.getDiscountCode().equals(discountCode))
                return true;
        }
        return false;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public static CodedDiscount getDiscountById(String discountCode){
        return null;
    }
    public boolean hasUser(Buyer buyer){
        return this.users.contains(buyer);
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public boolean checkTime(){
        //ToDo: check if code is valid in that time
        return true;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = Double.parseDouble(discountAmount);
    }

    public void setRepeat(String repeat) {
        this.repeat = Integer.parseInt(repeat);
    }
}
