package model;

import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
//import java.util.Date;

public class Off implements Serializable {
    private static String fileAddress = "database/Off.dat";
    public static int constructId = 0;
    public static ArrayList<Off> acceptedOffs = new ArrayList<>();
    public static ArrayList<Off> inQueueExpectionOffs = new ArrayList<>();
    public static ArrayList<Off> allOffsInQueueEdit = new ArrayList<>();
    public static ArrayList<Off> allOffs = new ArrayList<>();

    private String offId;
    private Seller seller;
    private String sellerUserName;
    private ArrayList<Product> products;
    private ArrayList<String> productsId;
    private State.OffState offState;
    private Date startTime;
    private Date endTime;
    private double discountAmount;

    public Off(Date startTime, Date endTime, double discountAmount, Seller seller, ArrayList<Product> products1) {
        this.offId = "" + constructId;
        this.offState = State.OffState.CREATING_PROCESS;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountAmount = discountAmount;
        this.seller = seller;
        products = new ArrayList<>(products1);
        productsId = new ArrayList<>();
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

    public static Off getAllOffById(String offId) {
        allOffs.clear();
        allOffs.addAll(acceptedOffs);
        allOffs.addAll(inQueueExpectionOffs);
        allOffs.addAll(allOffsInQueueEdit);
        for (Off off : allOffs) {
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

    public static Off getOffProductInOff(Product product) {
        for (Off off : acceptedOffs) {
            if (off.getProducts().contains(product))
                return off;
        }
        return null;
    }

    public boolean hasProduct(Product product) {
        return products.contains(product);
    }

    public void editField(String field, String newField) throws InvalidCommandException, ParseException {
        if (field.equalsIgnoreCase("startTime")) {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(newField);
            if (date.before(endTime)) {
                startTime = date;
            } else {
                throw new InvalidCommandException("startTime must be before endTime");
            }
        } else if (field.equalsIgnoreCase("endTime")) {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(newField);
            if (date.after(endTime)) {
                endTime = date;
            } else {
                throw new InvalidCommandException("endTime must be after startTime");
            }
        } else if (field.equalsIgnoreCase("discountAmount")) {
            try {
                discountAmount = Double.parseDouble(newField);
            } catch (Exception e) {
                throw new InvalidCommandException("discountAmount must be double");
            }
        } else if (field.equalsIgnoreCase("addProduct")) {
            products.add(seller.getProductById(newField));
        } else if (field.equalsIgnoreCase("removeProduct")) {
            products.remove(seller.getProductById(newField));
        } else {
            throw new InvalidCommandException("invalid field");
        }
    }

    @Override
    public String toString() {
        return "Off{" +
                "offId='" + offId + '\'' +
                ", seller=" + seller +
                ", products=" + products +
                ", offState=" + offState +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", discountAmount=" + discountAmount +
                '}';
    }

    private void saveProducts() {
        productsId = new ArrayList<>();
        for (Product product : products) {
            productsId.add(product.getProductId());
        }
        products = null;
    }

    private void loadProducts() {
        products = new ArrayList<>();
        for (String productId : productsId) {
            products.add(Product.getAllProductById(productId));
        }
    }

    private void saveSeller() {
        sellerUserName = seller.getUsername();
        seller = null;
    }

    private void loadSeller() {
        seller = (Seller) (User.getUserByUserName(sellerUserName));
    }

    private static void loadAllProducts() {
        for (Off off : allOffs) {
            off.loadProducts();
        }
    }

    private static void saveAllProducts() {
        allOffs.clear();
        allOffs.addAll(acceptedOffs);
        allOffs.addAll(inQueueExpectionOffs);
        allOffs.addAll(allOffsInQueueEdit);
        for (Off off : allOffs) {
            off.saveProducts();
        }
    }

    private static void loadAllSeller() {
        for (Off off : allOffs) {
            off.loadSeller();
        }
    }

    private static void saveAllSeller() {
        allOffs.clear();
        allOffs.addAll(acceptedOffs);
        allOffs.addAll(inQueueExpectionOffs);
        allOffs.addAll(allOffsInQueueEdit);
        for (Off off : allOffs) {
            off.saveSeller();
        }
    }

    public static void saveFields() {
        saveAllProducts();
        saveAllSeller();
    }

    public static void loadFields() {
        loadAllProducts();
        loadAllSeller();
    }

    public static void load() throws FileNotFoundException {
        Off[] offs = (Off[]) Loader.load(Off[].class, fileAddress);
        if (offs != null) {
            allOffs = new ArrayList<>(Arrays.asList(offs));
            loadOffs();
        }
    }

    public static void loadOffs() {
        for (Off off : allOffs) {
            if (off.offState.equals(State.OffState.CONFIRMED)) {
                acceptedOffs.add(off);
            } else if (off.offState.equals(State.OffState.EDITING_PROCESS)) {
                allOffsInQueueEdit.add(off);
            } else if (off.offState.equals(State.OffState.CREATING_PROCESS)) {
                inQueueExpectionOffs.add(off);
            }
            int id = Integer.parseInt(off.getOffId());
            if (constructId <= id) {
                constructId = id + 1;
            }
        }
    }

    public static void save() throws IOException {
        allOffs.clear();
        allOffs.addAll(acceptedOffs);
        allOffs.addAll(inQueueExpectionOffs);
        allOffs.addAll(allOffsInQueueEdit);
        Saver.save(allOffs, fileAddress);
    }

    public static ArrayList<Off> getAllOffs() {
        return allOffs;
    }

    public static ArrayList<Off> getAcceptedOffs() {
        return acceptedOffs;
    }

    public static ArrayList<Off> getInQueueExpectionOffs() {
        return inQueueExpectionOffs;
    }

    public static ArrayList<Off> getAllOffsInQueueEdit() {
        return allOffsInQueueEdit;
    }
}