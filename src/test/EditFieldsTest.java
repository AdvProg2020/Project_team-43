import Controller.BossProcessor;
import Controller.BuyerProcessor;
import Controller.Processor;
import Controller.SellerProcessor;
import View.BuyerShowAndCatch;
import model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class EditFieldsTest {
    Company company;
    Buyer buyer;
    Seller seller;
    Manager manager;
    BuyerProcessor buyerProcessor;
    BuyerShowAndCatch buyerShowAndCatch;
    SellerProcessor sellerProcessor;
    BossProcessor bossProcessor;
    UserPersonalInfo userPersonalInfo;


    @BeforeAll
    public void setAll() {
        company = new Company("asus", "none");
        userPersonalInfo = new UserPersonalInfo("first name", "last name", "email", "phone number", "password");
        buyer = new Buyer("alireza", userPersonalInfo);
        seller = new Seller("parsa", userPersonalInfo, "asus");
        manager = new Manager("sadra", userPersonalInfo);
        buyerProcessor = BuyerProcessor.getInstance();
        bossProcessor = BossProcessor.getInstance();
        sellerProcessor = SellerProcessor.getInstance();
        buyerShowAndCatch = BuyerShowAndCatch.getInstance();

    }

    @Test
    public void buyerEditFieldTestFirstName() throws InvalidCommandException {
        setAll();
        buyer.editFields("firstname", "new name");
        Assert.assertEquals(buyer.getUserPersonalInfo().getFirstName(), ("new name"));
    }

    @Test
    public void buyerEditFieldTestLastName() throws InvalidCommandException {
        setAll();
        buyer.editFields("lastname", "new name");
        Assert.assertEquals(buyer.getUserPersonalInfo().getLastName(), ("new name"));
    }

    @Test
    public void buyerEditFieldTestEmail() throws InvalidCommandException {
        setAll();
        buyer.editFields("email", "new email");
        Assert.assertEquals(buyer.getUserPersonalInfo().getEmail(), ("new email"));
    }

    @Test
    public void buyerEditFieldTestPhoneNumber() throws InvalidCommandException {
        setAll();
        buyer.editFields("phonenumber", "new phone number");
        Assert.assertEquals(buyer.getUserPersonalInfo().getPhoneNumber(), ("new phone number"));
    }

    @Test
    public void buyerEditFieldTestPassword() throws InvalidCommandException {
        setAll();
        buyer.editFields("password", "new password");
        Assert.assertEquals(buyer.getUserPersonalInfo().getPassword(), ("new password"));
    }

    @Test
    public void sellerEditFieldTestFirstName() throws InvalidCommandException {
        setAll();
        seller.editFields("firstname", "new name");
        Assert.assertEquals(buyer.getUserPersonalInfo().getFirstName(), ("new name"));
    }

    @Test
    public void sellerEditFieldTestLastName() throws InvalidCommandException {
        setAll();
        seller.editFields("lastname", "new name");
        Assert.assertEquals(buyer.getUserPersonalInfo().getLastName(), ("new name"));
    }

    @Test
    public void sellerEditFieldTestEmail() throws InvalidCommandException {
        setAll();
        seller.editFields("email", "new email");
        Assert.assertEquals(buyer.getUserPersonalInfo().getEmail(), ("new email"));
    }

    @Test
    public void sellerEditFieldTestPhoneNumber() throws InvalidCommandException {
        setAll();
        seller.editFields("phonenumber", "new phone number");
        Assert.assertEquals(buyer.getUserPersonalInfo().getPhoneNumber(), ("new phone number"));
    }

    @Test
    public void sellerEditFieldTestPassword() throws InvalidCommandException {
        setAll();
        seller.editFields("password", "new password");
        Assert.assertEquals(buyer.getUserPersonalInfo().getPassword(), ("new password"));
    }

    @Test
    public void managerEditFieldTestFirstName() throws InvalidCommandException {
        setAll();
        Processor.user = manager;
        bossProcessor.editField("edit firstname", "new name");
        Assert.assertEquals(manager.getUserPersonalInfo().getFirstName(), "new name");
    }

    @Test
    public void managerEditFieldTestLastName() throws InvalidCommandException {
        setAll();
        Processor.user = manager;
        bossProcessor.editField("edit lastname", "new name");
        Assert.assertEquals(manager.getUserPersonalInfo().getLastName(), "new name");
    }

    @Test
    public void managerEditFieldTestEmail() throws InvalidCommandException {
        setAll();
        Processor.user = manager;
        bossProcessor.editField("edit email", "new email");
        Assert.assertEquals(manager.getUserPersonalInfo().getEmail(), "new email");
    }

    @Test
    public void managerEditFieldTestPhoneNumber() throws InvalidCommandException {
        setAll();
        Processor.user = manager;
        bossProcessor.editField("edit phonenumber", "new phone number");
        Assert.assertEquals(manager.getUserPersonalInfo().getPhoneNumber(), "new phone number");
    }

    @Test
    public void managerEditFieldTestPassword() throws InvalidCommandException {
        setAll();
        Processor.user = manager;
        bossProcessor.editField("edit password", "new password");
        Assert.assertEquals(manager.getUserPersonalInfo().getPassword(), "new password");
    }


}
