import Controller.BuyerProcessor;
import Controller.Processor;
import javafx.util.Pair;
import model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PurchaseTest {
    Buyer buyer;
    BuyerProcessor buyerProcessor;
    Category category1;
    Category category2;
    Category category3;
    Seller seller;
    Product product1;
    Product product2;
    Product product3;
    Company company;
    CodedDiscount codedDiscount;

    @BeforeAll
    public void setAll() {
        buyerProcessor = BuyerProcessor.getInstance();
        buyer = new Buyer("alireza", null);
        seller = new Seller("seller", null, "asus");
        codedDiscount = new CodedDiscount(new Date(), new Date(), 20, 2);
        company = new Company("asus", "non");
        category1 = new Category("laptop", new ArrayList<String>());
        category2 = new Category("tablet", new ArrayList<String>());
        category3 = new Category("mobile", new ArrayList<String>());
        product1 = new Product("gt570", company, 10, category1);
        product2 = new Product("tb1010", company, 40, category2);
        product3 = new Product("nokia1100", company, 50, category3);
        buyer.addDiscountCode(codedDiscount);
        buyer.getNewBuyerCart().put(new Pair<Product, Seller>(product1, seller), 1);
        buyer.getNewBuyerCart().put(new Pair<Product, Seller>(product2, seller), 1);
        buyer.getNewBuyerCart().put(new Pair<Product, Seller>(product3, seller), 1);

    }

    @Test
    public void purchaseTest() {
        setAll();
        Processor.user = buyer;
        if(buyerProcessor.checkDiscountCode(codedDiscount.getDiscountCode())){
            buyerProcessor.payment("address", "0912", codedDiscount.getDiscountAmount());
            Assert.assertTrue(buyer.getBalance()==(100000-80) && seller.getBalance()==(100000+100));
        } else{
            buyerProcessor.payment("address", "0912", 0);
            Assert.assertTrue((buyer.getBalance()==100000) && seller.getBalance()==100000);
        }
    }




}


