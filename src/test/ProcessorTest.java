
import Controller.BossProcessor;
import Controller.BuyerProcessor;
import Controller.Processor;
import model.Buyer;
import model.InvalidCommandException;
import model.UserPersonalInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.text.ParseException;

public class ProcessorTest {
    Processor processor;
    Buyer buyer;
    UserPersonalInfo userPersonalInfo;
    BossProcessor bossProcessor;
    BuyerProcessor buyerProcessor;


    @BeforeAll
    public void setAll() {
        processor = new Processor();
        bossProcessor = BossProcessor.getInstance();
        buyerProcessor = BuyerProcessor.getInstance();
        userPersonalInfo = new UserPersonalInfo("first name", "last name", "email", "phone number", "password");
        buyer = new Buyer("alireza", userPersonalInfo);

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
    public void removeCategoryTest(){
        setAll();
        bossProcessor.removeCategory("null category");
    }

    @Test
    public void registerUserInvalidCommand(){
        setAll();
        Assert.assertEquals("invalid command", processor.registerUser("invalid command"));
    }
    @Test
    public void registerUserExistingUser(){
        setAll();
        Assert.assertEquals("there is a user with this username", processor.registerUser("create account buyer alireza"));
    }
}
