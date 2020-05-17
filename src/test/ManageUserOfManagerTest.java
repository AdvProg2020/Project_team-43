import Controller.BossProcessor;
import Controller.Processor;
import model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

public class ManageUserOfManagerTest {
    Buyer buyer;
    Manager manager;
    BossProcessor bossProcessor;
    UserPersonalInfo userPersonalInfo;


    @BeforeAll
    public void setAll() {
        userPersonalInfo = new UserPersonalInfo("first name", "last name", "email", "phone number", "password");
        buyer = new Buyer("alireza", userPersonalInfo);
        manager = new Manager("sadra", userPersonalInfo);
        bossProcessor = BossProcessor.getInstance();
    }

    @Test
    public void deleteUserTest() {
        setAll();
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
        setAll();
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
}
