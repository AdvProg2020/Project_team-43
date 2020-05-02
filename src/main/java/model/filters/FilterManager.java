package model.filters;

import model.Category;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class FilterManager {
    private static FilterManager instance = new FilterManager();
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
        return products;
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

    public void addCategoryFeaturesFilter(Category category, HashMap<String, String> features) {
        currentFilters.add(new CriteriaCategoryFeatures(category, features));
    }
}
