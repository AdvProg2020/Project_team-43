package test;

import model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class SortTests {
    ArrayList<Product> products;
    ArrayList<Product> expectationSort;
    Company company;
    Category category;
    Seller seller;
    Product product1;
    Product product2;
    Product product3;
    Product product4;
    Product product5;

    @BeforeAll
    public void setAll() {
        products = new ArrayList<>();
        expectationSort = new ArrayList<>();
        company = new Company("asus", "non");
        category = new Category("laptop", new ArrayList<>());
        seller = new Seller("seller", null, "asus");
        product1 = new Product("a", company, 1, category);
        product2 = new Product("b", company, 2, category);
        product3 = new Product("c", company, 3, category);
        product4 = new Product("d", company, 4, category);
        product5 = new Product("e", company, 5, category);
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
    }

    @Test
    public void viewSortingTest() {
        setAll();
        product1.setVisit(10);
        product2.setVisit(5);
        product3.setVisit(11);
        product4.setVisit(0);
        product5.setVisit(19);
        expectationSort.add(product5);
        expectationSort.add(product3);
        expectationSort.add(product1);
        expectationSort.add(product2);
        expectationSort.add(product4);
        Sorting.setSortByView();
        Collections.sort(products, Sorting.getComparator());
        Object[] productsArray = products.toArray();
        Object[] expectationArray = expectationSort.toArray();
        Assert.assertArrayEquals(expectationArray, productsArray);
    }

    @Test
    public void scoreSortingTest() {
        setAll();
        product1.getScore().setAvgScore(2.8);
        product2.getScore().setAvgScore(5);
        product3.getScore().setAvgScore(1.1);
        product4.getScore().setAvgScore(3.2);
        product5.getScore().setAvgScore(0);
        expectationSort.add(product2);
        expectationSort.add(product4);
        expectationSort.add(product1);
        expectationSort.add(product3);
        expectationSort.add(product5);
        Sorting.setSortByScore();
        Collections.sort(products, Sorting.getComparator());
        Object[] productsArray = products.toArray();
        Object[] expectationArray = expectationSort.toArray();
        Assert.assertArrayEquals(expectationArray, productsArray);
    }

    @Test
    public void dateSortingTest()throws Exception{
        setAll();
        String sDate1="31/12/1998";
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        String sDate2="30/10/1997";
        Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);
        String sDate3="05/8/1998";
        Date date3=new SimpleDateFormat("dd/MM/yyyy").parse(sDate3);
        String sDate4="16/01/2000";
        Date date4=new SimpleDateFormat("dd/MM/yyyy").parse(sDate4);
        String sDate5="17/01/2000";
        Date date5=new SimpleDateFormat("dd/MM/yyyy").parse(sDate5);
        product1.setDate(date1);
        product2.setDate(date2);
        product3.setDate(date3);
        product4.setDate(date4);
        product5.setDate(date5);
        expectationSort.add(product2);
        expectationSort.add(product3);
        expectationSort.add(product1);
        expectationSort.add(product4);
        expectationSort.add(product5);
        Sorting.setSortByDate();
        Collections.sort(products, Sorting.getComparator());
        Object[] productsArray = products.toArray();
        Object[] expectationArray = expectationSort.toArray();
        Assert.assertArrayEquals(expectationArray, productsArray);
    }
}
