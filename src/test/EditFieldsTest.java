
import controller.client.BossProcessor;
import controller.client.BuyerProcessor;
import controller.client.Processor;

import controller.client.SellerProcessor;
import View.console.BuyerShowAndCatch;
import model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class EditFieldsTest {
    Company company;
    Company company2;
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
        company2 = new Company("lenovo", "none");
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
    public void buyerEditFieldTestFirstName() {
        setAll();
        try {
            buyer.editFields("firstname", "new name");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(buyer.getUserPersonalInfo().getFirstName(), ("new name"));
    }

    @Test
    public void buyerEditFieldTestLastName() {
        setAll();
        try {
            buyer.editFields("lastname", "new name");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(buyer.getUserPersonalInfo().getLastName(), ("new name"));
    }

    @Test
    public void buyerEditFieldTestEmail() {
        setAll();
        try {
            buyer.editFields("email", "new email");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(buyer.getUserPersonalInfo().getEmail(), ("new email"));
    }

    @Test
    public void buyerEditFieldTestPhoneNumber() {
        setAll();
        try {
            buyer.editFields("phonenumber", "new phone number");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(buyer.getUserPersonalInfo().getPhoneNumber(), ("new phone number"));
    }

    @Test
    public void buyerEditFieldTestPassword() {
        setAll();
        try {
            buyer.editFields("password", "new password");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(buyer.getUserPersonalInfo().getPassword(), ("new password"));
    }

    @Test
    public void sellerEditFieldTestFirstName() {
        setAll();
        try {
            seller.editFields("firstname", "new name");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(seller.getUserPersonalInfo().getFirstName(), ("new name"));
    }

    @Test
    public void sellerEditFieldTestLastName() {
        setAll();
        try {
            seller.editFields("lastname", "new name");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(seller.getUserPersonalInfo().getLastName(), ("new name"));
    }

    @Test
    public void sellerEditFieldTestEmail() {
        setAll();
        try {
            seller.editFields("email", "new email");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(seller.getUserPersonalInfo().getEmail(), ("new email"));
    }

    @Test
    public void sellerEditFieldTestPhoneNumber() {
        setAll();
        try {
            seller.editFields("phonenumber", "new phone number");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(seller.getUserPersonalInfo().getPhoneNumber(), ("new phone number"));
    }

    @Test
    public void sellerEditFieldTestPassword() {
        setAll();
        try {
            seller.editFields("password", "new password");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(seller.getUserPersonalInfo().getPassword(), ("new password"));
    }

    @Test
    public void sellerEditFieldTestCompany() {
        setAll();
        try {
            seller.editFields("company", "lenovo");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(seller.getCompany().getName(), "lenovo");
    }

    @Test(expected = InvalidCommandException.class)
    public void sellerInvalidFieldEditFieldTest() throws InvalidCommandException {
        setAll();
        seller.editFields("invalid field", "invalid new field");
    }

    @Test
    public void managerEditFieldTestFirstName() {
        setAll();
        Processor.user = manager;
        try {
            bossProcessor.editField("edit firstname", "new name");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(manager.getUserPersonalInfo().getFirstName(), "new name");
    }

    @Test
    public void managerEditFieldTestLastName() {
        setAll();
        Processor.user = manager;
        try {
            bossProcessor.editField("edit lastname", "new name");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(manager.getUserPersonalInfo().getLastName(), "new name");
    }

    @Test
    public void managerEditFieldTestEmail() {
        setAll();
        Processor.user = manager;
        try {
            bossProcessor.editField("edit email", "new email");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(manager.getUserPersonalInfo().getEmail(), "new email");
    }

    @Test
    public void managerEditFieldTestPhoneNumber() {
        setAll();
        Processor.user = manager;
        try {
            bossProcessor.editField("edit phonenumber", "new phone number");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(manager.getUserPersonalInfo().getPhoneNumber(), "new phone number");
    }

    @Test
    public void managerEditFieldTestPassword() {
        setAll();
        Processor.user = manager;
        try {
            bossProcessor.editField("edit password", "new password");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(manager.getUserPersonalInfo().getPassword(), "new password");
    }

    @Test(expected = InvalidCommandException.class)
    public void sellerEditFieldExceptionTest() throws InvalidCommandException {
        setAll();
        sellerProcessor.editSellerField("invalid command");
    }

    @Test(expected = InvalidCommandException.class)
    public void BuyerEditFieldExceptionTest() throws InvalidCommandException {
        setAll();
        buyer.editFields("invalid command", "invalid new field");
    }


}
