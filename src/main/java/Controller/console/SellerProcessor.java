package Controller.console;

import model.*;
import View.SellerShowAndCatch;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SellerProcessor extends Processor {
    private static SellerProcessor instance = new SellerProcessor();

    public static SellerProcessor getInstance() {
        return instance;
    }

    private static SellerShowAndCatch sellerShowAndCatch = SellerShowAndCatch.getInstance();

    public void viewPersonalInfo() {
        sellerShowAndCatch.viewUser((Seller) user);
    }

    public String editSellerField(String command) throws InvalidCommandException {
        Pattern pattern = Pattern.compile("edit (\\S+)");
        Matcher matcher = pattern.matcher(command);
        if (!matcher.find()) {
            throw new InvalidCommandException("invalid command");
        }
        String field = matcher.group(1);
        String newField = sellerShowAndCatch.getNewField(field);
        ((Seller) user).editFields(field, newField);
        return (field + " successfully changed to " + newField);
    }

    public void viewCompanyInfo() {
        sellerShowAndCatch.showCompanyInfo(((Seller) user).getCompany());
    }

    public void viewSalesHistory() {
        sellerShowAndCatch.showSellOrders(((Seller) user).getOrders());
    }

    public void viewProductInfo(String productId) throws NullPointerException {
        if (((Seller) user).hasProductWithId(productId)) {
            sellerShowAndCatch.showProductInfo(((Seller) user).getProductById(productId));
        } else {
            throw new NullPointerException("product with this Id doesn't exist");
        }
    }

    public boolean checkProduct(String productId) {
        return ((Seller) user).hasProductWithId(productId);
    }

    public void viewProductList() {
        sellerShowAndCatch.showProductList(((Seller) user).getProductsNumber());
    }

    public void viewBuyers(String productId) throws NullPointerException {
        if (Product.hasProductWithId(productId)) {
            sellerShowAndCatch.showBuyers(((Seller) user).getBuyers(productId));
        } else {
            throw new NullPointerException("product with this Id doesn't exist");
        }
    }

    public String editProductInfo(String productId, String command) throws InvalidCommandException {
        Pattern pattern = Pattern.compile("edit (\\S+)");
        Matcher matcher = pattern.matcher(command);
        if (!matcher.find()) {
            throw new InvalidCommandException("invalid command");
        }
        String field = matcher.group(1);
        String newField = sellerShowAndCatch.getNewField(field);
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new NullPointerException("product with this Id doesn't exist");
        }
        ((Seller) user).editProduct(product, field, newField);
        return (field + " successfully changed to " + newField + "\nManager must confirm");
    }

    public String addNewProduct(String name, String companyName, String categoryName, String priceString, String number, HashMap<String, String> features) throws InvalidCommandException {
        if (number.matches("(\\d)+")) {
            int numberInt = Integer.parseInt(number);
            if (Category.hasCategoryWithName(categoryName)) {
                if (Company.hasCompanyWithName(companyName)) {
                    Company company = Company.getCompanyByName(companyName);
                    Category category = Category.getCategoryByName(categoryName);
                    if (!priceString.matches("(\\d)+")) {
                        throw new InvalidCommandException("price must be an integer");
                    }
                    Double price = Double.parseDouble(priceString);
                    ((Seller) user).addNewProduct(name, company, price, category, numberInt, features);
                    return "Product add successfully\nWaiting for manager to confirm";
                } else {
                    throw new InvalidCommandException("company with this name doesn't exist");
                }
            } else {
                throw new InvalidCommandException("category with this name doesn't exist");
            }
        } else {
            throw new InvalidCommandException("number must be an integer");
        }
    }

    public String addExistingProduct(String id, String number) throws InvalidCommandException {
        if (number.matches("(\\d)+")) {
            int numberInt = Integer.parseInt(number);
            if (Product.hasProductWithId(id)) {
                ((Seller) user).addExistingProduct(id, numberInt);
                return "Product add successfully\nWaiting for manger to confirm";
            } else {
                throw new InvalidCommandException("product with this Id doesn't exist");
            }
        } else {
            throw new InvalidCommandException("number must be an integer");
        }
    }

    public void removeProduct(String productId) throws NullPointerException {
        boolean hasProduct = ((Seller) user).hasProductWithId(productId);
        if (hasProduct) {
            ((Seller) user).removeProduct(((Seller) user).getProductById(productId));
            sellerShowAndCatch.showRemoveProductDone();
        } else {
            throw new NullPointerException("seller doesn't have this product");
        }

    }

    public void viewCategories() {
        sellerShowAndCatch.showCategories(Category.getAllCategories());
    }

    public void viewBalance() {
        sellerShowAndCatch.showBalance(user.getBalance());
    }

    public void viewOffs() {
        sellerShowAndCatch.showOffs(((Seller) user).getOffs());
    }

    public void viewOff(String offId) throws NullPointerException {
        boolean hasOff = ((Seller) user).hasOffWithId(offId);
        if (hasOff) {
            sellerShowAndCatch.showOff(((Seller) user).getOffById(offId));
        } else {
            throw new NullPointerException("off with this Id doesn't exist");
        }
    }

    public String editOff(String offId, String command) throws NullPointerException, InvalidCommandException {
        boolean hasOff = ((Seller) user).hasOffWithId(offId);
        if (hasOff) {
            Pattern pattern = Pattern.compile("edit (\\S+)");
            Matcher matcher = pattern.matcher(command);
            if (!matcher.find()) {
                throw new InvalidCommandException("invalid command");
            }
            String field = matcher.group(1);
            String newField = sellerShowAndCatch.getNewField(field);
            Off off = ((Seller) user).getOffById(offId);
            ((Seller) user).editOff(off, field, newField);
            return (field + " successfully changed to " + newField + "\nManager must confirm");
        } else {
            throw new NullPointerException("off with this Id doesn't exist");
        }
    }

    public void addOff(String startTime, String endTime, Double discountAmount, ArrayList<String> productIds) throws ParseException, InvalidCommandException {
        Date startTimeDate = new SimpleDateFormat("dd/MM/yyyy").parse(startTime);
        Date endTimeDate = new SimpleDateFormat("dd/MM/yyyy").parse(endTime);
        if (startTimeDate.after(endTimeDate)) {
            throw new InvalidCommandException("startTime must be before endTime");
        }
        ((Seller) user).addOff(startTimeDate, endTimeDate, discountAmount, productIds);
    }

}
