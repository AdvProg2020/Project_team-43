package View.GraphicController;

import com.jfoenix.controls.JFXListView;
import controller.client.BossProcessor;
import controller.client.Processor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.scene.text.TextFlow;
import model.*;

import java.io.File;
import java.io.IOException;


public class SupporterMenuController extends Controller {
    public BossProcessor bossProcessor;
    public User user;
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField phoneNumber;
    public ImageView profilePhoto;
    public Text userName;
    public JFXListView usersListView;
    public TextArea textMessage;
    public VBox chatBox;
    public ObservableList<String> users;
    public User selectedUser;

    public SupporterMenuController() {
        bossProcessor = BossProcessor.getInstance();
        users = FXCollections.observableArrayList();
    }

    public void sendMessage() {

    }

    public void update() {
        Music.getInstance().confirmation();
        client.updateUser(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText(), user);
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        bossProcessor.editField(userPersonalInfo);
    }

    public void emojiAction() {

    }

    public void chatWithUser() {
        String userName = usersListView.getSelectionModel().getSelectedItem().toString();
        init();
        selectedUser = User.getUserByUserName(userName);
        if (selectedUser == null) return;
        setChatRoom(selectedUser);
    }

    public void setChatRoom(User user) {

    }

    public void updateChatRoom(String username, String message) {
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
            chatBox.setAlignment(Pos.TOP_LEFT);
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
        Platform.runLater(() -> chatBox.getChildren().addAll(hbox));
    }

    public void initialize() {
        init();
        user = Processor.user;
        firstName.setPromptText(user.getUserPersonalInfo().getFirstName());
        lastName.setPromptText(user.getUserPersonalInfo().getLastName());
        email.setPromptText(user.getUserPersonalInfo().getEmail());
        password.setPromptText(user.getUserPersonalInfo().getPassword());
        phoneNumber.setPromptText(user.getUserPersonalInfo().getPhoneNumber());
        userName.setText(user.getUsername());
        setUserImage(user, profilePhoto);
        updateUsersVBox();
        updateChatRoom(user.getUsername(), "salam kos kesh");
        updateChatRoom("null", "salam bi namoos");
        updateChatRoom(user.getUsername(), "salam kos kesh");
        updateChatRoom("null", "salam bi namoos");
        updateChatRoom(user.getUsername(), "salam kos kesh");
        updateChatRoom("null", "salam bi namoos");
        updateChatRoom(user.getUsername(), "salam kos kesh");
        updateChatRoom("null", "salam bi namoos");updateChatRoom(user.getUsername(), "salam kos kesh");
        updateChatRoom("null", "salam bi namoos");
        updateChatRoom(user.getUsername(), "salam kos kesh");
        updateChatRoom("null", "salam bi namoos");updateChatRoom(user.getUsername(), "salam kos kesh");
        updateChatRoom("null", "salam bi namoos");




    }

    public void updateUsersVBox() {
        init();
        for (String userName : ((Supporter) user).getUsers().keySet()) {
            Text text = new Text(userName);
            text.setFont(new Font("Monospaced", 10));
        }
        users.add("mamad");
        users.add("mamad");
        users.add("mamad");
        usersListView.setItems(users);

    }

    private void init() {
        User.setAllUsers(client.getAllUsers());
    }

    public void browsePhotoUser() throws IOException {
        browsePhotoUser(user, profilePhoto);
    }

    public void open() {
        Music.getInstance().open();
    }

}
