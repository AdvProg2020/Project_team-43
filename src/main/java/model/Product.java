package model;

import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Product {
    private static String fileAddress = "database/Product.dat";
    private static ArrayList<Product> allProducts = new ArrayList<>();
    private static HashMap<String, String> SellerUsernameToProductId = new HashMap<>();


    public static int constructId = 0;

    public static ArrayList<Product> allProductsInList = new ArrayList<Product>();
    public static ArrayList<Product> allProductsInQueueExpect = new ArrayList<Product>();
    public static ArrayList<Product> allProductsInQueueEdit = new ArrayList<>();

    private String productId;
    private State.ProductState productState;
    private String name;
    private Company company;
    private double price;
    private int visit;
    private Date date;
    private int availableCount;
    private transient Category category;
    private Map<String, String> featuresMap;
    private transient ArrayList<Seller> sellers;
    private String description;/////////tozihat
    private ProductScore score;
    private ArrayList<Comment> comments;

    public Product(String name, Company company, double price, Category category) {
        this.productId = "" + constructId;
        this.productState = State.ProductState.CREATING_PROCESS;
        this.name = name;
        this.company = company;
        this.price = price;
        this.category = category;
        sellers = new ArrayList<>();
        this.date = new Date();
        score = new ProductScore();
        this.visit = 0;
        comments = new ArrayList<>();
        allProductsInQueueExpect.add(this);
        constructId += 1;
    }

    public ArrayList<Seller> getSellers() {
        return sellers;
    }

    public Date getDate() {
        return date;
    }

    public State.ProductState getProductState() {
        return productState;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public Map<String, String> getFeaturesMap() {
        return featuresMap;
    }

    public Company getCompany() {
        return company;
    }

    public Category getCategory() {
        return category;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return availableCount > 0;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProductState(State.ProductState productState) {
        this.productState = productState;
    }

    public ProductScore getScore() {
        return score;
    }

    public static boolean hasProductWithId(String productId) {
        for (Product product : allProductsInList) {
            if (product.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }

    public static Product getProductById(String productId) {
        for (Product product : allProductsInList) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
    public static Product getAllProductById(String productId){
        for (Product product : allProductsInList) {
           if(product.getProductId().equalsIgnoreCase(productId)){
               return product;
           }
        }
        for (Product product : allProductsInQueueEdit) {
            if(product.getProductId().equalsIgnoreCase(productId)){
                return product;
            }
        }
        for (Product product : allProductsInQueueExpect) {
            if(product.getProductId().equalsIgnoreCase(productId)){
                return product;
            }
        }
        return null;
    }

    private void fillFeaturesMap(ArrayList<String> features2) {
        ArrayList<String> features1 = this.category.getFeatures();
        for (int i = 0; i < features1.size(); i++) {
            featuresMap.put(features1.get(i), features2.get(i));
        }
    }

    public String getName() {
        return name;
    }

    public String getProductId() {
        return productId;
    }

    public void rateProduct(int score) {
        this.getScore().addBuyer(score);
    }

    public static ArrayList<Product> getAllProductsInList() {
        return allProductsInList;
    }

    public int getVisit() {
        return visit;
    }

    public void addVisit() {
        this.visit += 1;
    }

    public void addSeller(Seller seller) {
        sellers.add(seller);
    }

    public void editField(String field, String newField) throws InvalidCommandException {
        if (field.equalsIgnoreCase("name")) {
            name = newField;
        } else if (field.equalsIgnoreCase("company")) {
            if (Company.hasCompanyWithName(newField)) {
                company = Company.getCompanyByName(newField);
            } else {
                throw new InvalidCommandException("invalid company");
            }
        } else if (field.equalsIgnoreCase("price")) {
            try {
                price = Double.parseDouble(newField);
            } catch (Exception e) {
                throw new InvalidCommandException("price must be double");
            }
        } else if (field.equalsIgnoreCase("category")) {
            if (Category.hasCategoryWithName(newField)) {
                category = Category.getCategoryByName(newField);
            } else {
                throw new InvalidCommandException("invalid category");
            }
        } else {
            throw new InvalidCommandException("invalid field");
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productState=" + productState +
                ", name='" + name + '\'' +
                ", company=" + company +
                ", price=" + price +
                ", visit=" + visit +
                ", date=" + date +
                ", availableCount=" + availableCount +
                ", category=" + category +
                ", featuresMap=" + featuresMap +
                ", description='" + description + '\'' +
                ", score=" + score +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setVisit(int visit) {
        this.visit = visit;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setFeaturesMap(Map<String, String> featuresMap) {
        this.featuresMap = featuresMap;
    }

    public static void load() throws FileNotFoundException {
        Product[] products = (Product[]) Loader.load(Product[].class, fileAddress);
        if (products != null) {
            allProducts = new ArrayList<>(Arrays.asList(products));
            loadProducts();
        }
    }


    private static void loadProducts() {
        for (Product product : allProducts) {
            if (product.getProductState() == State.ProductState.CREATING_PROCESS) {
                allProductsInQueueExpect.add(product);
            }
            if (product.getProductState() == State.ProductState.CONFIRMED) {
                allProductsInList.add(product);
            }
            if (product.getProductState() == State.ProductState.EDITING_PROCESS) {
                allProductsInQueueEdit.add(product);
            }
        }
    }


    public static void save() throws IOException {
        ArrayList<Product> products = new ArrayList<>(allProductsInList);
        products.addAll(allProductsInQueueExpect);
        products.addAll(allProductsInQueueEdit);
        Saver.save(products, fileAddress);
    }
}