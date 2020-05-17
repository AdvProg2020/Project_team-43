package model;

import model.database.Loader;
import model.database.Saver;
import model.request.EditOffRequest;
import model.request.EditProductRequest;
import model.request.OffRequest;
import model.request.ProductRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class Seller extends User {
    private static String fileAddress = "database/Seller.dat";
    private Company company;

    private transient HashMap<Product, Integer> productsNumber;
    private HashMap<String, Integer> productsNumberWithId;

    private transient ArrayList<Off> offs;
    private ArrayList<String> offsId;

    private transient ArrayList<SellOrder> orders;
    private ArrayList<String> sellOrdersId;

    public Seller(String username, UserPersonalInfo userPersonalInfo, String companyName) {
        super(username, userPersonalInfo);
        this.company = Company.getCompanyByName(companyName);
        offs = new ArrayList<>();
        orders = new ArrayList<>();
        productsNumber = new HashMap<>();
        productsNumberWithId = new HashMap<>();
        offsId = new ArrayList<>();
        sellOrdersId = new ArrayList<>();
    }

    @Override
    public void setUserType() {
        userType = UserType.SELLER;
    }


    public void editFields(String field, String newField) throws InvalidCommandException {
        if (field.equalsIgnoreCase("password")) {
            this.getUserPersonalInfo().setPassword(newField);
        } else if (field.equalsIgnoreCase("lastName")) {
            this.getUserPersonalInfo().setLastName(newField);
        } else if (field.equalsIgnoreCase("firstName")) {
            this.getUserPersonalInfo().setFirstName(newField);
        } else if (field.equalsIgnoreCase("email")) {
            this.getUserPersonalInfo().setEmail(newField);
        } else if (field.equalsIgnoreCase("phoneNumber")) {
            this.getUserPersonalInfo().setPhoneNumber(newField);
        } else if (field.equalsIgnoreCase("company")) {
            company = Company.getCompanyByName(newField);
        } else {
            throw new InvalidCommandException("invalid field");
        }
    }

    public boolean hasProductWithId(String productId) {
        for (Product product : productsNumber.keySet()) {
            if (product.getProductId().equals(productId) && productsNumber.get(product) > 0) {
                return true;
            }
        }
        return false;
    }

    public Product getProductById(String productId) {
        for (Product product : productsNumber.keySet()) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public ArrayList<SellOrder> getOrders() {
        return orders;
    }

    public Company getCompany() {
        return company;
    }

    public ArrayList<Buyer> getBuyers(String productId) {
        ArrayList<Buyer> buyers = new ArrayList<>();
        for (SellOrder order : orders) {
            if (order.hasProductWithId(productId)) {
                buyers.add(order.getBuyer());
            }
        }
        return buyers;
    }

    public void editProduct(Product product, String field, String newField) {
        new EditProductRequest(product, field, newField, this);
        Product.allProductsInList.remove(product);
        Product.allProductsInQueueEdit.add(product);
    }

    public void addNewProduct(String name, Company company, Double price, Category category, int number) {
        Product product = new Product(name, company, price, category);
        new ProductRequest(product, this, number);
    }

    public void addExistingProduct(String productId, int number) {
        new ProductRequest(Product.getProductById(productId), this, number);
    }


    public void removeProduct(Product product) {
        int number = productsNumber.get(product);
        productsNumber.replace(product, number, number - 1);
    }

    public void editOff(Off off, String field, String input) {
        new EditOffRequest(off, field, input);
        Off.acceptedOffs.remove(off);
        Off.allOffsInQueueEdit.add(off);
    }

    public void addOff(Date startTime, Date endTime, Double discountAmount, ArrayList<String> productIds) {
        ArrayList<Product> productsTemp = new ArrayList<>();
        for (String productId : productIds) {
            productsTemp.add((getProductById(productId)));
        }
        Off off = new Off(startTime, endTime, discountAmount, this, productsTemp);
        new OffRequest(off);
    }

    public Off getOffById(String offId) {
        for (Off off : offs) {
            if (off.getOffId().equals(offId)) {
                return off;
            }
        }
        return null;
    }

    public boolean hasOffWithId(String offId) {
        for (Off off : offs) {
            if (off.getOffId().equals(offId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isProductInOff(Product product) {
        for (Off off : offs) {
            if (off.getOffState() == State.OffState.CONFIRMED) {
                if (off.hasProduct(product))
                    return true;
            }
        }
        return false;
    }

    public double getOffDiscountAmount(Product product) {
        for (Off off : offs) {
            if (off.getOffState() == State.OffState.CONFIRMED) {
                if (off.hasProduct(product))
                    return off.getDiscountAmount();
            }
        }
        return 0;
    }

    public ArrayList<Off> getOffs() {
        return offs;
    }

    public void addOrder(SellOrder sellOrder) {
        orders.add(sellOrder);
    }

    public HashMap<Product, Integer> getProductsNumber() {
        return productsNumber;
    }

    public boolean isProductAvailable(Product product) {
        return productsNumber.get(product) > 0;
    }

    public void decreaseProduct(Product product) {
        productsNumber.replace(product, productsNumber.get(product), productsNumber.get(product) - 1);
    }

    public void increaseProduct(Product product) {
        productsNumber.replace(product, productsNumber.get(product), productsNumber.get(product) + 1);
    }

    public void increaseProduct(Product product, int number) {
        productsNumber.replace(product, productsNumber.get(product), productsNumber.get(product) + number);
    }

    public void settleMoney(double price) {
        this.balance += price;

    }

    public static void load() throws FileNotFoundException {
        Seller[] sellers = (Seller[]) Loader.load(Seller[].class, fileAddress);
        if (sellers != null) {
            ArrayList<Seller> allSellers = new ArrayList<>(Arrays.asList(sellers));
            allUsers.addAll(allSellers);
        }
    }


    public static void save() throws IOException {
        ArrayList<Seller> allSellers = new ArrayList<>();
        for (User user : allUsers) {
            if (user.userType == UserType.SELLER) {
                allSellers.add((Seller) user);
            }
        }
        Saver.save(allSellers, fileAddress);
    }

    public static void saveAllFields() {
        saveAllProducts();
        saveAllOffs();
        saveAllSellOrders();
    }

    public static void loadAllFields() {
        loadAllProducts();
        loadAllOffs();
        loadAllSellOrders();
    }

    public static ArrayList<Seller> getSeller() {
        ArrayList<Seller> sellers = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getUserType() == UserType.SELLER) {
                sellers.add((Seller) user);
            }
        }
        return sellers;
    }

    public static void loadAllProducts() {
        for (Seller seller : getSeller()) {
            seller.loadProducts();
        }
    }

    public static void saveAllProducts() {
        for (Seller seller : getSeller()) {
            seller.saveProducts();
        }
    }

    public void loadProducts() {
        HashMap<Product, Integer> productIntegerHashMap = new HashMap<>();
        for (String productId : productsNumberWithId.keySet()) {
            productIntegerHashMap.put(Product.getProductById(productId), productsNumberWithId.get(productId));
        }
        productsNumber = productIntegerHashMap;
    }

    public void saveProducts() {
        productsNumberWithId.clear();
        for (Product product : productsNumber.keySet()) {
            productsNumberWithId.put(product.getProductId(), productsNumber.get(product));
        }
    }

    public static void loadAllOffs() {
        for (Seller seller : getSeller()) {
            seller.loadOffs();
        }

    }

    public void loadOffs() {
        ArrayList<Off> offsFromDataBase = new ArrayList<>();
        for (String offId : offsId) {
            offsFromDataBase.add(Off.getOffById(offId));
        }
        offs = offsFromDataBase;

    }

    public static void saveAllOffs() {
        for (Seller seller : getSeller()) {
            seller.saveOffs();
        }
    }

    public void saveOffs() {
        offsId.clear();
        for (Off off : offs) {
            offsId.add(off.getOffId());
        }
    }

    public static void loadAllSellOrders() {
        for (Seller seller : getSeller()) {
            seller.loadSellOrders();
        }

    }

    public void loadSellOrders() {
        ArrayList<SellOrder> sellOrdersFromDataBase = new ArrayList<>();
        for (String orderId : sellOrdersId) {
            sellOrdersFromDataBase.add((SellOrder) Order.getOrderById(orderId));
        }
        orders = sellOrdersFromDataBase;
    }

    public static void saveAllSellOrders() {
        for (Seller seller : getSeller()) {
            seller.saveSellOrders();
        }
    }

    public void saveSellOrders() {
        sellOrdersId.clear();
        for (SellOrder sellOrder : orders) {
            sellOrdersId.add(sellOrder.getOrderId());
        }

    }

}
