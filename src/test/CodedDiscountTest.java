import Controller.BossProcessor;
import Controller.Processor;
import model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CodedDiscountTest {
    Manager manager;
    BossProcessor bossProcessor;
    UserPersonalInfo userPersonalInfo;
    Company company;
    Category category;
    ArrayList<String> features;
    Category category1;
    Category category2;
    Category category3;
    Product product1;
    Product product2;
    Product product3;
    CodedDiscount codedDiscount;

    @BeforeAll
    public void setAll() {
        userPersonalInfo = new UserPersonalInfo("first name", "last name", "email", "phone number", "password");
        manager = new Manager("sadra", userPersonalInfo);
        bossProcessor = BossProcessor.getInstance();
        company = new Company("companyName", "info");
        features = new ArrayList<>();
        features.add("size");
        features.add("price");
        codedDiscount = new CodedDiscount(new Date(), new Date(), 20, 2);
        category1 = new Category("laptop", new ArrayList<String>());
        category2 = new Category("tablet", new ArrayList<String>());
        category3 = new Category("mobile", new ArrayList<String>());
        product1 = new Product("gt570", company, 10, category1);
        product2 = new Product("tb1010", company, 40, category2);
        product3 = new Product("nokia1100", company, 50, category3);
        Processor.user = manager;
    }

    @Test
    public void editCodedDiscountStartTimeTest() throws InvalidCommandException, ParseException {
        setAll();
        bossProcessor.processEditCodedDiscountSecond("Start time", codedDiscount.getDiscountCode(), "11/11/1111");
        String sDate1 = "11/11/1111";
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        Assert.assertEquals(codedDiscount.getStartTime(), date1);
    }

    @Test
    public void editCodedDiscountEndTimeTest() throws InvalidCommandException, ParseException {
        setAll();
        bossProcessor.processEditCodedDiscountSecond("end time", codedDiscount.getDiscountCode(), "11/11/2222");
        String sDate1 = "11/11/2222";
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        Assert.assertEquals(codedDiscount.getEndTime(), date1);
    }

    @Test
    public void editCodedDiscountAmountTest() throws InvalidCommandException, ParseException {
        setAll();
        bossProcessor.processEditCodedDiscountSecond("discount amount", codedDiscount.getDiscountCode(), "30");
        Assert.assertEquals(codedDiscount.getDiscountAmount(), 30, 1);
    }

    @Test
    public void editCodedDiscountRemainingTimeTest() throws InvalidCommandException, ParseException {
        setAll();
        bossProcessor.processEditCodedDiscountSecond("remaining time", codedDiscount.getDiscountCode(), "10");
        Assert.assertEquals(codedDiscount.getRepeat(), 10 );
    }

    @Test
    public void viewCodedDiscountTest()throws InvalidCommandException{
        setAll();
        bossProcessor.manageCodedDiscounts("view discount code "+codedDiscount.getDiscountCode());
        Assert.assertNotNull(codedDiscount);
    }

    @Test
    public void removeCodedDiscountTest()throws InvalidCommandException{
        setAll();
        bossProcessor.manageCodedDiscounts("remove discount code "+codedDiscount.getDiscountCode());
        Assert.assertFalse(CodedDiscount.isCodedDiscountWithThisCode(codedDiscount.getDiscountCode()));
    }

}
