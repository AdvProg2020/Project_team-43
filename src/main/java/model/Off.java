package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//import java.util.Date;

public class Off {
    public static int constructId = 0;
    public static ArrayList<Off> acceptedOffs = new ArrayList<>();
    public static ArrayList<Off> inQueueExpectionOffs = new ArrayList<>();
    private String offId;
    private Seller seller;
    private ArrayList<Product> products;
    private State.OffState offState;
    private Date startTime;
    private Date endTime;
    private double discountAmount;

    public Off(Date startTime, Date endTime, double discountAmount, Seller seller, ArrayList<Product> products1){
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

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
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

    public void editField(String field, String newField) throws InvalidCommandException, ParseException {
        if (field.equalsIgnoreCase("startTime") && newField.matches("\\d\\d/\\d\\d/\\d\\d\\d\\d")) {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(newField);
            if(date.before(endTime)){
                startTime = date;
            } else{
                throw new InvalidCommandException("startTime must be before endTime");
            }
        } else if (field.equalsIgnoreCase("endTime")) {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(newField);
            if(date.after(endTime)){
                endTime = date;
            } else{
                throw new InvalidCommandException("endTime must be after startTime");
            }
        } else if (field.equalsIgnoreCase("discountAmount")) {
            try {
                discountAmount = Double.parseDouble(newField);
            } catch (Exception e) {
                throw new InvalidCommandException("discountAmount must be double");
            }
        } else {
            throw new InvalidCommandException("invalid field");
        }
    }

    @Override
    public String toString() {
        return "Off{" +
                "offId='" + offId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", discountAmount=" + discountAmount +
                '}';
    }
}
