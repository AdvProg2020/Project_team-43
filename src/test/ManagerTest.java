
import Controller.BossProcessor;
import Controller.Processor;
import model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

public class ManagerTest {
    static Buyer buyer;
    static Manager manager;
    static BossProcessor bossProcessor;
    static UserPersonalInfo userPersonalInfo;


    @Before
    public void setAll() {
        userPersonalInfo = new UserPersonalInfo("first name", "last name", "email", "phone number", "password");
        buyer = new Buyer("alireza", userPersonalInfo);
        manager = new Manager("sadra", userPersonalInfo);
        bossProcessor = BossProcessor.getInstance();
    }

    @After
    public void clear() {
        User.getAllUsers().clear();
    }

    @Test
    public void deleteUserTest() {
        Processor.user = manager;
        try {
            bossProcessor.manageUsers("delete user alireza");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertFalse(User.hasUserWithUserName("alireza"));
    }

    @Test
    public void createManagerProfile(){
        ArrayList<String> info = new ArrayList<>();
        info.add("sadra2");
        info.add("first name");
        info.add("last name");
        info.add("email");
        info.add("phone number");
        info.add("password");
        manager.createManagerProfile(info);
        Assert.assertTrue(User.hasUserWithUserName("sadra2"));
    }
    @Test(expected = NullPointerException.class)
    public void getRequestByIdNullTest(){
        Assert.assertNull(manager.getRequestById("null Id"));
    }

}
