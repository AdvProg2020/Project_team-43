
import controller.client.BossProcessor;
import controller.client.Processor;
import model.*;
import model.request.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class AcceptRequestsTests {
    static Manager manager;
    static Request sellerRequest;
    static Request productRequest;
    static Request offRequest;
    static Request editOffRequest;
    static Request editProductRequest;
    static UserPersonalInfo userPersonalInfo;
    static Product product;
    static Company company;
    static Seller seller;
    static Category category;
    static Off off;
    static BossProcessor bossProcessor;

    @Before
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

    @After
    public void clear() {
        Request.getAllRequests().clear();
    }

    @Test
    public void acceptSellerRequestTest() {
        try {
            bossProcessor.manageRequests("accept request " + sellerRequest.getRequestId());
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Seller newSeller = ((SellerRequest) sellerRequest).getSeller();
        Assert.assertTrue(User.allUsers.contains(newSeller));
    }

    @Test
    public void acceptProductRequestTest() {
        Product newProduct = ((ProductRequest) productRequest).getProduct();
        try {
            bossProcessor.manageRequests("accept request " + productRequest.getRequestId());
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue(Product.allProductsInList.contains(newProduct) && !Product.allProductsInQueueExpect.contains(newProduct) && newProduct.getProductState().equals(State.ProductState.CONFIRMED));

    }

    @Test
    public void acceptOffRequestTest() {
        Off newOff = ((OffRequest) offRequest).getOff();
        try {
            bossProcessor.manageRequests("accept request " + offRequest.getRequestId());
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue(Off.acceptedOffs.contains(newOff) && !Off.inQueueExpectionOffs.contains(newOff) && newOff.getOffState().equals(State.OffState.CONFIRMED));

    }

    @Test
    public void acceptEditOffRequestTest() {
        Off newOff = ((EditOffRequest) editOffRequest).getOff();
        try {
            bossProcessor.manageRequests("accept request " + editOffRequest.getRequestId());
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue(newOff.getDiscountAmount() == 20 && newOff.getOffState().equals(State.OffState.CONFIRMED) && Off.acceptedOffs.contains(newOff) && !Off.allOffsInQueueEdit.contains(newOff));
    }

    @Test
    public void acceptEditProductRequestTest() {
        Product newProduct = ((EditProductRequest) editProductRequest).getProduct();
        try {
            bossProcessor.manageRequests("accept request " + editProductRequest.getRequestId());
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue(newProduct.getName().equalsIgnoreCase("new name"));
    }

    @Test
    public void addSellerRequestTest() {
        Request.getAllRequests().clear();
        SellerRequest.addSellerRequest(userPersonalInfo, "parsa", "asus");
        Assert.assertFalse(Request.getAllRequests().isEmpty());
    }

    @Test
    public void addAllTest() {
        ArrayList<Request> requests = new ArrayList<>();
        requests.add(sellerRequest);
        requests.add(offRequest);
        requests.add(productRequest);
        Request.getAllRequests().clear();
        Request.addAll(requests);
        Assert.assertArrayEquals(Request.getAllRequests().toArray(), requests.toArray());
    }


    @Test
    public void loadAndSaveFieldOffTest() {
        Request.saveFields();
        Request.loadFields();
        Assert.assertEquals(((OffRequest) offRequest).getOff(), off);
    }

    @Test
    public void loadAndSaveFieldProductTest() {
        Request.saveFields();
        Request.loadFields();
        Assert.assertEquals(((ProductRequest) productRequest).getProduct(), product);
    }

    @Test
    public void loadAndSaveFieldEditOffTest() {
        Request.saveFields();
        Request.loadFields();
        Assert.assertEquals(((EditOffRequest) editOffRequest).getOff(), off);
    }

    @Test
    public void loadAndSaveFieldEditProductTest() {
        Request.saveFields();
        Request.loadFields();
        Assert.assertEquals(((EditProductRequest) editProductRequest).getProduct(), product);
    }


}
