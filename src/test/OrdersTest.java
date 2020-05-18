import Controller.BossProcessor;
import Controller.Processor;
import Controller.SellerProcessor;
import model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OrdersTest {
    Manager manager;
    UserPersonalInfo userPersonalInfo;
    Buyer buyer;
    Company company;
    Category category;
    Seller seller;
    Product product1;
    Product product2;
    Product product3;
    BuyOrder buyOrder;
    SellOrder sellOrder;
    ArrayList<Seller> sellers;
    HashMap<Product, Integer> hashMap;
    ArrayList<SellOrder> sellOrders;
    ArrayList<Buyer> buyers;
    ArrayList<BuyOrder> buyOrders;

    @BeforeAll
    public void setAll() {
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
    }

    @Test
    public void getOrderSellerTest() {
        setAll();
        ArrayList<SellOrder> sellOrders = new ArrayList<>();
        sellOrders.add(sellOrder);
        Assert.assertArrayEquals(seller.getOrders().toArray(), sellOrders.toArray());
    }

    @Test
    public void getBuyerSellerTest() {
        setAll();
        Assert.assertArrayEquals(seller.getBuyers(product1.getProductId()).toArray(), buyers.toArray());
    }

    @Test
    public void toStringSellOrderTest(){
        setAll();
        Assert.assertEquals(sellOrder.toString(), "SellOrder{" +
                "payment=" + sellOrder.getPayment() +
                ", offAmount=" + sellOrder.getOffAmount() +
                ", id=" + sellOrder.getOrderId() +
                ", buyer=" + buyer +
                ", deliveryStatus=" + sellOrder.getDeliveryStatus() +
                ", productId=" + sellOrder.getProducts().getProductId() + ", }");
    }
    @Test
    public void toStringBuyOrderTest(){
        setAll();
        Assert.assertEquals(buyOrder.toString(),"BuyOrder{" +
                "payment=" + buyOrder.getPayment() +
                ", codedDiscountAmount=" + buyOrder.getCodedDiscountAmount() +
                ", id=" + buyOrder.getOrderId() +
                ", deliveryStatus=" + buyOrder.getDeliveryStatus() +
                ", sellersUsername=[" + seller.getUsername()+ ", " + "], productsId and numbers=[( " +product1.getProductId() + ", 1 )]}" );
    }
    @Test
    public void loadAndSaveFieldsBuyOrderTest(){
        setAll();
        Order.allOrders.add(buyOrder);
        Product.allProductsInList.add(product1);
        BuyOrder.saveAllFields();
        buyOrder.getProducts().clear();
        buyOrder.getSellers();
        BuyOrder.loadAllFields();
        Assert.assertTrue(buyOrder.getProducts().containsKey(product1) );
    }
    @Test
    public void getPhoneNumberBuyOrderTest(){
        setAll();
        Assert.assertEquals(buyOrder.getPhoneNumber(), "phone number");
    }

    @Test
    public void loadAndSaveFieldsSellOrderTest(){
        setAll();
        SellOrder.allOrders.add(sellOrder);
        Product.allProductsInList.add(product1);
        SellOrder.saveAllFields();
        SellOrder.loadAllFields();
        Assert.assertEquals(sellOrder.getProducts(), product1);
    }

    @Test
    public void getOrderByIdTest(){
        setAll();
        Order.allOrders.add(buyOrder);
        Assert.assertNotNull(Order.getOrderById(buyOrder.getOrderId()));
    }

    @Test
    public void getOrderByIdNullTest(){
        setAll();
        Assert.assertNull(Order.getOrderById("null Id"));
    }

    @Test
    public void loadAndSaveOrderTest(){
        setAll();
        Order.saveAllFields();
        Order.loadAllFields();
        Assert.assertTrue(Order.allOrders.contains(buyOrder));
    }

}
