import Controller.BossProcessor;
import Controller.Processor;
import model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

public class ProductTests {
    Manager manager;
    UserPersonalInfo userPersonalInfo;
    Buyer buyer;
    Company company;
    Company company2;
    Category category;
    Category category2;
    Seller seller;
    Seller seller2;
    Product product1;
    Product product2;
    Product product3;
    Product product4;
    Product product5;
    BossProcessor bossProcessor;

    @BeforeAll
    public void setAll() {
        userPersonalInfo = new UserPersonalInfo("firstName", "lastName", "email", "phoneNumber", "password");
        company = new Company("asus", "none");
        company2 = new Company("lenovo", "none");
        category = new Category("laptop", new ArrayList<>());
        category2 = new Category("mobile", new ArrayList<>());
        buyer = new Buyer("alireza", userPersonalInfo);
        manager = new Manager("sadra", userPersonalInfo);
        seller = new Seller("parsa", userPersonalInfo, "companyName");
        seller2 = new Seller("parsa2", userPersonalInfo, "companyName");
        bossProcessor = BossProcessor.getInstance();
        product1 = new Product("a", company, 1, category);
        product2 = new Product("b", company, 2, category);
        product3 = new Product("c", company, 3, category);
        product4 = new Product("d", company, 4, category);
        product5 = new Product("e", company, 5, category);
        Product.allProductsInList.add(product1);
        Product.allProductsInList.add(product2);
        Product.allProductsInList.add(product3);
        Processor.user = manager;
    }

    @Test
    public void removeProductTest() {
        setAll();
        try {
            bossProcessor.manageAllProducts("remove product " + product1.getProductId());
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertFalse(Product.hasProductWithId(product1.getProductId()) && product1.getCategory().hasProduct(product1));
    }

    @Test
    public void removeFromSellerProducts() {
        setAll();
        product1.addSeller(seller);
        product1.addSeller(seller2);
        manager.removeFromSellerProducts(product1);
        Assert.assertFalse(seller.hasProductWithId(product1.getProductId()) && seller2.hasProductWithId(product1.getProductId()));
    }

    @Test
    public void getCompanyProductTest() {
        setAll();
        Assert.assertEquals(company, product1.getCompany());
    }

    @Test
    public void checkGetAllProductsForInListTest() {
        setAll();
        Product.allProductsInList.add(product1);
        Assert.assertNotNull(Product.getAllProductById(product1.getProductId()));
    }

    @Test
    public void checkGetAllProductsForInQueueExpectationTest() {
        setAll();
        Product.allProductsInQueueExpect.add(product2);
        Assert.assertNotNull(Product.getAllProductById(product2.getProductId()));

    }

    @Test
    public void checkGetAllProductsForInEditQueueExpectationTest() {
        setAll();
        Product.allProductsInQueueEdit.add(product3);
        Assert.assertNotNull(Product.getAllProductById(product3.getProductId()));
    }

    @Test
    public void checkGetAllProductsIfNullTest() {
        setAll();
        Product newProduct = new Product("new product", company, 20, category);
        Product.allProductsInQueueExpect.remove(newProduct);
        Assert.assertNull(Product.getAllProductById(newProduct.getProductId()));
    }

    @Test
    public void getAllProductInListTest() {
        setAll();
        ArrayList<Product> arrayOfProducts = new ArrayList<>();
        arrayOfProducts.add(product1);
        arrayOfProducts.add(product2);
        arrayOfProducts.add(product3);
        Assert.assertArrayEquals(Product.getAllProductsInList().toArray(), arrayOfProducts.toArray());
    }

    @Test
    public void rateScoreTest() {
        setAll();
        product1.rateProduct(5);
        Assert.assertEquals(product1.getScore().getAvgScore(), 5, 1);
    }

    @Test
    public void addVisitTest() {
        setAll();
        int visit = product1.getVisit();
        product1.addVisit();
        Assert.assertEquals(product1.getVisit(), visit + 1);
    }

    @Test
    public void editFieldTestName() {
        setAll();
        try {
            product1.editField("name", "new name");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(product1.getName(), "new name");
    }

    @Test
    public void editFieldTestCompany() {
        setAll();
        try {
            product1.editField("company", "lenovo");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(product1.getCompany(), company2);
    }


    @Test
    public void editFieldTestPrice() {
        setAll();
        try {
            product1.editField("price", "40");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(product1.getPrice(), 40, 1);
    }


    @Test
    public void editFieldTestCategory() {
        setAll();
        try {
            product1.editField("category", "mobile");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(product1.getCategory(), category2);
    }

    @Test
    public void setNameProductTest(){
        setAll();
        product1.setName("new name");
        Assert.assertEquals(product1.getName(), "new name");
    }
    @Test
    public void setCompanyProductTest(){
        setAll();
        product1.setCompany(company2);
        Assert.assertEquals(product1.getCompany(), company2);
    }
    @Test
    public void setPriceProductTest(){
        setAll();
        product1.setPrice(33);
        Assert.assertEquals(product1.getPrice(), 33, 1);
    }

}
