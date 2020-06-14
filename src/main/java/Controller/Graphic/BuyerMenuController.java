package Controller.Graphic;

import Controller.console.BuyerProcessor;
import View.graphic.PurchaseWindow;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import model.*;

import java.io.File;
import java.util.HashMap;

public class BuyerMenuController extends Controller {
    public ListView<String> products;
    public Text userName;
    public Text totalPrice;
    public ListView<String> discountCodes;
    public ListView<String> orders;
    public ListView<String> order;
    public ImageView closeButton;
    BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField phoneNumber;
    public ImageView profilePhoto;
    private Buyer user;

    @FXML
    public void initialize() {
        user = (Buyer) buyerProcessor.getUser();
        userName.setText(user.getUsername());
        UserPersonalInfo userPersonalInfo = user.getUserPersonalInfo();
        firstName.setText(userPersonalInfo.getFirstName());
        lastName.setText(userPersonalInfo.getLastName());
        email.setText(userPersonalInfo.getEmail());
        password.setText(userPersonalInfo.getPassword());
        phoneNumber.setText(userPersonalInfo.getPhoneNumber());
        if (user.getImagePath() != null) {
            profilePhoto.setImage(new Image("file:" + user.getImagePath()));
        }
        setCart();
        setDiscountCodes();
        setOrders();
    }

    public void setCart() {
        buyerProcessor.addToBuyerCart(new Pair<Product, Seller>(Product.getProductById("1"),
                Product.getProductById("1").getSellers().get(0)));
        HashMap<Pair<Product, Seller>, Integer> cart = user.getNewBuyerCart();
        for (Pair<Product, Seller> productSellerPair : cart.keySet()) {
            setCartCells(productSellerPair);
        }
        products.setCellFactory(param -> new XCell(user));
        setTotalPrice(Double.toString(user.getNewCartPrice()));
    }

    public void setDiscountCodes() {
        for (CodedDiscount discount : user.getDiscounts()) {
            discountCodes.getItems().add(discount.toString() + "remain : " + user.remainRepeats(discount));
        }
    }

    public void setOrders() {
        order.setVisible(false);
        closeButton.setVisible(false);
        for (BuyOrder order : user.getOrders()) {
            orders.getItems().add(order.getOrderId() + " " + order.getPayment());
        }
    }

    public void setTotalPrice(String text) {
        this.totalPrice.setText(text);
    }

    public void update() {
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        buyerProcessor.editField(userPersonalInfo);
    }

    public void browsePhotoUser() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            user.setImagePath(file.getAbsolutePath());
            profilePhoto.setImage(new Image(file.toURI().toString()));
        }
    }

    public void setCartCells(Pair<Product, Seller> productSellerPair) {
        products.getItems().add(productSellerPair.getKey().getName() + "\t" +
                productSellerPair.getValue().getUsername() + "\t" + user.getNewBuyerCart().get(productSellerPair) + "\t" + productSellerPair.getKey().getPrice() + "\t" +
                productSellerPair.getKey().getPrice() * user.getNewBuyerCart().get(productSellerPair));

    }

    public void showOrder(MouseEvent mouseEvent) {
        String orderId = orders.getSelectionModel().getSelectedItem().split(" ")[0];
        BuyOrder buyOrder = (BuyOrder) BuyOrder.getOrderById(orderId);
        HashMap<Product, Integer> products = buyOrder.getProducts();
        for (Product product : products.keySet()) {
            order.getItems().add(product.getName() + " " + products.get(product));
        }
        order.getItems().add(Double.toString(buyOrder.getPayment()));
        order.setVisible(true);
        closeButton.setVisible(true);

    }

    public void closeOrder(MouseEvent mouseEvent) {
        order.setVisible(false);
        closeButton.setVisible(false);

    }

    public void purchase(MouseEvent mouseEvent) {
        try {
            PurchaseWindow.getInstance().start(this.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class XCell extends ListCell<String> {
        Buyer buyer;
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button addButton = new Button("+");
        Button removeButton = new Button("-");

        public XCell(Buyer buyer) {
            super();
            this.buyer = buyer;
            hbox.getChildren().addAll(label, pane, addButton, removeButton);
            HBox.setHgrow(pane, Priority.ALWAYS);
            addButton.setOnAction(event -> addRemoveItem(getItem(), true));
            removeButton.setOnAction(event -> addRemoveItem(getItem(), false));
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);
            if (item != null && !empty) {
                label.setText(item);
                setGraphic(hbox);
            }
        }


        private void addRemoveItem(String item, boolean add) {
            String productName = item.split("\t")[0];
            String sellerName = item.split("\t")[1];
            for (Pair<Product, Seller> productSellerPair : buyer.getNewBuyerCart().keySet()) {
                if (productSellerPair.getKey().getName().equals(productName) &&
                        productSellerPair.getValue().getUsername().equals(sellerName)) {
                    if (add) {
                        BuyerProcessor.getInstance().increaseProduct(productSellerPair.getKey().getProductId(),
                                productSellerPair.getValue().getUsername());
                    } else {
                        BuyerProcessor.getInstance().decreaseProduct(productSellerPair.getKey().getProductId(),
                                productSellerPair.getValue().getUsername());
                    }
                    setLabel(productSellerPair);
                }
                setTotalPrice(Double.toString(user.getNewCartPrice()));

            }
        }

        private void setLabel(Pair<Product, Seller> productSellerPair) {
            if (buyer.getNewBuyerCart().containsKey(productSellerPair))
                label.setText(productSellerPair.getKey().getName() + "\t" + productSellerPair.getValue().getUsername() + "\t" +
                        buyer.getNewBuyerCart().get(productSellerPair) + "\t" + productSellerPair.getKey().getPrice() + "\t" +
                        productSellerPair.getKey().getPrice() * buyer.getNewBuyerCart().get(productSellerPair));
            else
                getListView().getItems().remove(getItem());
        }
    }
}
