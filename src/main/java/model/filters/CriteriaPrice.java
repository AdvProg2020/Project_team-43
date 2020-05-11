package model.filters;

import model.Product;

import java.util.ArrayList;

public class CriteriaPrice implements Criteria {
    private double minPrice;
    private double maxPrice;
    private String name;

    public CriteriaPrice(double minPrice, double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.name = "price from " + minPrice + " to " + maxPrice;
    }

    @Override
    public ArrayList<Product> meetCriteria(ArrayList<Product> products) {
        ArrayList<Product> meetCriteria = new ArrayList<>();
        for (Product product : products) {
            if (minPrice <= product.getPrice() && product.getPrice() <= maxPrice)
                meetCriteria.add(product);
        }
        return meetCriteria;
    }

    @Override
    public String getName() {
        return name;
    }
}
