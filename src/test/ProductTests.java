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
    Category category;
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
        company = new Company("asus", "non");
        category = new Category("laptop", new ArrayList<>());
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
    public void removeFromSellerProducts(){
        setAll();
        product1.addSeller(seller);
        product1.addSeller(seller2);
        manager.removeFromSellerProducts(product1);
        Assert.assertFalse(seller.hasProductWithId(product1.getProductId()) && seller2.hasProductWithId(product1.getProductId()));
    }



}
