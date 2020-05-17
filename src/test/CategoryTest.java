import Controller.BossProcessor;
import Controller.Processor;
import model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

public class CategoryTest {
    Manager manager;
    BossProcessor bossProcessor;
    UserPersonalInfo userPersonalInfo;
    Company company;
    Category category;
    ArrayList<String> features;


    @BeforeAll
    public void setAll() {
        userPersonalInfo = new UserPersonalInfo("first name", "last name", "email", "phone number", "password");
        manager = new Manager("sadra", userPersonalInfo);
        bossProcessor = BossProcessor.getInstance();
        company = new Company("companyName", "info");
        features = new ArrayList<>();
        features.add("size");
        features.add("price");
        category = new Category("categoryName", features);
        Processor.user = manager;
    }


    @Test
    public void editCategoryNameTest() {
        setAll();
        manager.editCategoryName(category, "new name");
        Assert.assertEquals(category.getName(), "new name");
    }

    @Test
    public void editCategoryFeatureTest() throws InvalidCommandException{
        setAll();
        manager.editFeatureName(category, "size", "new size");
        Assert.assertEquals(category.getFeatures().get(1), "new size");
    }

    @Test
    public void removeCategoryFeatureTest(){
        setAll();
        manager.deleteFeature(category, "size");
        Assert.assertFalse(category.hasFeature("size"));
    }

    @Test
    public void removeCategoryTest()throws InvalidCommandException{
        setAll();
        bossProcessor.manageCategories("remove category "+category.getName());
        Assert.assertFalse(Category.hasCategoryWithName(category.getName()));
    }
}
