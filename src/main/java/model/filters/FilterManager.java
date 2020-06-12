package model.filters;

import model.Category;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class FilterManager {
    private CriteriaCategoryFeatures criteriaCategoryFeatures;
    private Category category;
    private ArrayList<Criteria> currentFilters;

    public FilterManager() {
        currentFilters = new ArrayList<Criteria>();
    }

    public ArrayList<Product> getProductsAfterFilter(ArrayList<Product> products) {
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
        disablePriceFilter();
        currentFilters.add(new CriteriaPrice(minPrice, maxPrice));
    }

    public void addAvailabilityFilter() {
        disableAvailabilityFilter();
        currentFilters.add(new CriteriaAvailable());
    }

    public void addNameFilter(String name) {
        disableNameFilter();
        currentFilters.add(new CriteriaName(name));
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        this.criteriaCategoryFeatures = new CriteriaCategoryFeatures(this.category);
    }

    public CriteriaCategoryFeatures getCriteriaCategoryFeatures() {
        return criteriaCategoryFeatures;
    }


    public void addFeaturesToCategoryFeaturesFilter(HashMap<String, String> newFeatures) {
        for (String feature : newFeatures.keySet()) {
            criteriaCategoryFeatures.addFeature(feature, newFeatures.get(feature));
        }
    }

    public void addOffFilter() {
        this.currentFilters.add(new CriteriaOff());
    }

    public void disableOffFilter() {
        currentFilters.removeIf(t -> t instanceof CriteriaOff);
    }

    public void disablePriceFilter() {
        currentFilters.removeIf(t -> t instanceof CriteriaPrice);
    }

    public void disableAvailabilityFilter() {
        currentFilters.removeIf(t -> t instanceof CriteriaAvailable);
    }

    public void disableFeature(String feature) {
        criteriaCategoryFeatures.deleteFeature(feature);
    }

    public void disableNameFilter() {
        currentFilters.removeIf(t -> t instanceof CriteriaName);
    }
}
