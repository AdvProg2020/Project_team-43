package Controller.console;

import View.BuyerShowAndCatch;
import javafx.util.Pair;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyerProcessor extends Processor {
    private final static BuyerProcessor instance = new BuyerProcessor();

    public static BuyerProcessor getInstance() {
        return instance;
    }

    private final static HashMap<Pair<Product, Seller>, Integer> newBuyerCart = new HashMap<Pair<Product, Seller>, Integer>();
    private final static BuyerShowAndCatch buyerViewManager = BuyerShowAndCatch.getInstance();

    private BuyerProcessor() {
    }

    public static HashMap<Pair<Product, Seller>, Integer> getNewBuyerCart() {
        return newBuyerCart;
    }

    public String editBuyerField(String command) {
        Pattern pattern = Pattern.compile("edit (\\S+)");
        Matcher matcher = pattern.matcher(command);
        if (command.equalsIgnoreCase("back"))
            return null;
        if (!matcher.find())
            errorMessage("invalid command");
        String field = matcher.group(1);
        String newField = buyerViewManager.getNewField(field);
        try {
            ((Buyer) user).editFields(field, newField);
            return (field + " successfully changed to " + newField);
        } catch (InvalidCommandException e) {
            return e.getMessage();
        }
    }

    public void addToBuyerCart(Pair<Product, Seller> productSellerPair) {
        if (newBuyerCart.containsKey(productSellerPair)) {
            newBuyerCart.replace(productSellerPair, newBuyerCart.get(productSellerPair), newBuyerCart.get(productSellerPair) + 1);
        } else {
            newBuyerCart.put(productSellerPair, 1);
        }
        productSellerPair.getValue().decreaseProduct(productSellerPair.getKey());
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
            errorMessage("invalid command");
        }
    }

    public void showOrder(String orderId) {
        BuyOrder order = (BuyOrder) Order.getOrderById(orderId);
        if (order == null) {
            errorMessage("order with this Id doesn't exist");
        }
        buyerViewManager.showBuyOrder(order);
    }

    public void rateProduct(String productId, int score) {
        Product product = Product.getProductById(productId);
        if (product == null) {
            errorMessage("product with this Id doesn't exist");
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

    public boolean checkProductAndSellerValidation(String productId, String sellerName) {
        if (!(User.hasUserWithUserName(sellerName) && User.getUserByUserName(sellerName) instanceof Seller)) {
            errorMessage("invalid seller name");
            return true;
        }
        if (!Product.hasProductWithId(productId)) {
            errorMessage("product with this Id doesn't exist");
            return true;
        }
        return !((Buyer) user).cartHasPair(new Pair<Product, Seller>(Product.getProductById(productId), (Seller) User.getUserByUserName(sellerName)));
    }

    public void increaseProduct(String productId, String sellerName) throws NullPointerException {
        if (checkProductAndSellerValidation(productId, sellerName))
            return;
        Product product = Product.getProductById(productId);
        Seller seller = (Seller) User.getUserByUserName(sellerName);
        if (Objects.requireNonNull(seller).isProductAvailable(product)) {
            ((Buyer) user).increaseCart(product, (Seller) User.getUserByUserName(sellerName));
            Objects.requireNonNull(product).setAvailableCount(product.getAvailableCount() - 1);
            seller.decreaseProduct(product);
        } else
            errorMessage("not available any more");
    }


    public void decreaseProduct(String productId, String sellerName) {
        if (checkProductAndSellerValidation(productId, sellerName))
            return;
        Product product = Product.getProductById(productId);
        Seller seller = (Seller) User.getUserByUserName(sellerName);
        ((Buyer) user).decreaseCart(product, (Seller) User.getUserByUserName(sellerName));
        Objects.requireNonNull(seller).increaseProduct(product);
        Objects.requireNonNull(product).setAvailableCount(product.getAvailableCount() + 1);
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
        if (user.getBalance() < ((Buyer) user).getNewCartPrice() * (100 - discount) / 100)
            return "insufficient money";
        ((Buyer) user).purchase(discount, address, phoneNumber);
        newBuyerCart.clear();
        return "payment done";
    }


    public void setNewBuyerCart() {
        ((Buyer) user).setNewBuyerCart(newBuyerCart);
    }

    public void logout() {
        user = null;
        isLogin = false;
        for (Pair<Product, Seller> productSellerPair : newBuyerCart.keySet()) {
            productSellerPair.getValue().increaseProduct(productSellerPair.getKey(),
                    newBuyerCart.get(productSellerPair));
        }
        newBuyerCart.clear();
    }

    public boolean isCartEmpty() {
        return newBuyerCart.size() == 0;
    }
}
