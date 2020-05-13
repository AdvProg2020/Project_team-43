package model;

import javafx.util.Pair;

import java.util.*;

public class Buyer extends User {
    //TODO
    //private ArrayList<CodedDiscount> discounts;
    //private ArrayList<Integer> repeatOfDiscountCode;
    private HashMap<CodedDiscount, Integer> codedDiscounts;
    private ArrayList<Product> cart;
    private HashMap<Product, Integer> buyerCart;
    private HashMap<Pair<Product, Seller>, Integer> newBuyerCart = new HashMap<Pair<Product, Seller>, Integer>();


    private ArrayList<BuyOrder> orders;

    public Buyer(String username, UserPersonalInfo userPersonalInfo) {
        super(username, userPersonalInfo);
        codedDiscounts = new HashMap<CodedDiscount, Integer>();
        cart = new ArrayList<Product>();
        orders = new ArrayList<BuyOrder>();
        buyerCart = new HashMap<>();
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


    public void increaseCart(Product product) {
        buyerCart.replace(product,
                buyerCart.get(product),
                buyerCart.get(product) + 1);

    }

    public void increaseCart(Product product, Seller seller) {
        Pair<Product, Seller> productSellerPair = new Pair<>(product, seller);
        newBuyerCart.replace(productSellerPair,
                newBuyerCart.get(productSellerPair), newBuyerCart.get(productSellerPair) + 1);

    }

    public void decreaseCart(Product product) {

        if (buyerCart.get(product) == 1) {
            buyerCart.remove(product);

        } else {
            buyerCart.replace(product,
                    buyerCart.get(product),
                    buyerCart.get(product) - 1);
        }

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

    public void purchase(double discount) {
        HashMap<Product, Integer> order = new HashMap<>();
        for (Pair<Product, Seller> productSellerPair : newBuyerCart.keySet()) {
            order.put(productSellerPair.getKey(), newBuyerCart.get(productSellerPair));
        }
        BuyOrder buyOrder = new BuyOrder( new Date(),
                this.getNewCartPrice(), discount, order, this.getSellerOfCartProducts());
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
            SellOrder sellOrder = new SellOrder( discount, new Date(),
                    product.getPrice(), product, this);
            seller.addOrder(sellOrder);
        }
    }


    public ArrayList<CodedDiscount> getDiscounts() {
        ArrayList<CodedDiscount> discounts = new ArrayList<>();
        discounts.addAll(codedDiscounts.keySet());
        return discounts;
    }

    public void setBuyerCart(HashMap<Product, Integer> buyerCart) {
        this.buyerCart = buyerCart;
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
        for (Product product : buyerCart.keySet()) {
            sellers.add(product.getSeller());
        }
        return sellers;

    }

}
