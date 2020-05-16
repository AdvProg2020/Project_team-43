package model;

import java.util.Date;
import java.util.ArrayList;

public class CodedDiscount {
    public static int constructId = 0;
    public static ArrayList<CodedDiscount> allCodedDiscount = new ArrayList<CodedDiscount>();
    private String discountCode;
    private Date startTime;
    private Date endTime;
    private double discountAmount;
    private int repeat;

    public int getRepeat() {
        return repeat;
    }

    public CodedDiscount(Date startTime, Date endTime, double discount, int repeat) {
        this.discountCode = "" + constructId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountAmount = discount;
        this.repeat = repeat;
        allCodedDiscount.add(this);
        constructId += 1;
    }

    public static boolean isCodedDiscountWithThisCode(String discountCode) {
        for (CodedDiscount discount : allCodedDiscount) {
            if (discount.getDiscountCode().equals(discountCode))
                return true;
        }
        return false;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public static CodedDiscount getDiscountById(String discountCode) {
        for (CodedDiscount codedDiscount : allCodedDiscount) {
            if (codedDiscount.discountCode.equalsIgnoreCase(discountCode)) {
                return codedDiscount;
            }
        }
        return null;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public boolean checkTime() {
        //ToDo: check if code is valid in that time
        return true;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = Double.parseDouble(discountAmount);
    }

    public void setRepeat(String repeat) {
        this.repeat = Integer.parseInt(repeat);
    }

    @Override
    public String toString() {
        return "{discount code : " + discountCode + "}"
                + "{discount amount : " + discountAmount + "}"
                + "{repeat : " + repeat + "}"
                + "{start time : " + startTime + "}"
                + "{end time : " + endTime + "}";
    }
}