import Controller.BuyerProcessor;
import Controller.Processor;
import javafx.util.Pair;
import model.*;
import org.codehaus.plexus.component.annotations.Component;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PurchaseTest {
    Buyer buyer;
    BuyerProcessor buyerProcessor;
    Processor processor;
    Category category1;
    Category category2;
    Category category3;
    Seller seller;
    Product product1;
    Product product2;
    Product product3;
    Product product4;
    Company company;
    CodedDiscount codedDiscount;

    @BeforeAll
    public void setAll() {
        processor = new Processor();
        buyerProcessor = BuyerProcessor.getInstance();
        buyer = new Buyer("alireza", null);
        seller = new Seller("seller", null, "asus");
        User.allUsers.add(seller);
        codedDiscount = new CodedDiscount(new Date(), new Date(), 20, 2);
        company = new Company("asus", "non");
        category1 = new Category("laptop", new ArrayList<String>());
        category2 = new Category("tablet", new ArrayList<String>());
        category3 = new Category("mobile", new ArrayList<String>());
        product1 = new Product("gt570", company, 10, category1);
        product2 = new Product("tb1010", company, 40, category2);
        product3 = new Product("nokia1100", company, 50, category3);
        product4 = new Product("iphoneX", company, 100, category3);
        seller.getProductsNumber().put(product1, 5);
        seller.getProductsNumber().put(product2, 5);
        seller.getProductsNumber().put(product3, 5);
        seller.getProductsNumber().put(product4, 5);
        buyer.addDiscountCode(codedDiscount);
        buyer.getNewBuyerCart().put(new Pair<Product, Seller>(product1, seller), 1);
        buyer.getNewBuyerCart().put(new Pair<Product, Seller>(product2, seller), 1);
        buyer.getNewBuyerCart().put(new Pair<Product, Seller>(product3, seller), 1);

    }

    @Test
    public void purchaseTest() {
        setAll();
        Processor.user = buyer;
        if (buyerProcessor.checkDiscountCode(codedDiscount.getDiscountCode())) {
            buyerProcessor.payment("address", "0912", codedDiscount.getDiscountAmount());
            Assert.assertEquals(buyer.getBalance(), (100000 - 80), 0.0);
        } else {
            buyerProcessor.payment("address", "0912", 0);
            Assert.assertEquals(100000-100, buyer.getBalance(), 0.0);
        }
    }

    @Test
    public void addToBuyerCartSellerPartTest() {
        setAll();
        Processor.user = buyer;
        buyerProcessor.addToBuyerCart(new Pair<Product, Seller>(product4, seller));
        Assert.assertEquals(seller.getProductsNumber().get(product4), 4, 1);
    }
    @Test
    public void addToBuyerCartBuyerPartTest(){
        setAll();
        Processor.user = buyer;
        buyerProcessor.addToBuyerCart(new Pair<Product, Seller>(product4, seller));
        Assert.assertTrue(buyer.getNewBuyerCart().containsKey(new Pair<Product, Seller>(product4, seller)));
    }

    @Test
    public void addToBuyerCartBuyerPart2Test(){
        setAll();
        Processor.user = buyer;
        buyerProcessor.addToBuyerCart(new Pair<Product, Seller>(product3, seller));
        Assert.assertEquals(buyer.getNewBuyerCart().get(new Pair<>(product3, seller)), 2, 1);
    }

    @Test
    public void rateProductTest(){
        setAll();
        Product.getAllProductsInList().add(product4);
        Processor.user = buyer;
        try {
            buyerProcessor.manageOrders("rate "+product4.getProductId()+" 5");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(product4.getScore().getAvgScore(), 5, 1);
    }

    @Test
    public void checkValiditySellerTest(){
        setAll();
        Assert.assertTrue(buyerProcessor.checkProductAndSellerValidation(product4.getProductId(), "no seller"));
    }

    @Test
    public void checkValidityProductTest(){
        setAll();
        Assert.assertTrue(buyerProcessor.checkProductAndSellerValidation(product1.getProductId(), "seller"));
    }

    @Test
    public void increaseCartProductTest(){
        setAll();
        Processor.user = buyer;
        Product.allProductsInList.add(product1);
        int availableCount = product1.getAvailableCount();
        buyerProcessor.increaseProduct(product1.getProductId(), "seller");
        Assert.assertEquals(product1.getAvailableCount(), availableCount-1);

    }

    @Test
    public void increaseCartSellerTest(){
        setAll();
        Processor.user = buyer;
        int availableCount = seller.getProductsNumber().get(product1);
        Product.allProductsInList.add(product1);
        buyerProcessor.increaseProduct(product1.getProductId(), "seller");
        Assert.assertEquals(seller.getProductsNumber().get(product1),availableCount-1, 1);
    }

    @Test
    public void isCartEmptyTest(){
        setAll();
        BuyerProcessor.getNewBuyerCart().clear();
        Assert.assertTrue(buyerProcessor.isCartEmpty());
    }
    @Test
    public void logoutUserTest(){
        setAll();
        Processor.user = buyer;
        processor.logout();
        Assert.assertNull(Processor.user);
    }

    @Test
    public void logoutBuyerCartTest(){
        setAll();
        Processor.user = buyer;
        buyerProcessor.logout();
        Assert.assertEquals(BuyerProcessor.getNewBuyerCart().size(), 0);
    }

    @Test
    public void addToCartFromProcessorTest(){
        setAll();
        Product.allProductsInList.add(product1);
        processor.addToCart(product1.getProductId(), seller);
        Assert.assertFalse(BuyerProcessor.getNewBuyerCart().isEmpty());

    }

    @Test
    public void increaseAndDecreaseCartTest(){
        setAll();
        Pair<Product, Seller> pair = new Pair<>(product1, seller);
        buyer.increaseCart(product1, seller);
        buyer.decreaseCart(product1, seller);
        Assert.assertTrue(buyer.getNewBuyerCart().containsKey(pair));
    }

    @Test
    public void justDecreaseCartTest(){
        setAll();
        Pair<Product, Seller> pair = new Pair<>(product1, seller);
        buyer.decreaseCart(product1, seller);
        Assert.assertFalse(buyer.getNewBuyerCart().containsKey(pair));
    }


}


