package model;

import javafx.scene.image.Image;
import model.database.Loader;
import model.database.Saver;
import model.database.Sqlite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Product implements Serializable {
    private static String fileAddress = "database/Product.dat";
    private static ArrayList<Product> allProducts = new ArrayList<>();
    private static HashMap<String, String> SellerUsernameToProductId = new HashMap<>();

    public static int constructId = 0;

    public static void setAllProductsInList(ArrayList<Product> allProductsInList) {
        Product.allProductsInList = allProductsInList;
    }

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
    private Map<String, String> featuresMap;
    private String description;
    private ProductScore score;
    private ArrayList<Comment> comments;
    private Category category;
    private String categoryName;

    private ArrayList<Seller> sellers;
    private ArrayList<String> sellersName;

    public Product(String productId, State.ProductState productState, String name, String companyName, double price, int visit, String date, int availableCount, HashMap<String, String> featureMap, String description, ProductScore productScore, ArrayList<Comment> comments, String categoryName, ArrayList<String> sellersName) {
        this.productId = productId;
        this.productState = productState;
        this.name = name;
        this.company = Company.getCompanyByName(companyName);
        this.price = price;
        this.visit = visit;
        this.date = changeDate(date);
        this.availableCount = availableCount;
        this.featuresMap = featureMap;
        this.description = description;
        this.score = productScore;
        this.comments = comments;
        this.categoryName = categoryName;
        this.sellersName = sellersName;

    }

    public Date changeDate(String date) {
        Date theSameDate = null;
        try {
            theSameDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return theSameDate;
    }

    public Product(String name, Company company, double price, Category category) {
        this.productId = "" + constructId;
        this.productState = State.ProductState.CREATING_PROCESS;
        this.name = name;
        this.company = company;
        this.price = price;
        this.category = category;
        category.addProduct(this);
        this.description = "";
        sellers = new ArrayList<>();
        sellersName = new ArrayList<>();
        featuresMap = new HashMap<>();
        this.date = new Date();
        score = new ProductScore();
        this.visit = 0;
        comments = new ArrayList<>();
        allProductsInQueueExpect.add(this);
        constructId += 1;
    }

    public ArrayList<String> getSellersName() {
        return sellersName;
    }

    public void setVisit(int visit) {
        this.visit = visit;
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public ArrayList<Seller> getSellers() {
        return sellers;
    }

    public Date getDate() {
        return date;
    }

    public void setFeaturesMap(Map<String, String> featuresMap) {
        this.featuresMap = featuresMap;
    }

    public State.ProductState getProductState() {
        return productState;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);

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

    public static Product getAllProductById(String productId) {
        for (Product product : allProductsInList) {
            if (product.getProductId().equalsIgnoreCase(productId)) {
                return product;
            }
        }
        for (Product product : allProductsInQueueEdit) {
            if (product.getProductId().equalsIgnoreCase(productId)) {
                return product;
            }
        }
        for (Product product : allProductsInQueueExpect) {
            if (product.getProductId().equalsIgnoreCase(productId)) {
                return product;
            }
        }
        return null;
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

    public void rateProduct(int score, User user) {
        if (!this.getScore().isUserRatedBefore(user))
            this.getScore().addBuyer(score, user.getUsername());
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
        if (!sellers.contains(seller)) {
            sellers.add(seller);
        }
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
                category.removeProduct(this);
                category = Category.getCategoryByName(newField);
                category.addProduct(this);
            } else {
                throw new InvalidCommandException("invalid category");
            }
        } else if (featuresMap.containsKey(field)) {
            featuresMap.replace(field, featuresMap.get(field), newField);
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


    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static void load() throws FileNotFoundException {
        Product[] products = (Product[]) Loader.load(Product[].class, fileAddress);
        FileProduct[] fileProducts = (FileProduct[]) Loader.load(FileProduct[].class, "database/Files.dat");
        if (products != null) {
            allProducts = new ArrayList<>(Arrays.asList(products));
            loadProducts();
        }
        if (fileProducts != null) {
            allProducts.addAll((Arrays.asList(fileProducts)));
            loadFiles();
        }
    }

    public static void loadFiles() {
        for (Product product : allProducts) {
            if (product instanceof FileProduct)
                allProductsInList.add(product);
            int id = Integer.parseInt(product.getProductId());
            if (constructId <= id) {
                constructId = id + 1;
            }
        }
    }


    public static void loadProducts() {
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
            int id = Integer.parseInt(product.getProductId());
            if (constructId <= id) {
                constructId = id + 1;
            }
        }
    }

    public static void save() throws IOException {
        ArrayList<FileProduct> files = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : allProductsInList) {
            if (product instanceof FileProduct)
                files.add((FileProduct) product);
            else
                products.add(product);
        }
        products.addAll(allProductsInQueueExpect);
        products.addAll(allProductsInQueueEdit);
        //Saver.save(products, fileAddress);
        new Sqlite().saveProduct(allProducts);
        Saver.save(files, "database/Files.dat");
    }

    public static void saveFields() {
        saveAllCategories();
        saveAllSellers();
    }

    public static void loadFields() {
        loadAllCategories();
        loadAllSellers();
    }

    public static void loadAllCategories() {
        for (Product product : allProducts) {
            product.loadCategory();
        }
    }

    public void loadCategory() {
        this.category = Category.getCategoryByName(categoryName);
    }

    public static void saveAllCategories() {
        allProducts.clear();
        allProducts.addAll(allProductsInQueueEdit);
        allProducts.addAll(allProductsInList);
        allProducts.addAll(allProductsInQueueExpect);
        for (Product product : allProducts) {
            product.saveCategory();
        }
    }

    public void saveCategory() {
        categoryName = category.getName();
        category = null;
    }

    public static void loadAllSellers() {
        for (Product product : allProducts) {
            product.loadSellers();
        }
    }

    public void loadSellers() {
        ArrayList<Seller> sellersFromDataBase = new ArrayList<>();
        for (String sellerName : sellersName) {
            sellersFromDataBase.add((Seller) User.getUserByUserName(sellerName));
        }
        sellers = sellersFromDataBase;
    }

    public static void saveAllSellers() {
        allProducts.clear();
        allProducts.addAll(allProductsInQueueEdit);
        allProducts.addAll(allProductsInList);
        allProducts.addAll(allProductsInQueueExpect);
        for (Product product : allProducts) {
            product.saveSellers();
        }
    }

    public void saveSellers() {
        sellersName.clear();
        for (Seller seller : sellers) {
            sellersName.add(seller.getUsername());
        }
        sellers = null;
    }

    public Seller getSellerByUserName(String username) {
        for (Seller seller : sellers) {
            if (seller.getUsername().equals(username))
                return seller;
        }
        return null;
    }

    public String getCategoryName() {
        return categoryName;
    }
}