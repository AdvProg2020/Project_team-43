package model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Buyer extends User {
    //TODO
    private ArrayList<CodedDiscount> discounts;
    private ArrayList<Integer> repeatOfDiscountCode;
    private ArrayList<Product> cart;
    private HashMap<Product, Integer> buyerCart;
    public HashMap<Product, Integer> getBuyerCart() {
        return buyerCart;
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    private ArrayList<BuyOrder> orders;

    public Buyer(String username, UserPersonalInfo userPersonalInfo) {
        super(username, userPersonalInfo);
        discounts = new ArrayList<CodedDiscount>();
        repeatOfDiscountCode= new ArrayList<Integer>();
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

    public void editFields(String field) {

    }

    //////////viewCart??????


    public ArrayList<BuyOrder> getOrders() {
        return orders;
    }

    public void showProducts() {

    }

    public void viewProduct(String productId) {

    }

    public void increaseCart(String productId) {
        buyerCart.replace(Product.getProductById(productId),
                buyerCart.get(Product.getProductById(productId)),
                buyerCart.get(Product.getProductById(productId)) + 1);
    }

    public void decreaseCart(String productId) {
        if (buyerCart.get(Product.getProductById(productId)) == 1) {
            buyerCart.remove(Product.getProductById(productId));
        } else {
            buyerCart.replace(Product.getProductById(productId),
                    buyerCart.get(Product.getProductById(productId)),
                    buyerCart.get(Product.getProductById(productId)) - 1);
        }
    }

    public double getCartPrice() {
        double price=0;
        for (Product product : buyerCart.keySet()){
            price+=buyerCart.get(product)*product.getPrice();
        }
        return price;
    }

    public void purchase() {
        BuyOrder buyOrder = new BuyOrder(UUID.randomUUID().toString(),new Date(),
                this.getCartPrice(),buyerCart,this.getSellerOfCartProducts());
        this.orders.add(buyOrder);
        this.buyerCart.clear();
    }
    public void viewOrders() {

    }//////View logs

    public void showOrder(String orderId) {

    }

    public void rateProduct(String productId, int score) {

    }

    public void viewBalance() {

    }

    public void viewDiscounts() {

    }

    public static void addBuyer(UserPersonalInfo personalInfo, String username) {
        new Buyer(username, personalInfo);
    }
    public boolean checkDiscountCode(CodedDiscount discount){
        if (!this.discounts.contains(discount))
            return false;
        if (!discount.checkTime())
            return false;
        this.repeatOfDiscountCode.set(discounts.indexOf(discount),
                this.repeatOfDiscountCode.get(discounts.indexOf(discount))-1);
        if (this.repeatOfDiscountCode.get(discounts.indexOf(discount))==discount.getRepeat()){
            this.repeatOfDiscountCode.remove(discounts.indexOf(discount));
            this.discounts.remove(discount);
        }
        return true;

    }
    public void addDiscountCode(CodedDiscount discount){
        this.discounts.add(discount);
        this.repeatOfDiscountCode.add(0);
    }
    public ArrayList<Seller> getSellerOfCartProducts(){
        ArrayList<Seller> sellers= new ArrayList<>();
        for (Product product : buyerCart.keySet()) {
            sellers.add(product.getSeller());
        }
        return sellers;

    }

}
