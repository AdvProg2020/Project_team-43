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
        products = new ArrayList<>();
        offs = new ArrayList<>();
        orders = new ArrayList<>();
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
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }

    public Product getProductById(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<SellOrder> getOrders() {
        return orders;
    }

    public Company getCompany() {
        return company;
    }

    public void viewProduct(String productId) {

    }

    public void viewBuyers(String productId) {

    }

    public void editProduct(String productId) {

    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void editOff(String offId) {

    }

    public void addOff(Off off) {
        offs.add(off);
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

    public ArrayList<Off> getOffs() {
        return offs;
    }
}
