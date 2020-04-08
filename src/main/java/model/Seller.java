package model;

import java.util.ArrayList;

public class Seller extends User {
    private Company company;
    private ArrayList<Product> products;
    private ArrayList<Off> offs;

    public Seller(String nickName, String firstName, String lastName, String email, String phoneNumber, String password, String companyName) {
        super(nickName, firstName, lastName, email, phoneNumber, password);
        this.company = Company.getCompanyByName(companyName);
        this.userType = 2;
        products = new ArrayList<Product>();
        offs = new ArrayList<Off>();
        User.allUsers.add(this);
    }

    public void viewPersonalInfo() {

    }

    public void editFields(String field) {

    }

    public void viewCompanyInfo() {

    }

    public void viewSalesHistory() {

    }

    public void viewProduct(String productId) {

    }

    public void viewBuyers(String productId) {

    }

    public void editProduct(String productId) {

    }

    public void addProduct() {

    }

    public void removeProduct(String productId) {

    }

    public void viewOffs(){

    }

    public void editOff(String offId){

    }

    public void addOff(){

    }
}
