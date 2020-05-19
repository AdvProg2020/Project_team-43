import model.Buyer;
import model.Manager;
import model.User;
import model.UserPersonalInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

public class UserTest {
    Manager manager;
    Buyer buyer;
    UserPersonalInfo userPersonalInfo;

    @BeforeAll
    public void setAll(){
        userPersonalInfo = new UserPersonalInfo("firstName", "lastName", "email", "phoneNumber", "password");
        manager = new Manager("sadra", userPersonalInfo);
        buyer = new Buyer("alireza", userPersonalInfo);

    }
    @Test
    public void hasManagerTest(){
        setAll();
        Assert.assertTrue(User.hasManager());
    }

    @Test
    public void hasNotManagerTest(){
        setAll();
        User.allUsers.clear();
        Assert.assertFalse(User.hasManager());
    }

    @Test
    public void removeUserTest(){
        setAll();
        User.removeUser(buyer);
        Assert.assertFalse(User.allUsers.contains(buyer));
    }

    @Test
    public void addAllTest(){
        setAll();
        User.allUsers.clear();
        ArrayList<User> users = new ArrayList<>();
        users.add(buyer);
        users.add(manager);
        User.addAll(users);
        Assert.assertArrayEquals(User.getAllUsers().toArray(), users.toArray());
    }

    @Test
    public void addBuyerTest(){
        setAll();
        Buyer.addBuyer(userPersonalInfo, "alireza2");
        Assert.assertTrue(User.hasUserWithUserName("alireza2"));
    }

}
