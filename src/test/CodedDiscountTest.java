
import controller.client.BossProcessor;
import controller.client.Processor;
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
    Buyer buyer;
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
        buyer = new Buyer("alireza", userPersonalInfo);
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
    public void editCodedDiscountStartTimeTest() {
        setAll();
        try {
            bossProcessor.processEditCodedDiscountSecond("Start time", codedDiscount.getDiscountCode(), "11/11/1111");
        } catch (InvalidCommandException | ParseException e) {
            Assert.assertTrue(true);
        }
        String sDate1 = "11/11/1111";
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        } catch (ParseException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(codedDiscount.getStartTime(), date1);
    }

    @Test
    public void editCodedDiscountEndTimeTest() {
        setAll();
        try {
            bossProcessor.processEditCodedDiscountSecond("end time", codedDiscount.getDiscountCode(), "11/11/2222");
        } catch (InvalidCommandException | ParseException e) {
            Assert.assertTrue(true);
        }
        String sDate1 = "11/11/2222";
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        } catch (ParseException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(codedDiscount.getEndTime(), date1);
    }

    @Test
    public void editCodedDiscountAmountTest() {
        setAll();
        try {
            bossProcessor.processEditCodedDiscountSecond("discount amount", codedDiscount.getDiscountCode(), "30");
        } catch (InvalidCommandException | ParseException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(codedDiscount.getDiscountAmount(), 30, 1);
    }

    @Test
    public void editCodedDiscountRemainingTimeTest() {
        setAll();
        try {
            bossProcessor.processEditCodedDiscountSecond("remaining time", codedDiscount.getDiscountCode(), "10");
        } catch (InvalidCommandException | ParseException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(codedDiscount.getRepeat(), 10);
    }

    @Test
    public void viewCodedDiscountTest() {
        setAll();
        try {
            bossProcessor.manageCodedDiscounts("view discount code " + codedDiscount.getDiscountCode());
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertNotNull(codedDiscount);
    }

    @Test
    public void removeCodedDiscountTest() {
        setAll();
        try {
            bossProcessor.manageCodedDiscounts("remove discount code " + codedDiscount.getDiscountCode());
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertFalse(CodedDiscount.isCodedDiscountWithThisCode(codedDiscount.getDiscountCode()));
    }

    @Test
    public void checkCodedDiscountInfoTest() {
        setAll();
        ArrayList<String> info = new ArrayList<>();
        info.add("none");
        info.add("none");
        info.add("20.2");
        info.add("11");
        try {
            Assert.assertTrue(bossProcessor.checkCodedDiscountInfo(info));
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void createDiscountCodeTest() {
        setAll();
        ArrayList<String> info = new ArrayList<>();
        info.add("11/11/1111");
        info.add("11/12/1111");
        info.add("10.5");
        info.add("3");
        try {
            manager.createDiscountCoded(info);
        } catch (ParseException e) {
            Assert.assertTrue(true);
        }
        Assert.assertNotNull(CodedDiscount.allCodedDiscount.get(CodedDiscount.allCodedDiscount.size() - 1));
    }

    @Test
    public void addAndGetCodedDiscountBuyerTest(){
        setAll();
        buyer.addDiscountCode(codedDiscount);
        Assert.assertTrue(buyer.getDiscounts().contains(codedDiscount));
    }

    @Test
    public void makeSpecialCodedDiscountBuyerTest(){
        setAll();
        buyer.getDiscounts().clear();
        buyer.makeSpecialCoddedDiscount();
        Assert.assertFalse(buyer.getDiscounts().isEmpty());
    }


}
