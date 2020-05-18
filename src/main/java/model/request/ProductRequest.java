package model.request;

import model.Manager;
import model.Product;
import model.Seller;
import model.User;
import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ProductRequest extends Request {
    private static String fileAddress = "database/ProductRequest.dat";
    private transient Product product;
    private String productId;
    private transient Seller seller;
    private String sellerUsername;
    private int number;

    public ProductRequest(Product product, Seller seller, int number) {
        super("product");
        this.product = product;
        this.seller = seller;
        this.number = number;
    }

    public Product getProduct() {
        return product;
    }

    public Seller getSeller() {
        return seller;
    }

    public int getNumber() {
        return number;
    }

    public static void load() throws FileNotFoundException {
        ProductRequest[] productRequests = (ProductRequest[]) Loader.load(ProductRequest[].class, fileAddress);
        if (productRequests != null) {
            Request.addAll(new ArrayList<>(Arrays.asList(productRequests)));
        }
    }


    public static void save() throws IOException {
        ArrayList<ProductRequest> productRequests = new ArrayList<>();
        for (Request request : allRequests) {
            if (request.getRequestType().equals("product")) {
                productRequests.add((ProductRequest) request);
            }
        }
        Saver.save(productRequests, fileAddress);
    }

    private void loadProduct() {
        this.product = Product.getAllProductById(productId);
    }

    private void saveProduct() {
        this.productId = product.getProductId();
    }

    private static void loadAllProducts() {
        for (Request request : allRequests) {
            if (request.getRequestType().equals("product")) {
                ((ProductRequest) request).loadProduct();
            }
        }
    }

    private static void saveAllProducts() {
        for (Request request : allRequests) {
            if (request.getRequestType().equals("product")) {
                ((ProductRequest) request).saveProduct();
            }
        }
    }

    private void loadSeller() {
        this.seller = (Seller) User.getUserByUserName(this.sellerUsername);
    }

    private void saveSeller() {
        this.sellerUsername = seller.getUsername();
    }

    private static void loadAllSellers() {
        for (Request request : allRequests) {
            if (request.getRequestType().equals("product")) {
                ((ProductRequest) request).loadSeller();
            }
        }
    }

    private static void saveAllSellers() {
        for (Request request : allRequests) {
            if (request.getRequestType().equals("product")) {
                ((ProductRequest) request).saveSeller();
            }
        }
    }

    public static void loadFields() {
        loadAllProducts();
        loadAllSellers();
    }

    public static void saveFields() {
        saveAllProducts();
        saveAllSellers();
    }

    @Override
    public String toString() {
        return "ProductRequest{" +
                "requestId='" + requestId + '\'' +
                ", product=" + product +
                ", seller=" + seller +
                ", number=" + number +
                ", requestType='" + requestType + '\'' +
                '}';
    }
}
