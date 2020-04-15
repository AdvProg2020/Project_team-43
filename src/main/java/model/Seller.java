package model;

import java.util.ArrayList;

public class Seller extends User {
    private Company company;
    private ArrayList<Product> products;
    private ArrayList<Off> offs;
    private ArrayList<SellOrder> orders;

    public Seller(String username, UserPersonalInfo userPersonalInfo, String companyName) {
        super(username, userPersonalInfo);
        this.company = Company.getCompanyByName(companyName);
        products = new ArrayList<Product>();
        offs = new ArrayList<Off>();
        orders = new ArrayList<SellOrder>();
    }

    @Override
    public void setUserType() {
        userType = UserType.SELLER;
    }

    public void viewPersonalInfo() {

    }

    public void editFields(String field) {

    }

    public void viewProduct(String productId) {

    }

    public void viewBuyers(String productId) {

    }

    public void editProduct(String productId) {

    }

    public void addProduct() {

    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void editOff(String offId) {

    }

    public void addOff(Off off) {
        offs.add(off);
    }

    public Off getOffById(String offId){

        return null;
    }

    public ArrayList<Off> getOffs(){
        return offs;
    }
}
