package model.filters;

import model.Product;

import java.util.ArrayList;

public class AndCriteria implements Criteria {
    private Criteria criteria;
    private Criteria otherCriteria;
    private String name;
    public AndCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<Product> meetCriteria(ArrayList<Product> products) {
        ArrayList<Product> firstCriteriaProduct=criteria.meetCriteria(products);
        return otherCriteria.meetCriteria(firstCriteriaProduct);
    }
}
