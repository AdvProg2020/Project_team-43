package model.filters;

import model.Category;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class CriteriaCategoryFeatures extends CriteriaCategory {
    private HashMap<String, String> features;

    public CriteriaCategoryFeatures(Category category, HashMap<String, String> features) {
        super(category);
        this.features = features;
    }

    @Override
    public ArrayList<Product> meetCriteria(ArrayList<Product> products) {
        ArrayList<Product> meetCriteria = super.meetCriteria(products);
        ArrayList<Product> finalMeetCriteria = new ArrayList<Product>();
        for (Product product : meetCriteria) {
            if (hasFeature(product))
                finalMeetCriteria.add(product);
        }
        return finalMeetCriteria;
    }

    public boolean hasFeature(Product product) {
        for (String feature : features.keySet()) {
            if (!product.getFeaturesMap().get(feature).equals(features.get(feature))) {
                return false;
            }
        }
        return true;
    }
}
