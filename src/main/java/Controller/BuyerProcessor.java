package Controller;

import View.BuyerShowAndCatch;
import javafx.util.Pair;
import model.*;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyerProcessor extends Processor {
    private static BuyerProcessor instance = new BuyerProcessor();

    public static BuyerProcessor getInstance() {
        return instance;
    }

    private static HashMap<Product, Integer> buyerCart = new HashMap<>();
    private static HashMap<Pair<Product, Seller>, Integer> newBuyerCart = new HashMap<Pair<Product, Seller>, Integer>();
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
        if (buyerCart.containsKey(product)) {
            buyerCart.replace(product, buyerCart.get(product), buyerCart.get(product) + 1);
        } else {
            buyerCart.put(product, 1);
        }
        product.setAvailableCount(product.getAvailableCount() - 1);
        if (user != null)
            setBuyerCart();
    }

    public void addToBuyerCart(Pair<Product, Seller> productSellerPair) {
        if (newBuyerCart.containsKey(productSellerPair)) {
            newBuyerCart.replace(productSellerPair,
                    newBuyerCart.get(productSellerPair), newBuyerCart.get(productSellerPair) + 1);
        } else {
            newBuyerCart.put(productSellerPair, 1);
        }
        productSellerPair.getKey().setAvailableCount(productSellerPair.getKey().getAvailableCount() - 1);
        if (user != null)
            setNewBuyerCart();
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

    public void showOrder(String orderId) throws NullPointerException {
        BuyOrder order = (BuyOrder) Order.getOrderById(orderId);
        if (order == null) {
            throw new NullPointerException("order with this Id doesn't exist");
        }
        buyerViewManager.showBuyOrder(order);
    }

    public void rateProduct(String productId, int score) throws NullPointerException {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new NullPointerException("product with this Id doesn't exist");
        }
        product.rateProduct(score);
    }

    public void viewBalance() {
        viewManager.showBalance(user);
    }

    public void viewBuyerDiscountCodes() {
        ArrayList<CodedDiscount> discounts = ((Buyer) user).getDiscounts();
        buyerViewManager.viewDiscountCodes(discounts);
    }

    public void showProductsInCart() {
        buyerViewManager.showProductsInCart(((Buyer) user).getNewBuyerCart());
    }

    public void increaseProduct(String productId) throws NullPointerException {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new NullPointerException("product with this Id doesn't exist");
        } else if (product.getAvailableCount() > 0) {
            ((Buyer) user).increaseCart(product);
            product.setAvailableCount(product.getAvailableCount() - 1);
        } else
            throw new NullPointerException("the product is not available");
    }

    public void increaseProduct(String productId, String sellerName) throws NullPointerException {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new NullPointerException("product with this Id doesn't exist");
        } else if (product.getAvailableCount() > 0) {
            ((Buyer) user).increaseCart(product, (Seller) User.getUserByUserName(sellerName));
            product.setAvailableCount(product.getAvailableCount() - 1);
        } else
            throw new NullPointerException("the product is not available");
    }

    public void decreaseProduct(String productId) {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new NullPointerException("product with this Id doesn't exist");
        }
        ((Buyer) user).decreaseCart(product);
        product.setAvailableCount(product.getAvailableCount() + 1);
    }

    public void decreaseProduct(String productId, String sellerName) {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new NullPointerException("product with this Id doesn't exist");
        }
        ((Buyer) user).decreaseCart(product, (Seller) User.getUserByUserName(sellerName));
        product.setAvailableCount(product.getAvailableCount() + 1);
    }

    public double showTotalPrice() {
        return ((Buyer) user).getNewCartPrice();
    }

    public boolean checkDiscountCode(String code) throws NullPointerException {
        if (!CodedDiscount.isCodedDiscountWithThisCode(code))
            return false;
        return ((Buyer) user).checkDiscountCode(CodedDiscount.getDiscountById(code));
    }

    public String payment(String address, String phoneNumber, double discount) {
        if (user.getBalance() < ((Buyer) user).getNewCartPrice())
            return "insufficient money";
        ((Buyer) user).purchase();
        newBuyerCart.clear();
        return "payment done";
    }

    public void setBuyerCart() {
        ((Buyer) user).setBuyerCart(buyerCart);
    }

    public void setNewBuyerCart() {
        ((Buyer) user).setNewBuyerCart(newBuyerCart);
    }

    public void logout() {
        user = null;
        isLogin = false;
        newBuyerCart.clear();
    }
}
