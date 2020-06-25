
import model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OrdersTest {
    static Manager manager;
    static UserPersonalInfo userPersonalInfo;
    static Buyer buyer;
    static Company company;
    static Category category;
    static Seller seller;
    static Product product1;
    static Product product2;
    static Product product3;
    static BuyOrder buyOrder;
    static SellOrder sellOrder;
    static ArrayList<Seller> sellers;
    static HashMap<Product, Integer> hashMap;
    static ArrayList<SellOrder> sellOrders;
    static ArrayList<Buyer> buyers;
    static ArrayList<BuyOrder> buyOrders;

    @Before
    public void init() {
        userPersonalInfo = new UserPersonalInfo("firstName", "lastName", "email", "phoneNumber", "password");
        company = new Company("asus", "none");
        category = new Category("laptop", new ArrayList<>());
        buyer = new Buyer("alireza", userPersonalInfo);
        buyers = new ArrayList<>();
        buyers.add(buyer);
        manager = new Manager("sadra", userPersonalInfo);
        seller = new Seller("parsa", userPersonalInfo, "asus");
        User.allUsers.add(seller);
        User.allUsers.add(buyer);
        sellers = new ArrayList<>();
        sellers.add(seller);
        product1 = new Product("a", company, 1, category);
        product2 = new Product("b", company, 2, category);
        product3 = new Product("c", company, 3, category);
        hashMap = new HashMap<>();
        hashMap.put(product1, 1);
        buyOrder = new BuyOrder(new Date(), 1, 0, hashMap, sellers, "phone number", "address");
        sellOrder = new SellOrder(0, new Date(), 1, product1, buyer);
        sellOrders = new ArrayList<>();
        sellOrders.add(sellOrder);
        seller.addOrder(sellOrder);
        buyOrders = new ArrayList<>();
        buyOrders.add(buyOrder);
        buyer.getOrders().add(buyOrder);
    }

    @After
    public void clear() {
        User.getAllUsers().clear();
        User.getAllUsers().clear();
        Order.constructId = 0;
        Order.getAllOrders().clear();

    }

    @Test
    public void getOrderSellerTest() {
        ArrayList<SellOrder> sellOrders = new ArrayList<>();
        sellOrders.add(sellOrder);
        Assert.assertArrayEquals(seller.getOrders().toArray(), sellOrders.toArray());
    }

    @Test
    public void getBuyerSellerTest() {
        Assert.assertArrayEquals(seller.getBuyers(product1.getProductId()).toArray(), buyers.toArray());
    }

    @Test
    public void toStringSellOrderTest() {
        Assert.assertEquals(sellOrder.toString(), sellOrder.toString());
    }

    @Test
    public void toStringBuyOrderTest() {
        Assert.assertEquals(buyOrder.toString(), buyOrder.toString());
    }

    @Test
    public void loadAndSaveFieldsBuyOrderTest() {
        Order.allOrders.add(buyOrder);
        Product.allProductsInList.add(product1);
        BuyOrder.saveAllFields();
        buyOrder.getProducts().clear();
        buyOrder.getSellers();
        BuyOrder.loadAllFields();
        Assert.assertTrue(buyOrder.getProducts().containsKey(product1));
    }

    @Test
    public void getPhoneNumberBuyOrderTest() {
        Assert.assertEquals(buyOrder.getPhoneNumber(), "phone number");
    }

    @Test
    public void getProductSellOrderTest() {
        Assert.assertEquals(product1, sellOrder.getProduct());
    }

    @Test
    public void loadAndSaveFieldsSellOrderTest() {
        SellOrder.allOrders.add(sellOrder);
        Product.allProductsInList.add(product1);
        SellOrder.saveAllFields();
        SellOrder.loadAllFields();
        Assert.assertEquals(sellOrder.getProduct(), product1);
    }

    @Test
    public void getOrderByIdTest() {
        Order.allOrders.add(buyOrder);
        Assert.assertNotNull(Order.getOrderById(buyOrder.getOrderId()));
    }

    @Test
    public void getOrderByIdNullTest() {
        Assert.assertNull(Order.getOrderById("null Id"));
    }

    @Test
    public void getBuyerOrderTest() {
        Assert.assertArrayEquals(buyer.getOrders().toArray(), buyOrders.toArray());

    }

    @Test
    public void loadAndSaveOrderTest() {
        Order.saveAllFields();
        Order.loadAllFields();
        Assert.assertTrue(Order.allOrders.contains(buyOrder));
    }

    @Test
    public void addAllTest() {
        ArrayList<Order> orders = new ArrayList<>();
        orders.addAll(buyOrders);
        orders.addAll(sellOrders);
        Order.addAll(orders);
        Assert.assertEquals(Order.constructId, 2);
    }

}
