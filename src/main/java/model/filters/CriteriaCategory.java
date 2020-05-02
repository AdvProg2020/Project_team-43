package model.filters;

import model.Category;
import model.Product;

import java.util.ArrayList;

public class CriteriaCategory implements Criteria {
    private Category category;
    private String name;
    public CriteriaCategory(Category category) {
        this.name="category : "+category.getName();
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

    @Override
    public String getName() {
        return name;
    }
}
