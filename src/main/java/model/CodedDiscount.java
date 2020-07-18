package model;

import model.database.Loader;
import model.database.Saver;

import java.io.Serializable;
import java.text.ParseException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

public class CodedDiscount implements Serializable {
    private static String fileAddress = "database/CodedDiscount.dat";

    public static int constructId;
    public static ArrayList<CodedDiscount> allCodedDiscount = new ArrayList<CodedDiscount>();
    private String discountCode;
    private Date startTime;
    private Date endTime;
    private double discountAmount;
    private int repeat;

    public static ArrayList<CodedDiscount> getAllCodedDiscount() {
        return allCodedDiscount;
    }

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

    public boolean checkTime() {
        Date now = new Date();
        return now.compareTo(startTime) > 0 && endTime.compareTo(now) > 0;

    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public static void remove(CodedDiscount codedDiscount) {
        allCodedDiscount.remove(codedDiscount);
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


    public static void load() throws FileNotFoundException {
        CodedDiscount[] codedDiscounts = (CodedDiscount[]) Loader.load(CodedDiscount[].class, fileAddress);
        if (codedDiscounts != null) {
            allCodedDiscount = new ArrayList<>(Arrays.asList(codedDiscounts));
            if (allCodedDiscount.size() != 0)
                constructId = Integer.parseInt(allCodedDiscount.get(allCodedDiscount.size() - 1).getDiscountCode()) + 1;
            else
                constructId = 0;
        }
    }


    public static void save() throws IOException {
        Saver.save(allCodedDiscount, fileAddress);
    }

    public static void setAllCodedDiscount(ArrayList<CodedDiscount> allCodedDiscount) {
        CodedDiscount.allCodedDiscount = allCodedDiscount;
    }
}