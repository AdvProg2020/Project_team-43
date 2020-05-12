package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Product {
    public static int constructId = 0;
    public static ArrayList<Product> allProductsInList = new ArrayList<Product>();
    public static ArrayList<Product> allProductsInQueueExpect = new ArrayList<Product>();

    private String productId;
    private State.ProductState productState;
    private String name;
    private Company company;
    private double price;
    private int visit;
    private Date date;

    public ArrayList<Seller> getSellers() {
        return sellers;
    }

    private int availableCount;
    private Category category;
    private Map<String, String> featuresMap;
    private Seller seller;
    private ArrayList<Seller> sellers;

    public Seller getSeller() {
        return seller;
    }

    private String description;/////////tozihat
    private ProductScore score;
    private ArrayList<Comment> comments;

    public Product(Product product) {
        productId = product.productId;
        productState = product.productState;
        name = product.name;
        company = product.company;
        price = product.price;
        category = product.category;
        seller = product.seller;
        sellers = product.sellers;
        date = product.date;
        score = product.score;
        visit = product.visit;
        comments = product.comments;
        description = product.description;
        featuresMap = product.featuresMap;
    }

    public Product(String name, Company company, double price, Category category, Seller seller) {
        this.productId = "" + constructId;
        this.productState = State.ProductState.CREATING_PROCESS;
        this.name = name;
        this.company = company;
        this.price = price;
        this.category = category;
        this.seller = seller;
        this.date = new Date();
        score = new ProductScore();
        this.visit = 0;
        comments = new ArrayList<Comment>();
        allProductsInQueueExpect.add(this);
        constructId += 1;
    }

    public Product(String productId, String name, Company company, double price, Category category, Seller seller, ArrayList<String> features) {
        this.productId = productId;
        this.productState = State.ProductState.CREATING_PROCESS;
        this.name = name;
        this.company = company;
        this.price = price;
        this.category = category;
        fillFeaturesMap(features);
        this.seller = seller;
        score = new ProductScore();
        this.visit = 0;
        comments = new ArrayList<Comment>();
        allProductsInQueueExpect.add(this);
    }


    public Date getDate() {
        return date;
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
            company = Company.getCompanyByName(newField);
        } else if (field.equalsIgnoreCase("price")) {
            price = Double.parseDouble(newField);
        } else if (field.equalsIgnoreCase("category")) {
            category = Category.getCategoryByName(newField);
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
                ", seller=" + seller +
                ", sellers=" + sellers +
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
}
