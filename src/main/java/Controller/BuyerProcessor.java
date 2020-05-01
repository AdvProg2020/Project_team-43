package Controller;

import View.BuyerShowAndCatch;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyerProcessor extends Processor {
    private static BuyerProcessor instance = new BuyerProcessor();

    public static BuyerProcessor getInstance() {
        return instance;
    }

    private static HashMap<Product, Integer> buyerCart = new HashMap<Product, Integer>();
    private static BuyerShowAndCatch buyerViewManager = BuyerShowAndCatch.getInstance();

    private BuyerProcessor() {
    }

    public String editBuyerField(String command) throws InvalidCommandException {
        Pattern pattern = Pattern.compile("edit (\\S+)");
        Matcher matcher = pattern.matcher(command);
        if (command.equalsIgnoreCase("back"))
            return null;
        if (!matcher.find())
            throw new InvalidCommandException("invalid command");
        String field = matcher.group(1);
        String newField = buyerViewManager.getNewField(field);
        try {
            ((Buyer) user).editFields(field, newField);
            return (field + " successfully changed to " + newField);
        } catch (InvalidCommandException e) {
            return e.getMessage();
        }
    }

    public void addToBuyerCart(Product product) {
        buyerCart.put(product, 1);
    }

    public void viewOrders() {
        ArrayList<BuyOrder> orders = ((Buyer) user).getOrders();
        buyerViewManager.viewBuyerOrders(orders);
    }

    public void manageOrders(String command) throws InvalidCommandException {
        if (command.equals("back")) {
            return;
        }
        Pattern showOrderPattern = Pattern.compile("show order (.+)");
        Matcher showOrderMatcher = showOrderPattern.matcher(command);
        Pattern rateProductPattern = Pattern.compile("rate (.+) (\\d)");
        Matcher rateProductMatcher = rateProductPattern.matcher(command);
        if (showOrderMatcher.matches()) {
            try {
                this.showOrder(showOrderMatcher.group(1));
            } catch (NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else if (rateProductMatcher.matches()) {
            try {
                this.rateProduct(rateProductMatcher.group(1), Integer.parseInt(rateProductMatcher.group(2)));
            } catch (NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else {
            throw new InvalidCommandException("invalid command");
        }
    }

    public void showOrder(String orderId) throws NullPointerException{
        BuyOrder order = (BuyOrder) Order.getOrderById(orderId);
        if (order == null) {
            throw new NullPointerException("order with this Id doesn't exist");
        }
        buyerViewManager.showBuyOrder(order);
    }

    public void rateProduct(String productId, int score) throws NullPointerException{
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new NullPointerException("product with this Id doesn't exist");
        }
        product.rateProduct(score);
    }

    public void viewBalance() {
        viewManager.showBalance(user);
    }

    public void viewBuyerDiscountCodes(){
        ArrayList<CodedDiscount> discounts = ((Buyer) user).getDiscounts();
        buyerViewManager.viewDiscountCodes(discounts);
    }

    public void showProductsInCart() {
        buyerViewManager.showProductsInCart(((Buyer) user).getBuyerCart());
    }

    public void increaseProduct(String productId) throws NullPointerException{
        //TODO : error handling
        Product product = Product.getProductById(productId);
        if(product == null){
            throw new NullPointerException("product with this Id doesn't exist");
        }
        ((Buyer) user).increaseCart(product);
    }

    public void decreaseProduct(String productId) {
        Product product = Product.getProductById(productId);
        if(product == null){
            throw new NullPointerException("product with this Id doesn't exist");
        }
        ((Buyer) user).decreaseCart(product);
    }

    public double showTotalPrice() {
        return ((Buyer) user).getCartPrice();
    }

    public boolean checkDiscountCode(String code) throws NullPointerException{
        if (!CodedDiscount.isCodedDiscountWithThisCode(code))
            return false;
        return ((Buyer) user).checkDiscountCode(CodedDiscount.getDiscountById(code));
    }

    public String payment(String address, String phoneNumber, double discount) {
        if (user.getBalance() < ((Buyer) user).getCartPrice())
            return "insufficient money";
        ((Buyer) user).purchase();
        buyerCart.clear();
        return "payment done";
    }

    public void setBuyerCart() {
        ((Buyer) user).setBuyerCart(buyerCart);
    }

    public void logout() {
        user = null;
        isLogin = false;
        buyerCart.clear();
    }
}
