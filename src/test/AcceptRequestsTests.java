package test;

import model.*;
import model.request.OffRequest;
import model.request.ProductRequest;
import model.request.Request;
import model.request.SellerRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

public class AcceptRequestsTests {
    Manager manager;
    Request sellerRequest;
    Request productRequest;
    Request offRequest;
    UserPersonalInfo userPersonalInfo;
    Product product;
    Company company;
    Seller seller;
    Category category;
    Off off;

    @BeforeAll
    public void setAll() {
        manager = new Manager("managerUserName", userPersonalInfo);
        seller = new Seller("sellerUserName", userPersonalInfo, "companyName");
        userPersonalInfo = new UserPersonalInfo("firstName", "lastName", "email", "phoneNumber", "password");
        company = new Company("companyName", "info");
        category = new Category("categoryName", null);
        product = new Product("productName", company, 10, category, seller);
        off = new Off("startTime", "endTime", 10, seller, new ArrayList<Product>());
        sellerRequest = new SellerRequest(userPersonalInfo, "companyName", "sellerUserName");
        productRequest = new ProductRequest(product);
        offRequest = new OffRequest(off);
    }

    @Test
    public void acceptSellerRequestTest() {
        setAll();
        manager.acceptSellerRequest(sellerRequest);
        Seller newSeller = ((SellerRequest) sellerRequest).getSeller();
        Assert.assertTrue(User.allUsers.contains(newSeller));
    }

    @Test
    public void acceptProductRequestTest() {
        setAll();
        manager.acceptProductRequest((ProductRequest) productRequest);
        Product newProduct = ((ProductRequest) productRequest).getProduct();
        if (Product.allProductsInList.contains(newProduct) && !Product.allProductsInQueueExpect.contains(newProduct) && newProduct.getProductState().equals(State.ProductState.CONFIRMED)) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void acceptOffRequestTest() {
        setAll();
        manager.acceptOffRequest(((OffRequest) offRequest));
        Off newOff = ((OffRequest) offRequest).getOff();
        if (Off.acceptedOffs.contains(newOff) && !Off.inQueueExpectionOffs.contains(newOff) && newOff.getOffState().equals(State.OffState.CONFIRMED)) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }
    }

}
