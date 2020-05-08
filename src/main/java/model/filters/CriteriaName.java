package model.filters;

import model.Product;

import java.util.ArrayList;

public class CriteriaName implements Criteria {
    private String name;

    public CriteriaName(String name) {
        this.name = name;
    }

    @Override
    public ArrayList<Product> meetCriteria(ArrayList<Product> products) {
        ArrayList<Product> meetCriteria = new ArrayList<Product>();
        for (Product product : products) {
            if (product.getName().contains(this.name))
                meetCriteria.add(product);
        }
        return meetCriteria;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
