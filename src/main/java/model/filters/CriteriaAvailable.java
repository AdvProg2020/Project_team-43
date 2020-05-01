package model.filters;

import model.Product;

import java.util.ArrayList;

public class CriteriaAvailable implements Criteria {
    @Override
    public ArrayList<Product> meetCriteria(ArrayList<Product> products) {
        ArrayList<Product> meetCriteria = new ArrayList<>();
        for (Product product : products) {
            if (product.isAvailable())
                meetCriteria.add(product);
        }
        return meetCriteria;
    }
}
