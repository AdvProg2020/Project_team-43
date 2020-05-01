package model.filters;

import model.Product;

import java.util.ArrayList;

public class OrCriteria implements Criteria {
    private Criteria criteria;
    private Criteria otherCriteria;

    public OrCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public ArrayList<Product> meetCriteria(ArrayList<Product> products) {
        ArrayList<Product> firstCriteriaProducts = criteria.meetCriteria(products);
        ArrayList<Product> otherCriteriaProducts = otherCriteria.meetCriteria(products);
        for (Product otherCriteriaProduct : otherCriteriaProducts) {
            if (!firstCriteriaProducts.contains(otherCriteriaProduct))
                firstCriteriaProducts.add(otherCriteriaProduct);
        }
        return firstCriteriaProducts;
    }
}
