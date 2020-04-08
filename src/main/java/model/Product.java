package model;

import java.util.ArrayList;

public class Product {
    public static ArrayList<Product> allProductsInList = new ArrayList<Product>();
    public static ArrayList<Product> allProductsInQueueExpect = new ArrayList<Product>();
    private String productId;
    private State.ProductState productState;
    private String name;
    private String companyName;
    private double price;
    private boolean isAvailable;
    private ArrayList<Double> score;
    private ArrayList<Opinion> opinions;
    public ArrayList<Buyer> buyers;
    private ArrayList<Seller> sellers;

    public Product(String productId, String name, String companyName, double price) {
        this.productId = productId;
        this.productState = State.ProductState.creatingProcess;
        this.name = name;
        this.companyName = companyName;
        this.price = price;
        buyers = new ArrayList<Buyer>();
        sellers = new ArrayList<Seller>();
        score = new ArrayList<Double>();
        opinions = new ArrayList<Opinion>();
        /////first isAvailable or not??
        allProductsInQueueExpect.add(this);
    }

    public static Product getProductById(String productId) {

        return null;
    }

}
