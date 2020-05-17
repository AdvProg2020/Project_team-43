package test;

import Controller.BossProcessor;
import Controller.Processor;
import model.*;
import model.request.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class AcceptRequestsTests {
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
    public void acceptSellerRequestTest()  {
        setAll();
        try {
            bossProcessor.manageRequests("accept request " + sellerRequest.getRequestId());
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Seller newSeller = ((SellerRequest) sellerRequest).getSeller();
        Assert.assertTrue(User.allUsers.contains(newSeller));
    }

    @Test
    public void acceptProductRequestTest()  {
        setAll();
        Product newProduct = ((ProductRequest) productRequest).getProduct();
        try {
            bossProcessor.manageRequests("accept request " + productRequest.getRequestId());
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue(Product.allProductsInList.contains(newProduct) && !Product.allProductsInQueueExpect.contains(newProduct) && newProduct.getProductState().equals(State.ProductState.CONFIRMED));

    }

    @Test
    public void acceptOffRequestTest()  {
        setAll();
        Off newOff = ((OffRequest) offRequest).getOff();
        try {
            bossProcessor.manageRequests("accept request " + offRequest.getRequestId());
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue(Off.acceptedOffs.contains(newOff) && !Off.inQueueExpectionOffs.contains(newOff) && newOff.getOffState().equals(State.OffState.CONFIRMED));

    }

    @Test
    public void acceptEditOffRequestTest()  {
        setAll();
        Off newOff = ((EditOffRequest) editOffRequest).getOff();
        try {
            bossProcessor.manageRequests("accept request " + editOffRequest.getRequestId());
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue(newOff.getDiscountAmount() == 20 && newOff.getOffState().equals(State.OffState.CONFIRMED) && Off.acceptedOffs.contains(newOff) && !Off.allOffsInQueueEdit.contains(newOff));
    }

    @Test
    public void acceptEditProductRequestTest()  {
        setAll();
        Product newProduct = ((EditProductRequest) editProductRequest).getProduct();
        try {
            bossProcessor.manageRequests("accept request " + editProductRequest.getRequestId());
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue(newProduct.getName().equalsIgnoreCase("new name") && newProduct.getProductState().equals(State.ProductState.CONFIRMED) && Product.allProductsInList.contains(newProduct) && !Product.allProductsInQueueEdit.contains(newProduct));
    }

}
