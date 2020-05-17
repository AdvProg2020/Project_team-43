package test;

import Controller.BossProcessor;
import Controller.Processor;
import model.*;
import model.request.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.Date;

public class DeclineRequestsTests {
    Manager manager;
    Request sellerRequest;
    Request productRequest;
    Request offRequest;
    Request editOffRequest;
    Request editProductRequest;
    UserPersonalInfo userPersonalInfo;
    Product product;
    Company company;
    Seller seller;
    Category category;
    Off off;
    BossProcessor bossProcessor;

    @BeforeAll
    public void setAll() {
        manager = new Manager("managerUserName", userPersonalInfo);
        seller = new Seller("sellerUserName", userPersonalInfo, "companyName");
        userPersonalInfo = new UserPersonalInfo("firstName", "lastName", "email", "phoneNumber", "password");
        company = new Company("companyName", "info");
        category = new Category("categoryName", new ArrayList<>());
        product = new Product("productName", company, 10, category);
        off = new Off(new Date(), new Date(), 10, seller, new ArrayList<Product>());
        sellerRequest = new SellerRequest(userPersonalInfo, "companyName", "sellerUserName");
        productRequest = new ProductRequest(product, seller, 2);
        offRequest = new OffRequest(off);
        editOffRequest = new EditOffRequest(off, "discountAmount", "20");
        editProductRequest = new EditProductRequest(product, "name", "new name", seller);
        bossProcessor = BossProcessor.getInstance();
        Processor.user = manager;
    }

    @Test
    public void declineProductRequestTest() throws InvalidCommandException {
        setAll();
        Product newProduct = ((ProductRequest) productRequest).getProduct();
        bossProcessor.manageRequests("decline request " + productRequest.getRequestId());
        Assert.assertFalse(Product.allProductsInQueueExpect.contains(newProduct));
    }

    @Test
    public void declineOffRequestTest() throws InvalidCommandException {
        setAll();
        Off newOff = ((OffRequest) offRequest).getOff();
        bossProcessor.manageRequests("decline request " + offRequest.getRequestId());
        Assert.assertFalse(Off.inQueueExpectionOffs.contains(newOff));
    }

    @Test
    public void declineEditOffRequestTest() throws InvalidCommandException {
        setAll();
        Off newOff = ((EditOffRequest) editOffRequest).getOff();
        bossProcessor.manageRequests("decline request " + editOffRequest.getRequestId());
        Assert.assertTrue(newOff.getOffState().equals(State.OffState.CONFIRMED) && Off.acceptedOffs.contains(newOff) && !Off.allOffsInQueueEdit.contains(newOff));
    }

    @Test
    public void declineEditProductRequestTest() throws InvalidCommandException {
        setAll();
        Product newProduct = ((EditProductRequest) editProductRequest).getProduct();
        bossProcessor.manageRequests("decline request " + editProductRequest.getRequestId());
        Assert.assertTrue(newProduct.getProductState().equals(State.ProductState.CONFIRMED) && Product.allProductsInList.contains(newProduct) && !Product.allProductsInQueueEdit.contains(newProduct));
    }
}
