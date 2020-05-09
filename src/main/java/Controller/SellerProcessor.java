package Controller;

import model.*;
import View.SellerShowAndCatch;
import model.request.EditProductRequest;
import model.request.OffRequest;
import model.request.ProductRequest;

import java.util.ArrayList;
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

    public void viewProductList() {
        sellerShowAndCatch.showProductList((Seller) user);
    }

    public void viewBuyers(String productId) throws NullPointerException {
        Seller seller = (Seller) user;
        if (seller.hasProductWithId(productId)) {
            ArrayList<Buyer> buyers = new ArrayList<>();
            ArrayList<SellOrder> sellOrders = seller.getOrders();
            for (SellOrder order : sellOrders) {
                if (order.hasProductWithId(productId)) {
                    buyers.add(order.getBuyer());
                }
            }
            sellerShowAndCatch.showBuyers(buyers);
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
        Product newProduct = new Product(Product.getProductById(productId));
        newProduct.editField(field, newField);
        new EditProductRequest(productId, newProduct);
        return (field + " successfully changed to " + newField);
    }

    public String addNewProduct(String name, String companyName, String categoryName, String priceString) throws InvalidCommandException {
        if (Category.hasCategoryWithName(categoryName)) {
            if (Company.hasCompanyWithName(companyName)) {
                Company company = Company.getCompanyByName(companyName);
                Category category = Category.getCategoryByName(categoryName);
                int price = Integer.parseInt(priceString);
                Product product = new Product(name, company, price, category, (Seller) user);
                new ProductRequest(product);
                return "Product add successfully\nWaiting for manger to confirm";
            } else {
                throw new InvalidCommandException("company with this name doesn't exist");
            }
        } else {
            throw new InvalidCommandException("category with this name doesn't exist");
        }
    }

    public String addExistingProduct(String id) throws InvalidCommandException {
        if (Product.hasProductWithId(id)) {
            new ProductRequest(Product.getProductById(id));
            return "Product add successfully\nWaiting for manger to confirm";
        } else {
            throw new InvalidCommandException("product with this Id doesn't exist");
        }
    }

    public void removeProduct(String productId) throws NullPointerException {
        boolean hasProduct = ((Seller) user).hasProductWithId(productId);
        if (hasProduct) {
            ((Seller) user).removeProduct(((Seller) user).getProductById(productId));
            sellerShowAndCatch.showRemoveProductDone();
        } else {
            throw new NullPointerException("product with this Id doesn't exist");
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

    public void editOff(String offId) throws NullPointerException {
        boolean hasOff = ((Seller) user).hasOffWithId(offId);
        if (hasOff) {
            //TODO : zena
        } else {
            throw new NullPointerException("off with this Id doesn't exist");
        }
    }

    public void addOff(String startTime, String endTime, Double discountAmount, ArrayList<String> productIds) {
        ArrayList<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            products.add(((Seller) user).getProductById(productId));
        }
        Off off = new Off(startTime, endTime, discountAmount, (Seller) user, products);
        new OffRequest(off);
    }

}
