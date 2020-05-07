package model.filters;

import model.Category;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class CriteriaCategoryFeatures extends CriteriaCategory {
    private HashMap<String, String> features;

    public CriteriaCategoryFeatures(Category category) {
        super(category);
        this.features = new HashMap<String, String>();
    }


    public HashMap<String, String> getFeatures() {
        return features;
    }

    public void addFeature(String feature, String value) {
        this.features.put(feature, value);
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

    public void deleteFeature(String feature) {
        this.features.remove(feature);
    }
}
