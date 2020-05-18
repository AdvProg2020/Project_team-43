import model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.Date;

public class SellerDataBaseTest {
    Seller seller;
    Buyer buyer;
    UserPersonalInfo userPersonalInfo;
    Company company;
    Category category;
    Off off;
    ArrayList<Product> products;
    Product product1;
    Product product2;
    Product product3;
    SellOrder sellOrder;


    @BeforeAll
    public void setAll() {
        userPersonalInfo = new UserPersonalInfo("firstName", "lastName", "email", "phoneNumber", "password");
        company = new Company("asus", "none");
        seller = new Seller("parsa", userPersonalInfo, "asus");
        User.allUsers.add(seller);
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
        seller.getOrders().add(sellOrder);
    }


    @Test
    public void getSellerTest() {
        setAll();
        ArrayList<Seller> sellers = new ArrayList<>();
        sellers.add(seller);
        Assert.assertArrayEquals(Seller.getSeller().toArray(), sellers.toArray());
    }

    @Test
    public void loadAllProductsTest(){
        setAll();
        seller.saveProducts();
        seller.getProductsNumber().clear();
        seller.loadAllProducts();
        Assert.assertTrue(seller.getProductsNumber().containsKey(product1));
    }


}
