package model;

import javafx.util.Pair;
import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

public class Buyer extends User {
    private static String fileAddress = "database/Buyer.dat";

    private double sumOfPaymentForCoddedDiscount;

    private HashMap<CodedDiscount, Integer> codedDiscounts;
    private HashMap<String, Integer> codedDiscountsId;

    private HashMap<Pair<Product, Seller>, Integer> newBuyerCart;
    private ArrayList<BuyOrder> orders;
    private ArrayList<String> buyOrdersId;


    public Buyer(String username, UserPersonalInfo userPersonalInfo) {
        super(username, userPersonalInfo);
        newBuyerCart = new HashMap<>();
        sumOfPaymentForCoddedDiscount = 0;
        codedDiscounts = new HashMap<>();
        orders = new ArrayList<BuyOrder>();
        codedDiscountsId = new HashMap<>();
        buyOrdersId = new ArrayList<String>();
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
        Set<Pair<Product, Seller>> cart = newBuyerCart.keySet();
        for (Pair<Product, Seller> pair : cart) {
            if (pair.getKey().getProductId().equals(product.getProductId()) && pair.getValue().getUsername().equals(seller.getUsername())) {
                newBuyerCart.replace(pair, newBuyerCart.get(pair) + 1);
                break;
            }
        }
    }


    public void decreaseCart(Product product, Seller seller) {
        Set<Pair<Product, Seller>> cart = newBuyerCart.keySet();
        for (Pair<Product, Seller> pair : cart) {
            if (pair.getKey().getProductId().equals(product.getProductId()) && pair.getValue().getUsername().equals(seller.getUsername())) {
                if (newBuyerCart.get(pair) == 1) {
                    newBuyerCart.remove(pair);

                } else {
                    newBuyerCart.replace(pair,
                            newBuyerCart.get(pair), newBuyerCart.get(pair) - 1);
                }
                break;
            }
        }


    }

    public boolean hasBuyProduct(Product product) {
        for (BuyOrder order : orders) {
            for (Product buyProduct : order.getProducts().keySet()) {
                if (buyProduct.getProductId().equals(product.getProductId()))
                    return true;
            }

        }
        return false;

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

    public boolean purchase(double discount, String address, String phoneNumber) {
        for (Pair<Product, Seller> productSellerPair : newBuyerCart.keySet()) {
            synchronized (productSellerPair) {
                if (productSellerPair.getValue().isProductAvailable(productSellerPair.getKey())) {
                    decreaseInSeller(productSellerPair, newBuyerCart.get(productSellerPair));
                } else {
                    return false;
                }
            }
        }
        HashMap<Product, Integer> order = new HashMap<>();
        for (Pair<Product, Seller> productSellerPair : newBuyerCart.keySet()) {
            order.put(productSellerPair.getKey(), newBuyerCart.get(productSellerPair));
        }
        BuyOrder buyOrder = new BuyOrder(new Date(),
                this.getNewCartPrice() * (100 - discount) / 100, discount, order, this.getSellerOfCartProducts(), phoneNumber, address);
        this.orders.add(buyOrder);
        this.balance -= this.getNewCartPrice() * (100 - discount) / 100;
        this.sumOfPaymentForCoddedDiscount += this.getNewCartPrice() * (100 - discount) / 100;
        this.checkSumPaymentForOff();
        makingSellOrders();
        this.newBuyerCart = new HashMap<>();
        return true;
    }

    public void decreaseInSeller(Pair<Product, Seller> productSellerPair, int decreaseNumber) {
        for (int i = 0; i < decreaseNumber; i++) {
            productSellerPair.getValue().decreaseProduct(productSellerPair.getKey());
        }
        productSellerPair.getKey().setAvailableCount(productSellerPair.getKey().getAvailableCount() - decreaseNumber);
    }

    public void checkSumPaymentForOff() {
        if (sumOfPaymentForCoddedDiscount > 100) {
            this.makeSpecialCoddedDiscount();
            sumOfPaymentForCoddedDiscount = 0;
        }
    }

    public void makingSellOrders() {
        for (Pair<Product, Seller> productSellerPair : newBuyerCart.keySet()) {
            Seller seller = (Seller) User.getUserByUserName(productSellerPair.getValue().getUsername());
            Product product = productSellerPair.getKey();
            double discount = seller.getOffDiscountAmount(product);
            SellOrder sellOrder = new SellOrder(discount, new Date(),
                    product.getPrice() * newBuyerCart.get(productSellerPair), product, this, newBuyerCart.get(productSellerPair));
            seller.settleMoney(product.getPrice() * (100 - discount) / 100 * newBuyerCart.get(productSellerPair));
            seller.addOrder(sellOrder);
        }

    }


    public ArrayList<CodedDiscount> getDiscounts() {
        return new ArrayList<>(codedDiscounts.keySet());
    }

    public int remainRepeats(CodedDiscount codedDiscount) {
        return codedDiscount.getRepeat() - codedDiscounts.get(codedDiscount);
    }

    public void setNewBuyerCart(HashMap<Pair<Product, Seller>, Integer> newBuyerCart) {
        this.newBuyerCart = newBuyerCart;
    }

    public static void addBuyer(UserPersonalInfo personalInfo, String username) {
        new Buyer(username, personalInfo);
    }

    public boolean checkDiscountCode(CodedDiscount discount) {
        int flag = 0;
        for (CodedDiscount codedDiscount : codedDiscounts.keySet()) {
            if (codedDiscount.getDiscountCode().equals(discount.getDiscountCode())) {
                flag = 1;
                break;
            }
        }
        if (flag == 0)
            return false;
        return discount.checkTime();
    }

    public void changeRemainDiscount(CodedDiscount discount) {
        codedDiscounts.replace(discount, codedDiscounts.get(discount), codedDiscounts.get(discount) + 1);
        if (codedDiscounts.get(discount) >= discount.getRepeat()) {
            codedDiscounts.remove(discount);
        }
    }

    public boolean cartHasPair(Pair<Product, Seller> pair) {
        return newBuyerCart.containsKey(pair);
    }

    public void addDiscountCode(CodedDiscount discount) {
        codedDiscounts.put(discount, 0);
    }

    public void makeSpecialCoddedDiscount() {
        CodedDiscount codedDiscount = new CodedDiscount(new Date(), Date.from(ZonedDateTime.now().plusMonths(1).toInstant()), 10, 3);
        this.addDiscountCode(codedDiscount);
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

    public static void saveFields() {
        saveAllCodedDiscounts();
        saveAllBuyOrders();
        saveAllBuyerCarts();
    }

    private void saveBuyerCart() {
        newBuyerCart = null;
    }

    public static void loadFields() {
        loadAllCodedDiscounts();
        loadAllBuyOrders();
    }

    public static ArrayList<Buyer> getBuyers() {
        ArrayList<Buyer> buyers = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getUserType() == UserType.BUYER) {
                buyers.add((Buyer) user);
            }
        }
        return buyers;
    }

    public void codedDiscountsLoad() {
        HashMap<CodedDiscount, Integer> codedDiscountsFromDataBase = new HashMap<>();
        for (String codedDiscountId : codedDiscountsId.keySet()) {
            codedDiscountsFromDataBase.put(CodedDiscount.getDiscountById(codedDiscountId), codedDiscountsId.get(codedDiscountId));
        }
        this.codedDiscounts = codedDiscountsFromDataBase;
    }

    public void codedDiscountsSave() {
        codedDiscountsId.clear();
        for (CodedDiscount codedDiscount : codedDiscounts.keySet()) {
            codedDiscountsId.put(codedDiscount.getDiscountCode(), codedDiscounts.get(codedDiscount));
        }
        codedDiscounts = null;
    }

    public static void loadAllCodedDiscounts() {
        for (Buyer buyer : getBuyers()) {
            buyer.codedDiscountsLoad();
        }
    }

    public static void saveAllBuyerCarts() {
        for (Buyer buyer : getBuyers()) {
            buyer.saveBuyerCart();
        }
    }

    public static void saveAllCodedDiscounts() {
        for (Buyer buyer : getBuyers()) {
            buyer.codedDiscountsSave();
        }
    }

    public static void saveAllBuyOrders() {
        for (Buyer buyer : getBuyers()) {
            buyer.buyOrdersSave();
        }
    }

    public void buyOrdersSave() {
        buyOrdersId.clear();
        for (BuyOrder buyOrder : orders) {
            buyOrdersId.add(buyOrder.getOrderId());
        }
        orders = null;
    }

    public static void loadAllBuyOrders() {
        for (Buyer buyer : getBuyers()) {
            buyer.buyOrdersLoad();
        }
    }

    public void buyOrdersLoad() {
        ArrayList<BuyOrder> ordersFromDataBase = new ArrayList<>();
        for (String orderId : buyOrdersId) {
            ordersFromDataBase.add((BuyOrder) Order.getOrderById(orderId));
        }
        orders = ordersFromDataBase;
    }

}
