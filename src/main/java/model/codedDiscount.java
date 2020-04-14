package model;

import java.util.Date;
import java.util.ArrayList;

public class codedDiscount {
    private String discountCode;
    private Date startTime;
    private Date endTime;
    private double discount;
    private int repeat;
    private ArrayList<Buyer> users;

    public codedDiscount(String discountCode, Date startTime, Date endTime, double discount, int repeat) {
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
}
