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
    private boolean available;
    private Category category;
    private String description;/////////tozihat
    private ProductScore score;
    private ArrayList<Opinion> opinions;


    public Product(String productId, String name, String companyName, double price, Category category) {
        this.productId = productId;
        this.productState = State.ProductState.creatingProcess;
        this.name = name;
        this.companyName = companyName;
        this.price = price;
        this.category = category;
        score = new ProductScore();
        opinions = new ArrayList<Opinion>();
        allProductsInQueueExpect.add(this);
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductScore getScore() {
        return score;
    }

    public static Product getProductById(String productId) {

        return null;
    }

    public String getName() {
        return name;
    }

    public String getProductId() {
        return productId;
    }
}
