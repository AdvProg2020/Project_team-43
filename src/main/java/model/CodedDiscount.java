package model;

import com.sun.org.apache.bcel.internal.classfile.Code;

import java.util.Date;
import java.util.ArrayList;

public class CodedDiscount {
    public static ArrayList<CodedDiscount> allCodedDiscount = new ArrayList<CodedDiscount>();
    private String discountCode;
    private Date startTime;
    private Date endTime;
    private double discount;
    private int repeat;
    private ArrayList<Buyer> users;

    public CodedDiscount(String discountCode, Date startTime, Date endTime, double discount, int repeat) {
        this.discountCode = discountCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discount = discount;
        this.repeat = repeat;
        users = new ArrayList<Buyer>();
    }

    public void addUser(Buyer buyer) {
        users.add(buyer);
    }

    public static CodedDiscount getDiscountById(String discountCode){

        return null;
    }
}
