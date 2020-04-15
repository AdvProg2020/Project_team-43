package model;

import java.util.ArrayList;

public class Category {

    private static ArrayList<Category> allCategories = new ArrayList<Category>();


    private String name;
    private ArrayList<String> features;
    private Category categorySuper;
    private ArrayList<Category> subcategories;
    private ArrayList<Product> products;

    public Category(String name, Category categorySuper, ArrayList<String> features) {
        this.name = name;
        this.categorySuper = categorySuper;
        products = new ArrayList<Product>();
        subcategories = new ArrayList<Category>();
        allCategories.add(this);
    }

    public ArrayList<String> getFeatures() {
        return features;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean hasProduct(Product product) {
        return products.contains(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void addSubCategory(Category category) {
        subcategories.add(category);
    }

    public boolean hasSubCategory(Category category) {
        return subcategories.contains(category);
    }

    public void removeSubCategory(Category category) {
        subcategories.remove(category);
    }

    //TODO: else
    public String getName() {
        return name;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public static Category getCategoryByName(String categoryName) {
        return null;
    }
}
