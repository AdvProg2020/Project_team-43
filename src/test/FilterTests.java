package test;

import model.Category;
import model.Company;
import model.Product;
import model.Seller;
import model.filters.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

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

    @BeforeAll
    public void setAll() {
        filterManager = new FilterManager();
        products = new ArrayList<>();
        expectationFilter = new ArrayList<>();
        company = new Company("asus", "non");
        category1 = new Category("laptop", null);
        category2 = new Category("tablet", null);
        category3 = new Category("mobile", null);
        seller = new Seller("seller", null, "asus");
        product1 = new Product("gt570", company, 1, category1, seller);
        product2 = new Product("tb1010", company, 10, category2, seller);
        product3 = new Product("nokia1100", company, 25, category3, seller);
        product4 = new Product("vivo book", company, 13, category1, seller);
        product5 = new Product("ipod9", company, 50, category2, seller);
        product6 = new Product("f", company, 65, category3, seller);
        product7 = new Product("zen book", company, 17, category1, seller);
        product8 = new Product("ti 9", company, 8, category2, seller);
        product9 = new Product("iphoneX", company, 100, category3, seller);
        product10 = new Product(" mac book pro", company, 99, category1, seller);
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
    }

    @Test
    public void availableFilterTest() {
        setAll();
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
        CriteriaAvailable criteriaAvailable = new CriteriaAvailable();
        ArrayList<Product> afterFilter = criteriaAvailable.meetCriteria(products);
        Object[] afterFilterArray = afterFilter.toArray();
        Object[] expectationArray = expectationFilter.toArray();
        Assert.assertArrayEquals(expectationArray, afterFilterArray);
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
    public void categoryFeatureFilterTest() {
        setAll();
        //TODO : dastan dare in baadan mizanam
    }

    @Test
    public void nameFilterTest() {
        setAll();
        CriteriaName criteriaName = new CriteriaName("book");
        expectationFilter.add(product4);
        expectationFilter.add(product7);
        expectationFilter.add(product10);
        ArrayList<Product> afterFilter = criteriaName.meetCriteria(products);
        Object[] afterFilterArray = afterFilter.toArray();
        Object[] expectationArray = expectationFilter.toArray();
        Assert.assertArrayEquals(expectationArray, afterFilterArray);
    }

    @Test
    public void priceFilterTest(){
        setAll();
        CriteriaPrice criteriaPrice = new CriteriaPrice(50, 100);
        expectationFilter.add(product5);
        expectationFilter.add(product6);
        expectationFilter.add(product9);
        expectationFilter.add(product10);
        ArrayList<Product> afterFilter = criteriaPrice.meetCriteria(products);
        Object[] afterFilterArray = afterFilter.toArray();
        Object[] expectationArray = expectationFilter.toArray();
        Assert.assertArrayEquals(expectationArray, afterFilterArray);
    }

}
