package model;


import java.util.ArrayList;
import java.util.HashMap;

public class FileProduct extends Product {
    private String address;
    private String extension;


    public FileProduct(String productId, State.ProductState productState, String name, String companyName, double price, int visit, String date, int availableCount, HashMap<String, String> featureMap, String description, ProductScore productScore, ArrayList<Comment> comments, String categoryName, ArrayList<String> sellersName, String address, String extension) {
        super(productId, productState, name, companyName, price, visit, date, availableCount, featureMap, description, productScore, comments, categoryName, sellersName);
        this.address = address;
        this.extension = extension;
    }


    public FileProduct(String fileName, String address, String sellerUsername, String extension, int price, Company company, Category category) {
        super(fileName, company, price, category);
        this.address = address;
        super.setAvailableCount(2100000000);
        super.addSeller((Seller) User.getUserByUserName(sellerUsername));

        this.extension = extension;
        allProductsInList.add(this);
        allProductsInQueueExpect.remove(this);
        this.setProductState(State.ProductState.CONFIRMED);
    }


    public static void removeFile(FileProduct fileProduct) {
        allProductsInList.remove(fileProduct);
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getAddress() {
        return address;
    }


    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

}