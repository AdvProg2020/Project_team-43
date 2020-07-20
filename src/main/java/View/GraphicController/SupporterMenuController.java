package View.GraphicController;

import com.jfoenix.controls.JFXListView;
import controller.client.BossProcessor;
import controller.client.Processor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    public JFXListView onlineUsersListView;
    public TextArea textMessage;
    public TextArea globalTextMessage;
    public VBox privateChatBox;
    public VBox globalChatBox;
    public ScrollPane scrollPane21;
    public ScrollPane scrollPane2;
    public ObservableList<String> users;
    public ObservableList<String> onlineUsers;
    public User selectedUser;

    public SupporterMenuController() {
        bossProcessor = BossProcessor.getInstance();
        users = FXCollections.observableArrayList();
        onlineUsers = FXCollections.observableArrayList();
    }

    public void sendMessage() {
        String userName = "alireza";
        String message = textMessage.getText().trim();
        if (message.equals("")) return;
        updateChatRoom(user.getUsername(), message, privateChatBox);
        textMessage.clear();
        scrollPane21.vvalueProperty().bind(privateChatBox.heightProperty());
        client.sendMessage(user, userName, message);
    }

    public void globalSendMessage() {
        String message = globalTextMessage.getText().trim();
        if (message.equals("")) return;
        //todo server
        updateChatRoom(user.getUsername(), message, globalChatBox);
        globalTextMessage.clear();
        scrollPane2.vvalueProperty().bind(globalChatBox.heightProperty());
    }

    public void update() {
        Music.getInstance().confirmation();
        client.updateUser(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText(), user);
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        bossProcessor.editField(userPersonalInfo);
    }


    public void chatWithUser() {
        String userName = usersListView.getSelectionModel().getSelectedItem().toString();
        init();
        selectedUser = User.getUserByUserName(userName);
        if (selectedUser == null) return;
        setChatRoomPrivate(selectedUser);
    }

    private void setChatRoomPrivate(User user) {
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
        updateUsersListView();
        updateOnlineUserListView();
    }

    public void updateUsersListView() {
        init();
        for (String userName : ((Supporter) user).getUsers().keySet()) {
            Text text = new Text(userName);
            text.setFont(new Font("Monospaced", 10));
        }
        usersListView.setItems(users);

    }

    public void updateOnlineUserListView() {
        init();
        for (User user : User.allUsers) {
            //todo check is online or not

        }

        onlineUsersListView.setItems(onlineUsers);
    }

    private void init() {
        client.fuckThread();
        User.setAllUsers(client.getAllUsers());
        if (!client.threadIsNull())
            client.acknowledge(this, privateChatBox);
    }

    public void browsePhotoUser() throws IOException {
        browsePhotoUser(user, profilePhoto);
    }

    public void open() {
        Music.getInstance().open();
    }

    public void setOnline() {
        client.setUserOnline(user);
        ((Supporter) user).setOnline(!((Supporter) user).isOnline());
        if (((Supporter) user).isOnline()) {
            client.acknowledge(this, privateChatBox);
        } else {
            client.fuckThread();
        }
    }
}
