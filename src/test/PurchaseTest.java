
import Controller.console.BuyerProcessor;
import Controller.console.Processor;
import javafx.util.Pair;
import model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class PurchaseTest {
    static Buyer buyer;
    static BuyerProcessor buyerProcessor;
    static Processor processor;
    static Category category1;
    static Category category2;
    static Category category3;
    static Seller seller;
    static Product product1;
    static Product product2;
    static Product product3;
    static Product product4;
    static Company company;
    static CodedDiscount codedDiscount;

    @Before
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

    @After
    public void clear() {
        User.getAllUsers().clear();
        CodedDiscount.getAllCodedDiscount().clear();
        Order.getAllOrders().clear();
        Off.getAcceptedOffs().clear();
        Off.getAllOffsInQueueEdit().clear();
        Category.getAllCategories().clear();
        Off.getAllOffs().clear();
        Off.getInQueueExpectionOffs().clear();
        Product.getAllProducts().clear();
        Product.getAllProductsInList().clear();
        Company.getAllCompanies().clear();
    }

    @Test
    public void purchaseTest() {
        Processor.user = buyer;
        if (buyerProcessor.checkDiscountCode(codedDiscount.getDiscountCode())) {
            buyerProcessor.payment("address", "0912", codedDiscount.getDiscountAmount());
            Assert.assertEquals(buyer.getBalance(), (100000 - 80), 0.0);
        } else {
            buyerProcessor.payment("address", "0912", 0);
            Assert.assertEquals(100000 - 100, buyer.getBalance(), 0.0);
        }
    }

    @Test
    public void addToBuyerCartSellerPartTest() {
        Processor.user = buyer;
        buyerProcessor.addToBuyerCart(new Pair<Product, Seller>(product4, seller));
        Assert.assertEquals(seller.getProductsNumber().get(product4), 4, 1);
    }

    @Test
    public void addToBuyerCartBuyerPartTest() {
        Processor.user = buyer;
        buyerProcessor.addToBuyerCart(new Pair<Product, Seller>(product4, seller));
        Assert.assertTrue(buyer.getNewBuyerCart().containsKey(new Pair<Product, Seller>(product4, seller)));
    }

    @Test
    public void addToBuyerCartBuyerPart2Test() {
        Processor.user = buyer;
        buyerProcessor.addToBuyerCart(new Pair<Product, Seller>(product3, seller));
        Assert.assertEquals(buyer.getNewBuyerCart().get(new Pair<>(product3, seller)), 2, 1);
    }

    @Test
    public void rateProductTest() {
        Product.getAllProductsInList().add(product4);
        Processor.user = buyer;
        try {
            buyerProcessor.manageOrders("rate " + product4.getProductId() + " 5");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(product4.getScore().getAvgScore(), 5, 1);
    }

    @Test
    public void checkValiditySellerTest() {
        Assert.assertTrue(buyerProcessor.checkProductAndSellerValidation(product4.getProductId(), "no seller"));
    }

    @Test
    public void checkValidityProductTest() {
        Assert.assertTrue(buyerProcessor.checkProductAndSellerValidation(product1.getProductId(), "seller"));
    }

    @Test
    public void increaseCartProductTest() {
        Processor.user = buyer;
        Product.allProductsInList.add(product1);
        int availableCount = product1.getAvailableCount();
        buyerProcessor.increaseProduct(product1.getProductId(), "seller");
        Assert.assertEquals(product1.getAvailableCount(), availableCount);

    }

    @Test
    public void increaseCartSellerTest() {
        Processor.user = buyer;
        int availableCount = seller.getProductsNumber().get(product1);
        Product.allProductsInList.add(product1);
        buyerProcessor.increaseProduct(product1.getProductId(), "seller");
        Assert.assertEquals(seller.getProductsNumber().get(product1), availableCount - 1, 1);
    }

    @Test
    public void isCartEmptyTest() {
        BuyerProcessor.getNewBuyerCart().clear();
        Assert.assertTrue(buyerProcessor.isCartEmpty());
    }

    @Test
    public void logoutUserTest() {
        Processor.user = buyer;
        processor.logout();
        Assert.assertNull(Processor.user);
    }

    @Test
    public void logoutBuyerCartTest() {
        Processor.user = buyer;
        buyerProcessor.logout();
        Assert.assertEquals(BuyerProcessor.getNewBuyerCart().size(), 0);
    }

    @Test
    public void addToCartFromProcessorTest() {
        Product.allProductsInList.add(product1);
        processor.addToCart(product1.getProductId(), seller);
        Assert.assertFalse(BuyerProcessor.getNewBuyerCart().isEmpty());

    }

    @Test
    public void increaseAndDecreaseCartTest() {
        Pair<Product, Seller> pair = new Pair<>(product1, seller);
        buyer.increaseCart(product1, seller);
        buyer.decreaseCart(product1, seller);
        Assert.assertTrue(buyer.getNewBuyerCart().containsKey(pair));
    }

    @Test
    public void justDecreaseCartTest() {
        Pair<Product, Seller> pair = new Pair<>(product1, seller);
        buyer.decreaseCart(product1, seller);
        Assert.assertFalse(buyer.getNewBuyerCart().containsKey(pair));
    }


}


