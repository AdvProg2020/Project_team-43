package View.GraphicController;

import com.jfoenix.controls.JFXListView;
import controller.client.BuyerProcessor;
import View.graphic.*;
import controller.client.Processor;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;
import model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyerMenuController extends Controller {
    private static boolean isBack = false;
    public ListView<String> products;
    public Text userName;
    public Text totalPrice;
    public ListView<String> discountCodes;
    public ListView<String> orders;
    public ListView<String> order;
    public ImageView closeButton;
    public Tab cartTab;
    public TabPane tabPane;
    public Label balance;
    BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField phoneNumber;
    public ImageView profilePhoto;
    public ImageView closeDiscountButton;
    public ListView<String> discountCodeFeatures;
    private Buyer user;
    public JFXListView supportersListView;
    public JFXListView onlineUsersListView;
    public TextArea textMessage;
    public TextArea globalTextMessage;
    public VBox privateChatBox;
    public VBox globalChatBox;
    public ScrollPane scrollPane21;
    public ScrollPane scrollPane2;
    public Supporter selectedSupporter;
    public ObservableList<String> users;
    public ObservableList<String> onlineUsers;


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
        balance.setText(Double.toString(user.getBalance()));
        setUserImage(user, profilePhoto);
        setCart();
        setDiscountCodes();
        setOrders();
        if (isBack) {
            isBack = false;
            tabPane.getSelectionModel().select(cartTab);
        }
    }

    public void setCart() {
        HashMap<Pair<Product, Seller>, Integer> cart = user.getNewBuyerCart();
        for (Pair<Product, Seller> productSellerPair : cart.keySet()) {
            setCartCells(productSellerPair);
        }
        products.setCellFactory(param -> new XCell(user));
        setTotalPrice(Double.toString(user.getNewCartPrice()));
    }

    public void setDiscountCodes() {
        for (CodedDiscount discount : user.getDiscounts()) {
            discountCodes.getItems().add("Code: " + discount.getDiscountCode() + "   Amount: " + discount.getDiscountAmount() + "   Remain : " + user.remainRepeats(discount));
        }
    }

    public void setOrders() {
        order.setVisible(false);
        closeButton.setVisible(false);
        for (BuyOrder order : user.getOrders()) {
            orders.getItems().add("id:\t" + order.getOrderId() + "\t" + order.getPayment());
        }
    }

    public void setTotalPrice(String text) {
        this.totalPrice.setText(text);
    }

    public void update() {
        Music.getInstance().confirmation();
        client.updateUser(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText(), Processor.user);
        buyerProcessor.editField(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());

    }

    public void browsePhotoUser() throws IOException {
        browsePhotoUser(user, profilePhoto);
    }

    public void setCartCells(Pair<Product, Seller> productSellerPair) {
        products.getItems().add(productSellerPair.getKey().getName() + "\t" +
                productSellerPair.getValue().getUsername() + "\t" + user.getNewBuyerCart().get(productSellerPair) + "\t" + productSellerPair.getKey().getPrice() + "\t" +
                productSellerPair.getKey().getPrice() * user.getNewBuyerCart().get(productSellerPair) + "\t");

    }

    public void showOrder() {
        order.getItems().clear();
        BuyOrder buyOrder = null;
        String orderId = orders.getSelectionModel().getSelectedItem().split("\t")[1];
        for (BuyOrder userOrder : user.getOrders()) {
            if (userOrder.getOrderId().equals(orderId))
                buyOrder = userOrder;

        }
        HashMap<Product, Integer> products = buyOrder.getProducts();
        int i = 0;
        for (Product product : products.keySet()) {
            order.getItems().add(product.getName() + " " + products.get(product) + " " + buyOrder.getSellers().get(i).getUsername());
            i++;
        }
        order.getItems().add(Double.toString(buyOrder.getPayment()));
        order.getItems().add(buyOrder.getDeliveryStatus().toString());
        order.getItems().add(buyOrder.getDate().toString());
        order.getItems().add(buyOrder.getAddress());
        order.setVisible(true);
        closeDiscountButton.setVisible(true);

    }

    public void closeOrder() {
        Music.getInstance().close();
        order.setVisible(false);
        closeButton.setVisible(false);
    }

    public void purchase() {
        Music.getInstance().confirmation();
        try {
            isBack = true;
            PurchaseWindow.getInstance().start(this.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProduct(MouseEvent mouseEvent) {
        Music.getInstance().open();
        if (mouseEvent.getClickCount() >= 2) {
            isBack = true;
            String productName = products.getSelectionModel().getSelectedItem().split("\t")[0];
            String sellerName = products.getSelectionModel().getSelectedItem().split("\t")[1];
            for (Pair<Product, Seller> productSellerPair : ((Buyer) buyerProcessor.getUser()).getNewBuyerCart().keySet()) {
                if (productSellerPair.getKey().getName().equals(productName) && productSellerPair.getValue().getUsername().equals(sellerName)) {
                    ProductWindow.getInstance().setProduct(productSellerPair.getKey(), BuyerUserWindow.getInstance());
                    ProductWindow.getInstance().start(MainWindow.getInstance().getStage());
                }
            }
        }

    }

    public void showCodedDiscount() {
        discountCodeFeatures.getItems().clear();
        String discountId = discountCodes.getSelectionModel().getSelectedItem().split(" ")[1];
        CodedDiscount codedDiscount = null;
        for (CodedDiscount userDiscount : user.getDiscounts()) {
            if (userDiscount.getDiscountCode().equals(discountId))
                codedDiscount = userDiscount;
        }
        discountCodeFeatures.getItems().add("Amount: " + codedDiscount.getDiscountAmount());
        discountCodeFeatures.getItems().add("Repeat: " + user.remainRepeats(codedDiscount));
        discountCodeFeatures.getItems().add("Start Time: " + codedDiscount.getStartTime());
        discountCodeFeatures.getItems().add("End Time: " + codedDiscount.getEndTime());

        discountCodeFeatures.setVisible(true);
        closeDiscountButton.setVisible(true);
    }

    public void closeDiscountCodeFeatures() {
        discountCodeFeatures.setVisible(false);
        closeDiscountButton.setVisible(false);
    }

    public void increaseBalance() {
        BankForChargeWindow.getInstance().start(MainWindow.getInstance().getStage());
    }


    private class XCell extends ListCell<String> {
        Buyer buyer;
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button addButton = new Button("+");
        Button removeButton = new Button("-");
        ImageView imageView = new ImageView();

        public XCell(Buyer buyer) {
            super();
            this.buyer = buyer;

            hbox.getChildren().addAll(label, imageView, pane, addButton, removeButton);
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
                Pair<Product, Seller> pairForLabel = null;
                for (Pair<Product, Seller> pair : buyer.getNewBuyerCart().keySet()) {
                    if (pair.getKey().getName().equalsIgnoreCase(item.split("\t")[0])) {
                        pairForLabel = pair;
                    }
                }
                setProductImage(pairForLabel.getKey(), imageView, 70, 100);
                setLabel(pairForLabel);
                setGraphic(hbox);
            }
        }


        private void addRemoveItem(String item, boolean add) {
            String productName = item.split("\t")[0];
            String sellerName = item.split("\t")[1];
            HashMap<Pair<Product, Seller>, Integer> cart = buyer.getNewBuyerCart();
            for (Pair<Product, Seller> productSellerPair : cart.keySet()) {
                if (productSellerPair.getKey().getName().equals(productName) &&
                        productSellerPair.getValue().getUsername().equals(sellerName)) {
                    if (add) {
                        BuyerProcessor.getInstance().newIncreaseProduct(productSellerPair.getKey(), productSellerPair.getValue());
                    } else {
                        BuyerProcessor.getInstance().newDecreaseProduct(productSellerPair.getKey(),
                                productSellerPair.getValue());
                    }
                    setLabel(productSellerPair);
                    setTotalPrice(Double.toString(user.getNewCartPrice()));
                    break;
                }
            }
        }

        private void setLabel(Pair<Product, Seller> productSellerPair) {
            if (buyer.getNewBuyerCart().containsKey(productSellerPair))
                label.setText(productSellerPair.getKey().getName() + "\t" + productSellerPair.getValue().getUsername() + "\t" +
                        buyer.getNewBuyerCart().get(productSellerPair) + "\t" + productSellerPair.getKey().getPrice() + "\t" +
                        productSellerPair.getKey().getPrice() * buyer.getNewBuyerCart().get(productSellerPair) + "\t");
            else
                getListView().getItems().remove(getItem());
        }
    }

    public void sendMessage() {
        String message = textMessage.getText().trim();
        if(message.equals(""))return;
        //todo server
        updateChatRoom(user.getUsername(), message, privateChatBox);
        textMessage.clear();
        scrollPane21.vvalueProperty().bind(privateChatBox.heightProperty());
    }

    public void globalSendMessage() {
        String message = globalTextMessage.getText().trim();
        if (message.equals("")) return;
        //todo server
        updateChatRoom(user.getUsername(), message, globalChatBox);
        globalTextMessage.clear();
        scrollPane2.vvalueProperty().bind(globalChatBox.heightProperty());
    }

    public void updateChatRoom(String username, String message, VBox vbox) {
        if (message.equals("")) return;
        Text text = new Text(message);
        text.setFill(Color.WHITE);
        text.getStyleClass().add("message");
        TextFlow tempFlow = new TextFlow();
        if (!user.getUsername().equals(username)) {
            Text txtName = new Text(username + "\n");
            txtName.getStyleClass().add("txtName");
            tempFlow.getChildren().add(txtName);
        }

        tempFlow.getChildren().add(text);
        tempFlow.setMaxWidth(200);

        TextFlow flow = new TextFlow(tempFlow);

        HBox hbox = new HBox(12);

        Circle img = new Circle(32, 32, 16);
        /*try {
            String path = new File(("sadra.jpg")).toURI().toString();
            img.setFill(new ImagePattern(new Image(path)));
        } catch (Exception ex) {
            String path = new File("sadra.jpg").toURI().toString();
            img.setFill(new ImagePattern(new Image(path)));
        }/*/

//        img.getStyleClass().add("imageView");

        if (!user.getUsername().equals(username)) {

            tempFlow.getStyleClass().add("tempFlowFlipped");
            flow.getStyleClass().add("textFlowFlipped");
            privateChatBox.setAlignment(Pos.TOP_LEFT);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.getChildren().add(img);
            hbox.getChildren().add(flow);

        } else {
            text.setFill(Color.WHITE);
            tempFlow.getStyleClass().add("tempFlow");
            flow.getStyleClass().add("textFlow");
            hbox.setAlignment(Pos.BOTTOM_RIGHT);
            hbox.getChildren().add(flow);
            hbox.getChildren().add(img);
        }

        hbox.getStyleClass().add("hbox");
        Platform.runLater(() -> vbox.getChildren().addAll(hbox));
    }

    public void chatWithUser() {
        String userName = supportersListView.getSelectionModel().getSelectedItem().toString();
        init();
        selectedSupporter = (Supporter)User.getUserByUserName(userName);
        if (selectedSupporter == null) return;
        setChatRoomPrivate(selectedSupporter);
    }
    private void init() {
        User.setAllUsers(client.getAllUsers());
    }

    public void setChatRoomPrivate(User user) {
        //todo get supporter.map from server
        privateChatBox.getChildren().clear();
        Pattern pattern = Pattern.compile("(.+) : (.*)");
        for (String message : ((Supporter) user).getUsers().get(user.getUsername())) {
            Matcher matcher = pattern.matcher(message);
            if (matcher.matches()) {
                updateChatRoom(matcher.group(1), matcher.group(2), privateChatBox);
            }
        }
    }
    public void open() {
        Music.getInstance().open();
    }

}
