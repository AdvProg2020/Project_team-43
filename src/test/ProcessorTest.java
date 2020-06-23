
import Controller.BossProcessor;
import Controller.BuyerProcessor;
import Controller.Processor;
import model.*;
import model.filters.Criteria;
import model.filters.CriteriaAvailable;
import model.filters.CriteriaName;
import model.filters.CriteriaPrice;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.zip.Adler32;

public class ProcessorTest {
    Processor processor;
    Buyer buyer;
    Manager manager;
    UserPersonalInfo userPersonalInfo;
    BossProcessor bossProcessor;
    BuyerProcessor buyerProcessor;
    Product product;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeAll
    public void setAll() {
        processor = new Processor();
        bossProcessor = BossProcessor.getInstance();
        buyerProcessor = BuyerProcessor.getInstance();
        buyerProcessor.newProductFilter();
        userPersonalInfo = new UserPersonalInfo("first name", "last name", "email", "phone number", "password");
        buyer = new Buyer("alireza", userPersonalInfo);
        manager = new Manager("sadra", null);
        System.setOut(new PrintStream(outContent));


    }

    @Test
    public void getUserTest() {
        setAll();
        Processor.user = buyer;
        Assert.assertEquals(processor.getUser(), buyer);
    }

    @Test
    public void loginTest() {
        setAll();
        Assert.assertEquals(processor.login("alireza", "password"), "logged in successful");
    }

    @Test
    public void loginUserNameTest() {
        setAll();
        Assert.assertEquals(processor.login("alireza1", "password"), "there is no user with this username");
    }

    @Test
    public void loginPasswordTest() {
        setAll();
        Assert.assertEquals(processor.login("alireza", "password1"), "incorrect password");
    }

    @Test(expected = NullPointerException.class)
    public void viewUserTest() {
        setAll();
        bossProcessor.processViewUser("null user");
    }

    @Test(expected = NullPointerException.class)
    public void editCodedDiscountTest() throws ParseException, InvalidCommandException {
        setAll();
        bossProcessor.processEditCodedDiscountFirst("null code");
    }

    @Test(expected = NullPointerException.class)
    public void removeCodedDiscountTest() {
        setAll();
        bossProcessor.processRemoveCodedDiscount("null code");
    }

    @Test(expected = NullPointerException.class)
    public void viewRequestTest() {
        setAll();
        bossProcessor.viewRequestDetails("nullRequestId");
    }

    @Test(expected = InvalidCommandException.class)
    public void addCompanyTest() throws InvalidCommandException {
        setAll();
        bossProcessor.addCompany("invalid command");
    }

    @Test(expected = NullPointerException.class)
    public void removeCategoryTest() {
        setAll();
        bossProcessor.removeCategory("null category");
    }

    @Test
    public void registerUserInvalidCommand() {
        setAll();
        Assert.assertEquals("invalid command", processor.registerUser("invalid command"));
    }

    @Test
    public void registerUserExistingUser() {
        setAll();
        Assert.assertEquals("there is a user with this username", processor.registerUser("create account buyer alireza"));
    }

    @Test
    public void registerBuyer() {
        setAll();
        Assert.assertEquals("invalid type", bossProcessor.registerUser("create account buyerr alireza2"));
    }

    @Test
    public void registerManager() {
        setAll();
        Assert.assertEquals("there is a manger", bossProcessor.registerUser("create account manager a"));
    }

    @Test
    public void disablePriceFilterTest() {
        setAll();
        buyerProcessor.disableFilter("price");
        for (Criteria filter : buyerProcessor.getProductFilter().getCurrentFilters()) {
            Assert.assertFalse(filter instanceof CriteriaPrice);
        }
    }

    @Test
    public void disableAvailabilityFilterTest() {
        setAll();
        buyerProcessor.disableFilter("availability");
        for (Criteria filter : buyerProcessor.getProductFilter().getCurrentFilters()) {
            Assert.assertFalse(filter instanceof CriteriaAvailable);
        }
    }

    @Test
    public void disableNameFilterTest() {
        setAll();
        buyerProcessor.disableFilter("name");
        for (Criteria filter : buyerProcessor.getProductFilter().getCurrentFilters()) {
            Assert.assertFalse(filter instanceof CriteriaName);
        }
    }

    @Test
    public void disableFeatureFilterTest() {
        setAll();
        buyerProcessor.disableFilter("feature aa");
        Assert.assertEquals(true, outContent.toString().startsWith("you did not select a category"));

    }

    @Test
    public void disableFeatureFilterTest2() {
        setAll();
        buyerProcessor.disableFilter("faaaaa");
        Assert.assertEquals(true, outContent.toString().startsWith("there"));
    }

    @Test
    public void addFeaturesTest() {
        setAll();
        buyerProcessor.addFeaturesFilter();
        Assert.assertEquals(true, outContent.toString().startsWith("please select a category"));
    }

    @Test
    public void selectCategoryFroFilterTest() {
        setAll();
        buyerProcessor.selectCategoryForFilter("aaa");
        Assert.assertEquals(true, outContent.toString().startsWith("there is not category with this name"));
    }


    @Test
    public void showOffsTest() {
        setAll();
        buyerProcessor.showOffs();
        Assert.assertEquals(false, outContent.toString().startsWith("1"));
    }

    @Test
    public void showPersonalInfoTest() {
        setAll();
        buyerProcessor.login(buyer.getUsername(), buyer.getUserPersonalInfo().getPassword());
        buyerProcessor.viewPersonalInfo();
        Assert.assertEquals(true, outContent.toString().startsWith("User"));
    }

    @Test
    public void addCommentTest() {
        setAll();
        buyerProcessor.login(buyer.getUsername(), buyer.getUserPersonalInfo().getPassword());
        product = new Product("hello", null, 100, new Category("phone", new ArrayList<>()));
        ArrayList<String> commentInfo = new ArrayList<String>();
        commentInfo.add("hello");
        commentInfo.add("BYE");
        buyerProcessor.addComment(product, commentInfo);
        Assert.assertEquals(1, product.getComments().size());
    }

    @Test
    public void manageDigestTest() {
        setAll();
        buyerProcessor.manageDigest("add to cartt", "0");
        Assert.assertEquals(true, outContent.toString().startsWith("invalid"));
    }

    @Test
    public void editBuyerFieldTest() {
        setAll();
        buyerProcessor.login(buyer.getUsername(), buyer.getUserPersonalInfo().getPassword());
        Assert.assertNull(buyerProcessor.editBuyerField("back"));
    }

    @Test
    public void editBuyerFieldTest2() {
        setAll();
        buyerProcessor.login(buyer.getUsername(), buyer.getUserPersonalInfo().getPassword());
        Assert.assertEquals("invalid", buyerProcessor.editBuyerField("ewegwge"));
    }

    @Test
    public void editBuyerFieldTest3() {
        setAll();
        buyerProcessor.login(buyer.getUsername(), buyer.getUserPersonalInfo().getPassword());
        Assert.assertEquals("firstName" + " successfully changed to " + "ali", buyerProcessor.editBuyerField("firstName", "ali"));
    }

    @Test
    public void viewDiscountCodesTest() {
        setAll();
        buyerProcessor.login(buyer.getUsername(), buyer.getUserPersonalInfo().getPassword());
        buyerProcessor.viewBuyerDiscountCodes();
        Assert.assertEquals(false, outContent.toString().startsWith("discount"));
    }

    @Test
    public void viewBalanceTest() {
        setAll();
        buyerProcessor.login(buyer.getUsername(), buyer.getUserPersonalInfo().getPassword());
        buyerProcessor.viewBalance();
        Assert.assertEquals(true, outContent.toString().startsWith("user balance"));
    }

    @Test(expected = NullPointerException.class)
    public void viewCartTest() {
        setAll();
        buyerProcessor.showProductsInCart();
        Assert.assertNotEquals("", outContent.toString());
    }

    @Test
    public void totalPriceTest() {
        setAll();
        buyerProcessor.login(buyer.getUsername(), buyer.getUserPersonalInfo().getPassword());
        buyerProcessor.setNewBuyerCart();
        Assert.assertEquals(10, buyerProcessor.showTotalPrice(), 0);
    }

}
