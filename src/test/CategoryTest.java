
import controller.client.BossProcessor;
import controller.client.Processor;
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
    Category category2;
    ArrayList<String> features;
    ArrayList<Product> products;
    Product product1;
    Product product2;
    Product product3;
    Product product4;


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
        category2 = new Category("category2Name", features);
        product1 = new Product("a", company, 1, category);
        product2 = new Product("b", company, 2, category);
        product3 = new Product("c", company, 3, category2);
        product4 = new Product("d", company, 4, category2);
        products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        Processor.user = manager;
    }


    @Test
    public void editCategoryNameTest() {
        setAll();
        manager.editCategoryName(category, "new name");
        Assert.assertEquals(category.getName(), "new name");
    }

    @Test
    public void editCategoryFeatureTest() {
        setAll();
        try {
            manager.editFeatureName(category, "size", "new size");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(category.getFeatures().get(1), "new size");
    }

    @Test
    public void removeCategoryFeatureTest() {
        setAll();
        manager.deleteFeature(category, "size");
        Assert.assertFalse(category.hasFeature("size"));
    }

    @Test
    public void removeCategoryTest() {
        setAll();
        try {
            bossProcessor.manageCategories("remove category " + category.getName());
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertFalse(Category.hasCategoryWithName(category.getName()));
    }

    @Test
    public void hasProductCategoryTest() {
        setAll();
        Assert.assertTrue(category.hasProduct(product1) && !category.hasProduct(product4));
    }

    @Test
    public void addAndRemoveProductCategoryTest() {
        setAll();
        Product newProduct = new Product("new product", company, 20, category2);
        category2.removeProduct(newProduct);
        category.addProduct(newProduct);
        newProduct.setCategory(category);
        Assert.assertTrue(category.hasProduct(newProduct) && !category2.hasProduct(newProduct));
    }

    @Test
    public void hasAndAddFeaturesTest() {
        setAll();
        category.addFeatures("newFeatures1");
        Assert.assertTrue(category.hasFeature("newFeatures1") && category2.hasFeature("size") && !category2.hasFeature("newFeatures1"));
    }

    @Test
    public void stringProductsTest(){
        setAll();
        Assert.assertEquals("[a, b]", category.stringProducts());
    }

    @Test
    public void toStringCategoryTest(){
        setAll();
        Assert.assertEquals(category.toString(), "Category{name='categoryName', features=[size, price], products=[a, b]}");
    }

    @Test
    public void addFeatureCategoryTest(){
        setAll();
        try {
            manager.addCategoryFeature(category, "new feature1");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue(category.hasFeature("new feature1"));
    }

    @Test
    public void getCompanyByNameTest(){
        setAll();
        Assert.assertNull(Company.getCompanyByName("null company"));
    }

    @Test(expected = InvalidCommandException.class)
    public void addCategoryFeatureExceptionTest() throws InvalidCommandException {
        setAll();
        manager.addCategoryFeature(category, "size");
    }

    @Test(expected = InvalidCommandException.class)
    public void editCategoryFeatureExceptionTest() throws InvalidCommandException {
        setAll();
        manager.editFeatureName(category, "old feature", "new feature");
    }

    @Test
    public void loadAndSaveFieldsTest(){
        setAll();
        Category.saveFields();
        category.getProducts().clear();
        Category.loadFields();
        Assert.assertArrayEquals(category.getProducts().toArray(), products.toArray());
    }
}
