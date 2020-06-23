import Controller.BossProcessor;
import Controller.Processor;
import model.Buyer;
import model.InvalidCommandException;
import model.UserPersonalInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class ProcessorTest {
    Processor processor;
    Buyer buyer;
    UserPersonalInfo userPersonalInfo;
    BossProcessor bossProcessor;


    @BeforeAll
    public void setAll() {
        processor = new Processor();
        bossProcessor = new BossProcessor();
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
        bossProcessor.processViewUser("null user");
    }
}
