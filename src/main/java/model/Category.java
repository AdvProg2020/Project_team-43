package model;

import java.util.ArrayList;

public class Category {
    public String getName() {
        return name;
    }
    private static ArrayList<Category> allCategories = new ArrayList<Category>();

    public ArrayList<Product> getProducts() {
        return products;
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    private String name;
    private ArrayList<Product> products;

    //TODO: else


    public static Category getCategoryByName(String categoryName){
        return null;
    }
}
