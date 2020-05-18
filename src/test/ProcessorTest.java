import Controller.Processor;
import model.Buyer;
import model.UserPersonalInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class ProcessorTest {
    Processor processor;
    Buyer buyer;
    UserPersonalInfo userPersonalInfo;




    @BeforeAll
    public void setAll(){
        processor = new Processor();
        userPersonalInfo = new UserPersonalInfo("first name", "last name", "email", "phone number", "password");
        buyer = new Buyer("alireza", userPersonalInfo);

    }

    @Test
    public void getUserTest(){
        setAll();
        Processor.user = buyer;
        Assert.assertEquals(processor.getUser(), buyer);
    }

}
