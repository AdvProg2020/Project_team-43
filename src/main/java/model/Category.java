package model;

import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Category implements Serializable {
    private static String fileAddress = "database/Category.dat";
    private static ArrayList<Category> allCategories = new ArrayList<Category>();


    private String name;
    private ArrayList<String> features;
    private transient ArrayList<Product> products;
    private ArrayList<String> productsId;


    public Category(String name, ArrayList<String> features) {
        this.name = name;
        products = new ArrayList<>();
        this.features = new ArrayList<>(features);
        productsId = new ArrayList<>();
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
        features.add(newFeature);
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

    public void changeFeatureName(String oldName, String newName) {
        for (Product product : this.products) {
            String featureValue = product.getFeaturesMap().get(oldName);
            product.getFeaturesMap().remove(oldName);
            product.getFeaturesMap().put(newName, featureValue);
        }
        this.features.remove(oldName);
        this.addFeatures(newName);
    }

    public void removeFeature(String featureName) {
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

    public static boolean hasCategoryWithName(String categoryName) {
        for (Category category : allCategories) {
            if (category.name.equals(categoryName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", features=" + features +
                ", products=" + stringProducts() +
                '}';
    }

    public String stringProducts() {
        String res = "[";
        if (products.size() > 0) {
            res = res + products.get(0).getName();
            for (int i = 1; i < products.size(); i++) {
                res = res + ", " + products.get(i).getName();
            }
        }
        res = res.concat("]");
        return res;
    }

    public static void load() throws FileNotFoundException {
        Category[] categories = (Category[]) Loader.load(Category[].class, fileAddress);
        if (categories != null) {
            allCategories = new ArrayList<>(Arrays.asList(categories));
        }
    }


    public static void save() throws IOException {
        Saver.save(allCategories, fileAddress);
    }

    private void productsLoad() {
        products = new ArrayList<>();
        for (String id : productsId) {
            products.add(Product.getAllProductById(id));
        }
    }

    private void productsSave() {
        productsId.clear();
        for (Product product : products) {
            productsId.add(product.getProductId());
        }
    }

    private static void loadAllProducts() {
        for (Category category : allCategories) {
            category.productsLoad();
        }
    }

    private static void saveAllProducts() {
        for (Category category : allCategories) {
            category.productsSave();
        }
    }

    public static void loadFields() {
        loadAllProducts();
    }

    public static void saveFields() {
        saveAllProducts();
    }


}
