package test;

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

    }

    @Test
    public void declineProductRequestTest() {
        setAll();
        Product newProduct = ((ProductRequest) productRequest).getProduct();
        manager.declineProductRequest(productRequest);
        Assert.assertFalse(Product.allProductsInQueueExpect.contains(newProduct));
    }

    @Test
    public void declineOffRequestTest() {
        setAll();
        Off newOff = ((OffRequest) offRequest).getOff();
        manager.declineOffRequest(offRequest);
        Assert.assertFalse(Off.inQueueExpectionOffs.contains(newOff));
    }

    @Test
    public void declineEditOffRequestTest() {
        setAll();
        Off newOff = ((EditOffRequest)editOffRequest).getOff();
        manager.declineEditOffRequest(((EditOffRequest)editOffRequest));
        Assert.assertTrue(newOff.getOffState().equals(State.OffState.CONFIRMED) && Off.acceptedOffs.contains(newOff) && !Off.allOffsInQueueEdit.contains(newOff));
    }

    @Test
    public void declineEditProductRequestTest() {
        setAll();
        Product newProduct = ((EditProductRequest)editProductRequest).getProduct();
        manager.declineEditProductRequest(((EditProductRequest)editProductRequest));
        Assert.assertTrue(newProduct.getProductState().equals(State.ProductState.CONFIRMED) && Product.allProductsInList.contains(newProduct) && !Product.allProductsInQueueEdit.contains(newProduct));
    }
}
