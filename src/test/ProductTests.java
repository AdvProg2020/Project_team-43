import controller.client.BossProcessor;
import controller.client.Processor;
import controller.client.SellerProcessor;
import model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ProductTests {
    static Manager manager;
    static UserPersonalInfo userPersonalInfo;
    static UserPersonalInfo userPersonalInfo2;
    static Buyer buyer;
    static Company company;
    static Company company2;
    static Category category;
    static Category category2;
    static Seller seller;
    static Seller seller2;
    static Product product1;
    static Product product2;
    static Product product3;
    static Product product4;
    static Product product5;
    static BossProcessor bossProcessor;
    static SellerProcessor sellerProcessor;
    static Processor processor;
    static Comment comment;

    @Before
    public void setAll() {
        userPersonalInfo2 = new UserPersonalInfo();
        processor = new Processor();
        sellerProcessor = SellerProcessor.getInstance();
        userPersonalInfo = new UserPersonalInfo("firstName", "lastName", "email", "phoneNumber", "password");
        company = new Company("asus", "none");
        company2 = new Company("lenovo", "none");
        category = new Category("laptop", new ArrayList<>());
        category2 = new Category("mobile", new ArrayList<>());
        buyer = new Buyer("alireza", userPersonalInfo);
        manager = new Manager("sadra", userPersonalInfo);
        seller = new Seller("parsa", userPersonalInfo, "asus");
        seller2 = new Seller("parsa2", userPersonalInfo, "lenovo");
        User.allUsers.add(seller);
        User.allUsers.add(seller2);
        bossProcessor = BossProcessor.getInstance();
        product1 = new Product("a", company, 1, category);
        product2 = new Product("b", company, 2, category);
        product3 = new Product("c", company, 3, category);
        product4 = new Product("d", company, 4, category);
        product5 = new Product("e", company, 5, category);
        Product.allProductsInList.add(product1);
        Product.allProductsInList.add(product2);
        Product.allProductsInList.add(product3);
        product1.setProductState(State.ProductState.CONFIRMED);
        product2.setProductState(State.ProductState.EDITING_PROCESS);
        product3.setProductState(State.ProductState.CREATING_PROCESS);
        Product.getAllProducts().add(product1);
        Product.getAllProducts().add(product2);
        Product.getAllProducts().add(product3);
        Processor.user = manager;
        comment = new Comment(product1, "opinionText", true, buyer);
    }

    @After
    public void clear() {
        Product.getAllProducts().clear();
        Product.getAllProductsInList().clear();
        Company.getAllCompanies().clear();
        Category.getAllCategories().clear();
        User.getAllUsers().clear();
    }

    @Test
    public void removeProductTest() {
        try {
            bossProcessor.manageAllProducts("remove product " + product1.getProductId());
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertFalse(Product.hasProductWithId(product1.getProductId()) && product1.getCategory().hasProduct(product1));
    }

    @Test
    public void removeFromSellerProducts() {
        product1.addSeller(seller);
        product1.addSeller(seller2);
        manager.removeFromSellerProducts(product1);
        Assert.assertFalse(seller.hasProductWithId(product1.getProductId()) && seller2.hasProductWithId(product1.getProductId()));
    }

    @Test
    public void getCompanyProductTest() {
        Assert.assertEquals(company, product1.getCompany());
    }

    @Test
    public void checkGetAllProductsForInListTest() {
        Product.allProductsInList.add(product1);
        Assert.assertNotNull(Product.getAllProductById(product1.getProductId()));
    }

    @Test
    public void checkGetAllProductsForInQueueExpectationTest() {
        Product.allProductsInQueueExpect.add(product2);
        Assert.assertNotNull(Product.getAllProductById(product2.getProductId()));

    }

    @Test
    public void checkGetAllProductsForInEditQueueExpectationTest() {
        Product.allProductsInQueueEdit.add(product3);
        Assert.assertNotNull(Product.getAllProductById(product3.getProductId()));
    }

    @Test
    public void checkGetAllProductsIfNullTest() {
        Product newProduct = new Product("new product", company, 20, category);
        Product.allProductsInQueueExpect.remove(newProduct);
        Assert.assertNull(Product.getAllProductById(newProduct.getProductId()));
    }

    @Test
    public void getAllProductInListTest() {
        ArrayList<Product> arrayOfProducts = new ArrayList<>();
        arrayOfProducts.add(product1);
        arrayOfProducts.add(product2);
        arrayOfProducts.add(product3);
        Assert.assertArrayEquals(Product.getAllProductsInList().toArray(), arrayOfProducts.toArray());
    }

    @Test
    public void rateScoreTest() {
        product1.rateProduct(5);
        Assert.assertEquals(product1.getScore().getAvgScore(), 5, 1);
    }

    @Test
    public void addVisitTest() {
        int visit = product1.getVisit();
        product1.addVisit();
        Assert.assertEquals(product1.getVisit(), visit + 1);
    }

    @Test
    public void editFieldTestName() {
        try {
            product1.editField("name", "new name");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(product1.getName(), "new name");
    }

    @Test
    public void editFieldTestCompany() {
        try {
            product1.editField("company", "lenovo");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(product1.getCompany(), company2);
    }

    @Test(expected = InvalidCommandException.class)
    public void editFieldCompanyExceptionTest() throws InvalidCommandException {
        product1.editField("company", "null company");
    }


    @Test
    public void editFieldTestPrice() {
        try {
            product1.editField("price", "40");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(product1.getPrice(), 40, 1);
    }

    @Test(expected = InvalidCommandException.class)
    public void editFieldPriceExceptionTest() throws InvalidCommandException {
        product1.editField("price", "not a number");
    }


    @Test
    public void editFieldTestCategory() {
        try {
            product1.editField("category", "mobile");
        } catch (InvalidCommandException e) {
            Assert.assertTrue(true);
        }
        Assert.assertEquals(product1.getCategory(), category2);
    }

    @Test(expected = InvalidCommandException.class)
    public void editFieldCategoryExceptionTest() throws InvalidCommandException {
        product1.editField("category", "null category");
    }

    @Test(expected = InvalidCommandException.class)
    public void editFieldInvalidCommandTest() throws InvalidCommandException {
        product1.editField("invalid field", "invalid new field");
    }

    @Test
    public void setNameProductTest() {
        product1.setName("new name");
        Assert.assertEquals(product1.getName(), "new name");
    }

    @Test
    public void setCompanyProductTest() {
        product1.setCompany(company2);
        Assert.assertEquals(product1.getCompany(), company2);
    }

    @Test
    public void setPriceProductTest() {
        product1.setPrice(33);
        Assert.assertEquals(product1.getPrice(), 33, 1);
    }

    @Test
    public void getNameCompanyTest() {
        Assert.assertEquals(company.getName(), "asus");
    }

    @Test
    public void getInfoCompanyTest() {
        Assert.assertEquals(company2.getInfo(), "none");
    }

    @Test(expected = NullPointerException.class)
    public void showAttributesExceptionTest() {
        processor.showAttributes("null Id");
    }

    @Test(expected = NullPointerException.class)
    public void compareProcessExceptionTest() {
        processor.compareProcess("null product 1", "null product 2");
    }

    @Test(expected = InvalidCommandException.class)
    public void addCommentExceptionTest() throws InvalidCommandException {
        processor.manageComments("invalid command", product2.getProductId());
    }

    @Test(expected = InvalidCommandException.class)
    public void manageCommentExceptionTest() throws InvalidCommandException {
        Processor.setIsLogin(true);
        processor.manageComments("invalid command", product2.getProductId());
    }

    @Test(expected = NullPointerException.class)
    public void showDigestExceptionTest() {
        processor.showDigest("null product");
    }

    @Test(expected = NullPointerException.class)
    public void showCommentExceptionTest() {
        processor.showComments("null product");
    }

    @Test(expected = NullPointerException.class)
    public void viewProductInfoExceptionTest() {
        Processor.user = seller;
        sellerProcessor.viewProductInfo("null product");
    }

    @Test
    public void checkProductSellerTest() {
        Processor.user = seller;
        seller.getProductsNumber().put(product1, 1);
        Assert.assertTrue(sellerProcessor.checkProduct(product1.getProductId()) && !sellerProcessor.checkProduct("null product"));

    }

    @Test(expected = NullPointerException.class)
    public void viewBuyerExceptionTest() {
        Processor.user = seller;
        sellerProcessor.viewBuyers("null product");
    }

    @Test
    public void editProductCommandExceptionTest() {
        Processor.user = seller;
        String response = sellerProcessor.editProductInfo(product1.getProductId(), "invalid command");
        Assert.assertEquals(response, "invalid command");
    }

    @Test
    public void addNewProductTest() throws InvalidCommandException {
        Processor.user = seller;
        Assert.assertEquals(sellerProcessor.addNewProduct("new name", "asus", "laptop", "15", "2", null), "Product add successfully\nWaiting for manager to confirm");
    }

    @Test(expected = InvalidCommandException.class)
    public void addNewProductCompanyTest() throws InvalidCommandException {
        Processor.user = seller;
        sellerProcessor.addNewProduct("new name", "asus1", "laptop", "15", "2", null);
    }

    @Test(expected = InvalidCommandException.class)
    public void addNewProductCategoryTest() throws InvalidCommandException {
        Processor.user = seller;
        sellerProcessor.addNewProduct("new name", "asus", "laptop1", "15", "2", null);
    }

    @Test(expected = InvalidCommandException.class)
    public void addNewProductNumberTest() throws InvalidCommandException {
        Processor.user = seller;
        sellerProcessor.addNewProduct("new name", "asus", "laptop", "15", "not a number", null);
    }

    @Test(expected = InvalidCommandException.class)
    public void addNewProductPriceTest() throws InvalidCommandException {
        Processor.user = seller;
        sellerProcessor.addNewProduct("new name", "asus", "laptop", "not a number", "2", null);
    }

    @Test
    public void addExistingProductTest() {
        Processor.user = seller;
        Assert.assertEquals(sellerProcessor.addExistingProduct(product1.getProductId(), "1"), "Product added successfully\nWaiting for manager to confirm");
    }

    @Test
    public void addExistingProductExceptionNumberTest() {
        Processor.user = seller;
        String response = sellerProcessor.addExistingProduct(product1.getProductId(), "not a number");
        Assert.assertEquals(response, "Amount must be an integer");
    }

    @Test
    public void addExistingProductNullProductExceptionTest() {
        Processor.user = seller;
        String message = sellerProcessor.addExistingProduct("null product", "1");
        Assert.assertEquals(message, "Product with this Id doesn't exist");
    }

    @Test
    public void removeProductSellerProcessorTest() {
        Processor.user = seller;
        seller.getProductsNumber().put(product1, 2);
        sellerProcessor.removeProduct(product1.getProductId());
        Assert.assertEquals(seller.getProductsNumber().get(product1), 1, 1);
    }

    @Test(expected = NullPointerException.class)
    public void removeProductExceptionTest() {
        Processor.user = seller;
        sellerProcessor.removeProduct("null product");
    }

    @Test
    public void getCommentsTest() {
        Assert.assertEquals(product2.getComments().size(), 0, 1);
    }

    @Test
    public void getDescriptionTest() {
        Assert.assertEquals(product2.getDescription().length(), 0, 1);
    }

    @Test
    public void loadAndSaveFieldsTest() {
        product1.addSeller(seller);
        product1.addSeller(seller2);
        ArrayList<Seller> keepSellers = new ArrayList<>();
        keepSellers.add(seller);
        keepSellers.add(seller2);
        Product.saveFields();
        product1.getSellers().clear();
        Product.loadFields();
        Assert.assertArrayEquals(product1.getSellers().toArray(), keepSellers.toArray());
    }

    @Test
    public void loadProductsTest() {
        Product.loadProducts();
        Assert.assertTrue(Product.allProductsInQueueEdit.contains(product2));

    }

    @Test
    public void getProductByIdSellerTestNull() {
        seller.getProductById("null product");
        Assert.assertNull(seller.getProductById("null product"));
    }

    @Test
    public void editProductSellerTest() {
        seller.editProduct(product1, "name", "new name");
        Assert.assertTrue(Product.allProductsInQueueEdit.contains(product1) && !Product.allProductsInList.contains(product1));
    }

    @Test
    public void increaseProductSellerTest() {
        seller.getProductsNumber().put(product1, 1);
        seller.increaseProduct(product1);
        Assert.assertEquals(seller.getProductsNumber().get(product1), 2, 1);
    }
}
