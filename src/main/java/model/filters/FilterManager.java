package model.filters;

import model.Category;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class FilterManager {
    private static FilterManager instance = new FilterManager();
    private CriteriaCategoryFeatures criteriaCategoryFeatures;
    private Category category;
    private ArrayList<Criteria> currentFilters;

    private FilterManager() {
        currentFilters = new ArrayList<Criteria>();
    }

    public static FilterManager getInstance() {
        return instance;
    }

    public ArrayList<Product> getProductsAfterFilter() {
        ArrayList<Product> products = Product.getAllProductsInList();
        for (Criteria filter : currentFilters) {
            products = filter.meetCriteria(products);
        }
        if (criteriaCategoryFeatures == null) {
            return products;
        } else {
            return criteriaCategoryFeatures.meetCriteria(products);
        }
    }

    public ArrayList<Criteria> getCurrentFilters() {
        return currentFilters;
    }

    public void addPriceFilter(double minPrice, double maxPrice) {
        currentFilters.add(new CriteriaPrice(minPrice, maxPrice));
    }

    public void addAvailabilityPrice() {
        currentFilters.add(new CriteriaAvailable());
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CriteriaCategoryFeatures getCriteriaCategoryFeatures() {
        return criteriaCategoryFeatures;
    }

    public void addCategoryFeaturesFilter(HashMap<String, String> features) {
        criteriaCategoryFeatures = new CriteriaCategoryFeatures(this.category, features);
    }

    public void addFeaturesToCategoryFeaturesFilter(HashMap<String, String> newFeatures) {
        for (String feature : newFeatures.keySet()) {
            criteriaCategoryFeatures.addFeature(feature, newFeatures.get(feature));
        }
    }
    public void disablePriceFilter(){
        currentFilters.removeIf(t -> t instanceof CriteriaPrice);
    }
    public void disableAvailabilityFilter(){
        currentFilters.removeIf(t -> t instanceof CriteriaAvailable);
    }
}
