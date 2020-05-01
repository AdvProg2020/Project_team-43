package model.filters;

import model.Product;

import java.util.ArrayList;

public interface Criteria {
    ArrayList<Product> meetCriteria(ArrayList<Product> products);
}
