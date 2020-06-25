import model.*;
import model.database.Database;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UserDataBaseTest {
    static Seller seller;
    static Buyer buyer;
    static UserPersonalInfo userPersonalInfo;
    static Company company;
    static Category category;
    static Off off;
    static ArrayList<Product> products;
    static Product product1;
    static Product product2;
    static Product product3;
    static SellOrder sellOrder;
    static HashMap<Product, Integer> hashMap;
    static ArrayList<Seller> sellers;
    static BuyOrder buyOrder;
    static CodedDiscount codedDiscount;

    @Before
    public void setAll() {
        userPersonalInfo = new UserPersonalInfo("firstName", "lastName", "email", "phoneNumber", "password");
        company = new Company("asus", "none");
        seller = new Seller("parsa", userPersonalInfo, "asus");
        User.allUsers.add(seller);
        sellers = new ArrayList<>();
        sellers.add(seller);
        buyer = new Buyer("alireza", userPersonalInfo);
        category = new Category("laptop", new ArrayList<>());
        products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        product1 = new Product("a", company, 1, category);
        product2 = new Product("b", company, 2, category);
        product3 = new Product("c", company, 3, category);
        seller.getProductsNumber().put(product1, 1);
        seller.getProductsNumber().put(product2, 1);
        seller.getProductsNumber().put(product3, 1);
        off = new Off(new Date(), new Date(), 10, seller, products);
        Off.allOffs.add(off);
        Off.acceptedOffs.add(off);
        seller.getOffs().add(off);
        seller.getOffs().add(off);
        sellOrder = new SellOrder(0, new Date(), 1, product1, buyer);
        hashMap = new HashMap<>();
        hashMap.put(product1, 1);
        buyOrder = new BuyOrder(new Date(), 1, 0, hashMap, sellers, "phone number", "address");
        seller.getOrders().add(sellOrder);
        codedDiscount = new CodedDiscount(new Date(), new Date(), 20, 2);
        buyer.addDiscountCode(codedDiscount);
        buyer.getOrders().add(buyOrder);
    }

    @After
    public void clear() {
        User.getAllUsers().clear();
        CodedDiscount.getAllCodedDiscount().clear();
        Order.getAllOrders().clear();
        Off.getAcceptedOffs().clear();
        Off.getAllOffsInQueueEdit().clear();
        Off.getAllOffs().clear();
        Off.getInQueueExpectionOffs().clear();
        Product.getAllProducts().clear();
        Product.getAllProductsInList().clear();
        Company.getAllCompanies().clear();
    }


    @Test
    public void getSellerTest() {
        ArrayList<Seller> sellers = new ArrayList<>();
        sellers.add(seller);
        Assert.assertArrayEquals(Seller.getSeller().toArray(), sellers.toArray());
    }

    @Test
    public void loadAndSaveSellerTest() {
        seller.saveOffs();
        seller.saveProducts();
        seller.saveSellOrders();
        seller.loadOffs();
        seller.loadProducts();
        seller.loadSellOrders();
        Assert.assertTrue(seller.getOffs().contains(off) && seller.getProductsNumber().containsKey(product1) && seller.getOrders().contains(sellOrder));
    }

    @Test
    public void loadAndSaveBuyerTest(){
        buyer.buyOrdersSave();
        buyer.codedDiscountsSave();
        buyer.getDiscounts().clear();
        buyer.getOrders().clear();
        buyer.buyOrdersLoad();
        buyer.codedDiscountsLoad();
        Assert.assertTrue(buyer.getDiscounts().contains(codedDiscount) && buyer.getOrders().contains(buyOrder));

    }


}
