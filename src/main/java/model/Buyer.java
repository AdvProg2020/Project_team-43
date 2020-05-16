package model;

import javafx.util.Pair;
import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Buyer extends User {
    private static String fileAddress = "database/Buyer.dat";

    private transient HashMap<CodedDiscount, Integer> codedDiscounts;
    private transient HashMap<Pair<Product, Seller>, Integer> newBuyerCart = new HashMap<>();
    private transient ArrayList<BuyOrder> orders;


    public Buyer(String username, UserPersonalInfo userPersonalInfo) {
        super(username, userPersonalInfo);
        codedDiscounts = new HashMap<CodedDiscount, Integer>();
        orders = new ArrayList<BuyOrder>();
        allUsers.add(this);
    }

    @Override
    public void setUserType() {
        userType = UserType.BUYER;
    }

    public void viewPersonalInfo() {

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
        } else {
            throw new InvalidCommandException("invalid field");
        }
    }


    public ArrayList<BuyOrder> getOrders() {
        return orders;
    }


    public void increaseCart(Product product, Seller seller) {
        Pair<Product, Seller> productSellerPair = new Pair<>(product, seller);
        newBuyerCart.replace(productSellerPair,
                newBuyerCart.get(productSellerPair), newBuyerCart.get(productSellerPair) + 1);

    }


    public void decreaseCart(Product product, Seller seller) {
        Pair<Product, Seller> productSellerPair = new Pair<>(product, seller);
        if (newBuyerCart.get(productSellerPair) == 1) {
            newBuyerCart.remove(productSellerPair);

        } else {
            newBuyerCart.replace(productSellerPair,
                    newBuyerCart.get(productSellerPair), newBuyerCart.get(productSellerPair) - 1);
        }

    }


    public HashMap<Pair<Product, Seller>, Integer> getNewBuyerCart() {
        return newBuyerCart;
    }

    public double getNewCartPrice() {
        double price = 0;
        for (Pair<Product, Seller> productSellerPair : newBuyerCart.keySet()) {
            price += productSellerPair.getKey().getPrice() * newBuyerCart.get(productSellerPair);
            if (productSellerPair.getValue().isProductInOff(productSellerPair.getKey())) {
                double discount = productSellerPair.getValue().getOffDiscountAmount(productSellerPair.getKey());
                price -= productSellerPair.getKey().getPrice() * newBuyerCart.get(productSellerPair) * (discount) / 100;
            }
        }
        return price;
    }

    public void purchase(double discount, String address, String phoneNumber) {
        HashMap<Product, Integer> order = new HashMap<>();
        for (Pair<Product, Seller> productSellerPair : newBuyerCart.keySet()) {
            order.put(productSellerPair.getKey(), newBuyerCart.get(productSellerPair));
        }
        BuyOrder buyOrder = new BuyOrder(new Date(),
                this.getNewCartPrice(), discount, order, this.getSellerOfCartProducts(), phoneNumber, address);
        this.orders.add(buyOrder);
        this.balance -= this.getNewCartPrice() * (100 - discount) / 100;
        makingSellOrders();
        this.newBuyerCart.clear();
    }

    public void makingSellOrders() {
        for (Pair<Product, Seller> productSellerPair : newBuyerCart.keySet()) {
            Seller seller = productSellerPair.getValue();
            Product product = productSellerPair.getKey();
            double discount = seller.getOffDiscountAmount(product);
            SellOrder sellOrder = new SellOrder(discount, new Date(),
                    product.getPrice(), product, this);
            seller.settleMoney(product.getPrice() * (100 - discount) / 100);
            seller.addOrder(sellOrder);

        }

    }


    public ArrayList<CodedDiscount> getDiscounts() {
        return new ArrayList<>(codedDiscounts.keySet());
    }


    public void setNewBuyerCart(HashMap<Pair<Product, Seller>, Integer> newBuyerCart) {
        this.newBuyerCart = newBuyerCart;
    }

    public static void addBuyer(UserPersonalInfo personalInfo, String username) {
        new Buyer(username, personalInfo);
    }

    public boolean checkDiscountCode(CodedDiscount discount) {
        if (!this.codedDiscounts.containsKey(discount))
            return false;
        if (!discount.checkTime())
            return false;
        codedDiscounts.replace(discount, codedDiscounts.get(discount), codedDiscounts.get(discount) - 1);
        if (codedDiscounts.get(discount) == discount.getRepeat()) {
            codedDiscounts.remove(discount);
        }
        return true;

    }

    public void addDiscountCode(CodedDiscount discount) {
        codedDiscounts.put(discount, 0);
    }

    public ArrayList<Seller> getSellerOfCartProducts() {
        ArrayList<Seller> sellers = new ArrayList<>();
        for (Pair<Product, Seller> productSellerPair : newBuyerCart.keySet()) {
            sellers.add(productSellerPair.getValue());
        }
        return sellers;

    }

    public static void load() throws FileNotFoundException {
        Buyer[] buyers = (Buyer[]) Loader.load(Buyer[].class, fileAddress);
        if (buyers != null) {
            ArrayList<Buyer> allBuyers = new ArrayList<>(Arrays.asList(buyers));
            allUsers.addAll(allBuyers);
        }
    }


    public static void save() throws IOException {
        ArrayList<Buyer> allBuyers = new ArrayList<>();
        for (User user : allUsers) {
            if (user.userType == UserType.BUYER) {
                allBuyers.add((Buyer) user);
            }
        }
        Saver.save(allBuyers, fileAddress);
    }

}
