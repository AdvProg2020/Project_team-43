import Controller.Processor;
import Controller.SellerProcessor;
import model.*;
import model.request.OffRequest;
import model.request.Request;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OffTest {
    Processor processor;
    Buyer buyer;
    Seller seller;
    UserPersonalInfo userPersonalInfo;
    SellerProcessor sellerProcessor;
    ArrayList<Product> products;
    Product product1;
    Product product2;
    Product product3;
    Product product4;
    Company company;
    Category category;
    Off off;
    ArrayList<String> productsId;

    @BeforeAll
    public void setAll() {
        processor = new Processor();
        sellerProcessor = SellerProcessor.getInstance();
        company = new Company("asus", "none");
        category = new Category("laptop", new ArrayList<>());
        userPersonalInfo = new UserPersonalInfo("first name", "last name", "email", "phone number", "password");
        buyer = new Buyer("alireza", userPersonalInfo);
        seller = new Seller("parsa", userPersonalInfo, "asus");
        User.allUsers.add(seller);
        product1 = new Product("a", company, 1, category);
        product2 = new Product("b", company, 2, category);
        product3 = new Product("c", company, 3, category);
        product4 = new Product("d", company, 4, category);
        products = new ArrayList<>();
        productsId = new ArrayList<>();
        productsId.add(product1.getProductId());
        productsId.add(product2.getProductId());
        productsId.add(product3.getProductId());
        products.add(product1);
        products.add(product2);
        products.add(product3);
        off = new Off(new Date(), new Date(), 10, seller, products);
        Off.acceptedOffs.add(off);
        seller.getOffs().add(off);

    }

    @Test(expected = NullPointerException.class)
    public void viewOffExceptionTest() {
        setAll();
        Processor.user = seller;
        sellerProcessor.viewOff("null off");
    }

    @Test(expected = InvalidCommandException.class)
    public void editOffExceptionTest() throws InvalidCommandException {
        setAll();
        Processor.user = seller;
        sellerProcessor.editOff(off.getOffId(), "invalid command");
    }

    @Test
    public void addOffTest() throws ParseException, InvalidCommandException {
        setAll();
        Processor.user = seller;
        sellerProcessor.addOff("11/11/1111", "12/11/1111", 20.0, productsId);
        Assert.assertTrue(((Request.getAllRequests().get(Request.getAllRequests().size() - 1))) instanceof OffRequest);
    }

    @Test(expected = InvalidCommandException.class)
    public void addOffDateExceptionTest() throws ParseException, InvalidCommandException {
        setAll();
        Processor.user = seller;
        sellerProcessor.addOff("12/11/1111", "11/11/1111", 20.0, productsId);
    }

    @Test
    public void getOffByIdTest() {
        setAll();
        Assert.assertEquals(Off.getOffById(off.getOffId()), off);
    }

    @Test
    public void getOffByIdNullTest() {
        setAll();
        Assert.assertNull(Off.getOffById("null off"));
    }

    @Test
    public void isProductInOffTest() {
        setAll();
        Assert.assertEquals(Off.isProductInOff(product1), off.getDiscountAmount(), 1);
    }

    @Test
    public void isProductInOffNullTest() {
        setAll();
        Assert.assertEquals(Off.isProductInOff(product4), 0, 1);
    }

    @Test
    public void hasProductTest() {
        setAll();
        Assert.assertTrue(off.hasProduct(product1));
    }

    @Test
    public void editFieldStartTimeTest() throws ParseException, InvalidCommandException {
        setAll();
        off.editField("startTime", "11/11/1000");
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("11/11/1000");
        Assert.assertEquals(off.getStartTime(), date);
    }

    @Test(expected = InvalidCommandException.class)
    public void editFieldStartTimeExceptionTest() throws ParseException, InvalidCommandException {
        setAll();
        off.editField("startTime", "11/11/3000");
    }

    @Test
    public void editFieldEndTimeTest() throws ParseException, InvalidCommandException {
        setAll();
        off.editField("endTime", "11/11/3000");
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("11/11/3000");
        Assert.assertEquals(off.getEndTime(), date);
    }

    @Test(expected = InvalidCommandException.class)
    public void editFieldEndTimeExceptionTest() throws ParseException, InvalidCommandException {
        setAll();
        off.editField("endTime", "11/11/2000");
    }

    @Test(expected = InvalidCommandException.class)
    public void editFieldAmountExceptionTest() throws ParseException, InvalidCommandException {
        setAll();
        off.editField("discountAmount", "not a number");
    }

    @Test(expected = InvalidCommandException.class)
    public void editFieldCommandTest() throws ParseException, InvalidCommandException {
        setAll();
        off.editField("invalid command", "invalid value");
    }

    @Test
    public void toStringOffTest(){
        setAll();
        Assert.assertEquals(off.toString(), "Off{" +
                "offId='" + off.getOffId() + '\'' +
                ", startTime='" + off.getStartTime() + '\'' +
                ", endTime='" + off.getEndTime() + '\'' +
                ", discountAmount=" + off.getDiscountAmount() +
                '}');
    }

}
