package model;

import java.util.ArrayList;

public class Category {

    private static ArrayList<Category> allCategories = new ArrayList<Category>();


    private String name;
    private ArrayList<String> features;
    private ArrayList<Product> products;

    public Category(String name, ArrayList<String> features) {
        this.name = name;
        products = new ArrayList<Product>();
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

    public void setName(String name) {
        this.name = name;
    }

    public void addFeatures(String newFeature) {
        this.features.add(newFeature);
        for (Product product : this.products) {
            product.getFeaturesMap().put(newFeature, "");
        }
    }

    public boolean hasFeature(String feature) {
        for (String existsFeature : this.features) {
            if (existsFeature.equalsIgnoreCase(feature)) {
                return true;
            }
        }
        return false;
    }

    public void changeFeatureName(String oldName, String newName){
        for (Product product : this.products) {
            String featureValue = product.getFeaturesMap().get(oldName);
            product.getFeaturesMap().remove(oldName);
            product.getFeaturesMap().put(newName, featureValue);
        }
    }

    public void removeFeature(String featureName){
        this.getFeatures().remove(featureName);
        for (Product product : this.products) {
            product.getFeaturesMap().remove(featureName);
        }
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
        for (Category category : allCategories) {
            if (category.name.equals(categoryName)) {
                return category;
            }
        }
        return null;
    }
}
