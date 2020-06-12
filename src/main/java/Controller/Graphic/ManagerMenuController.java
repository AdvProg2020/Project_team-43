package Controller.Graphic;

import Controller.console.BossProcessor;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Product;
import model.Seller;
import model.User;
import model.UserPersonalInfo;

public class ManagerMenuController extends Controller {
    BossProcessor bossProcessor = BossProcessor.getInstance();
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField phoneNumber;
    public Text userName2;
    public Text firstName2;
    public Text lastName2;
    public Text email2;
    public Text phoneNumber2;
    public Text productName;
    public Text productPrice;
    public Text productScore;
    public TextField userNameCreateManager;
    public TextField firstNameCreateManager;
    public TextField lastNameCreateManager;
    public TextField emailCreateManager;
    public TextField passwordCreateManager;
    public TextField phoneCreateManager;
    public ListView usersListView;
    public ListView productsListView;
    ObservableList<String> users;
    ObservableList<String> products;
    public Pane userInfoPane;
    public Pane productInfoPane;

    public void showUserInfo() {
        String userName = usersListView.getSelectionModel().getSelectedItem().toString();
        showUser(User.getUserByUserName(userName));
    }

    public void showUser(User user) {
        userName2.setText(user.getUsername());
        firstName2.setText(user.getUserPersonalInfo().getFirstName());
        lastName2.setText(user.getUserPersonalInfo().getLastName());
        email2.setText(user.getUserPersonalInfo().getEmail());
        phoneNumber2.setText(user.getUserPersonalInfo().getPhoneNumber());
        userInfoPane.setVisible(true);

    }

    public void showProductInfo() {
        String productNameAndId = productsListView.getSelectionModel().getSelectedItems().toString();
        String productId = productNameAndId.split(" / ")[1];
        Product product = Product.getProductById(productId);
        showProduct(product);
    }

    public void showProduct(Product product) {
        productName.setText(product.getName());
        productPrice.setText("" + product.getPrice());
        productScore.setText("" + product.getScore());
        productInfoPane.setVisible(true);
    }

    public void createManagerProfile() {

    }

    public void closeProductInfo(){
        productInfoPane.setVisible(false);
    }

    public void closeUserInfo() {
        userInfoPane.setVisible(false);
    }

    public void deleteUser() {

    }

    @FXML
    public void initialize() {
        UserPersonalInfo userPersonalInfo = bossProcessor.getUser().getUserPersonalInfo();
        firstName.setText(userPersonalInfo.getFirstName());
        lastName.setText(userPersonalInfo.getLastName());
        email.setText(userPersonalInfo.getEmail());
        password.setText(userPersonalInfo.getPassword());
        phoneNumber.setText(userPersonalInfo.getPhoneNumber());
        for (User user : User.allUsers) {
            users.add(user.getUsername());
        }
        usersListView.setItems(users);
        for (Product product : Product.allProductsInList) {
            products.add(product.getName() + " / " + product.getProductId());
        }
        productsListView.setItems(products);
    }

    /*public void update(ActionEvent actionEvent) {
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        bossProcessor.editField(userPersonalInfo);
    }*/
}
