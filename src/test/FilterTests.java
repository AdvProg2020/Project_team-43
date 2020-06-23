
import Controller.Processor;
import model.Category;
import model.Company;
import model.Product;
import model.Seller;
import model.filters.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.HashMap;

public class FilterTests {

    FilterManager filterManager;
    ArrayList<Product> products;
    ArrayList<Product> expectationFilter;
    Company company;
    Category category1;
    Category category2;
    Category category3;
    Seller seller;
    Product product1;
    Product product2;
    Product product3;
    Product product4;
    Product product5;
    Product product6;
    Product product7;
    Product product8;
    Product product9;
    Product product10;
    Processor processor;

    @BeforeAll
    public void setAll() {
        processor = new Processor();
        products = new ArrayList<>();
        expectationFilter = new ArrayList<>();
        company = new Company("asus", "non");
        category1 = new Category("laptop", new ArrayList<String>());
        category2 = new Category("tablet", new ArrayList<String>());
        category3 = new Category("mobile", new ArrayList<String>());
        seller = new Seller("seller", null, "asus");
        product1 = new Product("gt570", company, 1, category1);
        product2 = new Product("tb1010", company, 10, category2);
        product3 = new Product("nokia1100", company, 25, category3);
        product4 = new Product("vivo book", company, 13, category1);
        product5 = new Product("ipod9", company, 50, category2);
        product6 = new Product("f", company, 65, category3);
        product7 = new Product("zen book", company, 17, category1);
        product8 = new Product("ti 9", company, 8, category2);
        product9 = new Product("iphoneX", company, 100, category3);
        product10 = new Product(" mac book pro", company, 99, category1);
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);
        products.add(product8);
        products.add(product9);
        products.add(product10);
        processor.newProductFilter();
        filterManager = processor.getProductFilter();
    }

    @Test
    public void availableFilterTest() {
        setAll();
        processor.filteringProcess("filter by availability");
        product1.setAvailableCount(2);
        product2.setAvailableCount(0);
        product3.setAvailableCount(5);
        product4.setAvailableCount(0);
        product5.setAvailableCount(9);
        product6.setAvailableCount(10);
        product7.setAvailableCount(0);
        product8.setAvailableCount(0);
        product9.setAvailableCount(2);
        product10.setAvailableCount(1);
        expectationFilter.add(product1);
        expectationFilter.add(product3);
        expectationFilter.add(product5);
        expectationFilter.add(product6);
        expectationFilter.add(product9);
        expectationFilter.add(product10);
        ArrayList<Product> afterFilter = filterManager.getCurrentFilters().get(filterManager.getCurrentFilters().size()-1).meetCriteria(products);
        Object[] afterFilterArray = afterFilter.toArray();
        Object[] expectationArray = expectationFilter.toArray();
        Assert.assertArrayEquals(expectationArray, afterFilterArray);
    }

    @Test
    public void getAvailableNameTest(){
        setAll();
        processor.filteringProcess("filter by availability");
        Assert.assertEquals(filterManager.getCurrentFilters().get(filterManager.getCurrentFilters().size()-1).getName(), "available");

    }

    @Test
    public void categoryFilterTest() {
        setAll();
        CriteriaCategory criteriaCategory = new CriteriaCategory(category1);
        expectationFilter.add(product1);
        expectationFilter.add(product4);
        expectationFilter.add(product7);
        expectationFilter.add(product10);
        ArrayList<Product> afterFilter = criteriaCategory.meetCriteria(products);
        Object[] afterFilterArray = afterFilter.toArray();
        Object[] expectationArray = expectationFilter.toArray();
        Assert.assertArrayEquals(expectationArray, afterFilterArray);
    }

    @Test
    public void categoryFilterGetNameAndSetCategoryTest(){
        setAll();
        CriteriaCategory criteriaCategory = new CriteriaCategory(category2);
        filterManager.setCategory(category2);
        criteriaCategory.setCategory(category2);
        Assert.assertEquals(criteriaCategory.getName(), "category : " + filterManager.getCategory().getName());

    }
    @Test
    public void categoryFeatureFilterTest() {
        setAll();
        category1.addFeatures("color");
        product1.getFeaturesMap().replace("color", "red");
        product4.getFeaturesMap().replace("color", "blue");
        product7.getFeaturesMap().replace("color", "red");
        product10.getFeaturesMap().replace("color", "yellow");
        filterManager.setCategory(category1);
        CriteriaCategoryFeatures criteriaCategoryFeatures = filterManager.getCriteriaCategoryFeatures();
        criteriaCategoryFeatures.getFeatures().put("new junk feature", new ArrayList<>());
        filterManager.disableFeature("new junk feature", "junk value");
        criteriaCategoryFeatures.addFeature("color", "red");
        expectationFilter.add(product1);
        expectationFilter.add(product7);
        ArrayList<Product> afterFilter = criteriaCategoryFeatures.meetCriteria(products);
        Object[] afterFilterArray = afterFilter.toArray();
        Object[] expectationArray = expectationFilter.toArray();
        Assert.assertArrayEquals(expectationArray, afterFilterArray);
    }

    @Test
    public void addFeatureFromFilterManagerTest(){
        setAll();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("color", "black");
        filterManager.setCategory(category1);
        filterManager.addFeaturesToCategoryFeaturesFilter(hashMap);
        Assert.assertTrue(filterManager.getCriteriaCategoryFeatures().getFeatures().containsKey("color"));
    }

    @Test
    public void nameFilterTest() {
        setAll();
        processor.filteringProcess("filter by name book");
        expectationFilter.add(product4);
        expectationFilter.add(product7);
        expectationFilter.add(product10);
        ArrayList<Product> afterFilter = filterManager.getCurrentFilters().get(filterManager.getCurrentFilters().size()-1).meetCriteria(products);
        Object[] afterFilterArray = afterFilter.toArray();
        Object[] expectationArray = expectationFilter.toArray();
        Assert.assertArrayEquals(expectationArray, afterFilterArray);
    }
    @Test
    public void nameFilterToStringTest(){
        setAll();
        processor.filteringProcess("filter by name newName");
        Assert.assertEquals(filterManager.getCurrentFilters().get(filterManager.getCurrentFilters().size()-1).getName(), "name filter : newName");
    }

    @Test
    public void priceFilterTest(){
        setAll();
        processor.filteringProcess("filter by price from 50 to 100");
        expectationFilter.add(product5);
        expectationFilter.add(product6);
        expectationFilter.add(product9);
        expectationFilter.add(product10);
        ArrayList<Product> afterFilter = filterManager.getCurrentFilters().get(filterManager.getCurrentFilters().size()-1).meetCriteria(products);
        Object[] afterFilterArray = afterFilter.toArray();
        Object[] expectationArray = expectationFilter.toArray();
        Assert.assertArrayEquals(expectationArray, afterFilterArray);
    }

    @Test
    public void getPriceFilterNameTest(){
        setAll();
        processor.filteringProcess("filter by price from 50 to 100");
        Assert.assertEquals(filterManager.getCurrentFilters().get(filterManager.getCurrentFilters().size()-1).getName(), "price from " + "50.0" + " to " + "100.0");


    }

}
