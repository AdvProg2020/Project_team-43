package model.filters;

import model.Category;
import model.Product;

import java.util.ArrayList;

public class CriteriaCategory implements Criteria {
    private Category category;

    public CriteriaCategory(Category category) {
        this.category = category;
    }

    @Override
    public ArrayList<Product> meetCriteria(ArrayList<Product> products) {
        ArrayList<Product> meetCriteria = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory()==category)
                meetCriteria.add(product);
        }
        return meetCriteria;
    }
}
