package model.filters;

import model.Category;
import model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CriteriaCategoryFeatures extends CriteriaCategory {
    private HashMap<String, ArrayList<String>> features;

    public CriteriaCategoryFeatures(Category category) {
        super(category);
        this.features = new HashMap<String, ArrayList<String>>();
    }

    public HashMap<String, ArrayList<String>> getFeatures() {
        return features;
    }

    public void addFeature(String feature, String value) {
        if (!this.features.containsKey(feature)) {
            this.features.put(feature, new ArrayList<String>());
        }
        this.features.get(feature).add(value);
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
            if (!features.get(feature).contains(product.getFeaturesMap().get(feature))) {
                return false;
            }
        }
        return true;
    }

    public void deleteFeature(String feature, String value) {
        this.features.get(feature).remove(value);
    }
}
