package model;

import java.util.ArrayList;
//import java.util.Date;

public class Off {
    public static int constructId = 0;
    public static ArrayList<Off> acceptedOffs = new ArrayList<>();
    public static ArrayList<Off> inQueueExpectionOffs = new ArrayList<>();
    private String offId;
    private Seller seller;
    private ArrayList<Product> products;
    private State.OffState offState;
    private String startTime;
    private String endTime;
    //private Date startDate;
    //private Date endDate;
    private double discountAmount;

    public Off(String startTime, String endTime, double discountAmount, Seller seller, ArrayList<Product> products1) {
        this.offId = "" + constructId;
        this.offState = State.OffState.CREATING_PROCESS;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountAmount = discountAmount;
        this.seller = seller;
        products = new ArrayList<>(products1);
        inQueueExpectionOffs.add(this);
        constructId += 1;
    }

    public String getOffId() {
        return offId;
    }

    public String getSellerName() {
        return seller.getUsername();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public State.OffState getOffState() {
        return offState;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public Seller getSeller() {
        return seller;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void setOffState(State.OffState offState) {
        this.offState = offState;
    }

    public static Off getOffById(String offId) {
        for (Off off : acceptedOffs) {
            if (off.getOffId().equals(offId)) {
                return off;
            }
        }
        return null;
    }

    public static double isProductInOff(Product product) {
        for (Off off : acceptedOffs) {
            if (off.getProducts().contains(product))
                return off.getDiscountAmount();
        }
        return 0;
    }

    public boolean hasProduct(Product product) {
        return products.contains(product);
    }
}
