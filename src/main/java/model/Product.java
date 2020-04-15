package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class Product {
    public static ArrayList<Product> allProductsInList = new ArrayList<Product>();
    public static ArrayList<Product> allProductsInQueueExpect = new ArrayList<Product>();

    private String productId;
    private State.ProductState productState;
    private String name;
    private Company company;
    private double price;
    private int availableCount;
    private Category category;
    private Map<String, String >featuresMap;
    private Seller seller;
    private String description;/////////tozihat
    private ProductScore score;
    private ArrayList<Opinion> opinions;


    public Product(String productId, String name, Company company, double price, Category category, Seller seller, ArrayList<String> features) {
        this.productId = productId;
        this.productState = State.ProductState.creatingProcess;
        this.name = name;
        this.company = company;
        this.price = price;
        this.category = category;
        fillFeaturesMap(features);
        this.seller = seller;
        score = new ProductScore();
        opinions = new ArrayList<Opinion>();
        allProductsInQueueExpect.add(this);
    }


    public boolean isAvailable() {
        return availableCount>0;
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

    private void fillFeaturesMap(ArrayList<String> features2){
        ArrayList<String>features1 = this.category.getFeatures();
        for(int i=0;i<features1.size();i++){
            featuresMap.put(features1.get(i), features2.get(i));
        }
    }

    public String getName() {
        return name;
    }

    public String getProductId() {
        return productId;
    }

}
