package View.GraphicController;

import controller.client.BuyerProcessor;
import View.graphic.AddCommentWindow;
import View.graphic.MainWindow;
import View.graphic.ProductWindow;
import View.graphic.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;
import model.*;
import org.controlsfx.control.Rating;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class ProductWindowController extends Controller {
    public ImageView productImage;
    public Label productName;
    public Label productPrice;
    public ChoiceBox<String> sellers;
    public Label error;
    public Rating rating;
    public TableView<Map.Entry<String, String>> features;
    public TableColumn<Map.Entry<String, String>, String> feature;
    public TableColumn<Map.Entry<String, String>, String> value;
    public ListView<String> comments;
    public TextArea comment;
    private static boolean isBackToComment = false;
    public TabPane tabPane;
    public Tab tab;
    public ImageView ivTarget;
    public Label isOff;
    public Label timesRemain;
    public Rating productScore;
    public TextField secondProductId;
    public TableView<Pair<String, Pair<String, String>>> compareTable;
    public TableColumn<Pair<String, Pair<String, String>>, String> featureForCompare;
    public TableColumn<Pair<String, Pair<String, String>>, String> featureOfProduct1;
    public TableColumn<Pair<String, Pair<String, String>>, String> featureOfProduct2;
    public StackPane videoPane;
    public Label numberOfPeopleRated;
    public Label usernameTextField;
    public Text allTimeOfVideo;
    public Text currentTimeOfVideo;
    public Slider timeSlider;
    public Slider volumeSlider;
    public File file;
    public Media media;
    public MediaPlayer mediaPlayer;
    public MediaView mediaView;


    private Product product;
    private Application parent;

    public void setProductAndParent(Product product, Application parent) {
        this.product = product;
        product.setVisit(product.getVisit() + 1);
        this.parent = parent;
        setProductImage(product, productImage, 200, 300);
        ivTarget.setVisible(false);
        setProductProperties();
        setSellers();
        setFeatures();
        setComments();
        checkOff();
        comment.setEditable(false);
        error.setVisible(false);
        if (isBackToComment) {
            isBackToComment = false;
            tabPane.getSelectionModel().select(tab);
        }
        if (BuyerProcessor.getInstance().isUserLoggedIn()) {
            usernameTextField.setText(BuyerProcessor.getInstance().getUser().getUsername());
        }
        init();

    }

    public void checkOff() {
        timesRemain.setVisible(false);
        isOff.setVisible(false);
        if (Off.isProductInOff(product) != 0) {
            isOff.setText("off amount : " + Off.isProductInOff(product));
            isOff.setVisible(true);
            Off off = Off.getOffProductInOff(product);
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            assert off != null;
            timesRemain.setText(timeFormat.format(off.getEndTime().getTime() - new Date().getTime()));

            timesRemain.setVisible(true);
            Timeline oneSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), event -> timesRemain.setText(timeFormat.format(off.getEndTime().getTime() - new Date().getTime()))));
            oneSecondsWonder.setCycleCount(Timeline.INDEFINITE);
            oneSecondsWonder.play();

        }
    }

    public void setComments() {
        product.getComments().forEach((comment -> comments.getItems().add(comment.getBuyerUserName() + "\t" + comment.isBuy())));
    }

    public void setFeatures() {
        feature.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        value.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(product.getFeaturesMap().entrySet());
        features.getColumns().setAll(feature, value);
        features.setItems(items);
    }

    public void setSellers() {
        for (Seller seller : product.getSellers()) {
            sellers.getItems().add(seller.getUsername());
        }
        sellers.setValue(product.getSellers().get(0).getUsername());
    }

    public void setProductProperties() {
        productName.setText(product.getName());
        productPrice.setText(Double.toString(product.getPrice()));
        productScore.setPartialRating(true);
        numberOfPeopleRated.setText(product.getScore().getBuyers() + " user");
        productScore.setRating(product.getScore().getAvgScore());
        productScore.disableProperty().setValue(true);
    }

    public void goBack() {
        Music.getInstance().backPage();
        try {
            parent.start(ProductWindow.getInstance().getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToCart() {
        Seller seller = product.getSellerByUserName(sellers.getValue());
        new Alert(Alert.AlertType.INFORMATION, BuyerProcessor.getInstance().addToBuyerCart(new Pair<>(product, seller))).showAndWait();
    }

    public void rate() {

        error.setVisible(true);
        if (!(BuyerProcessor.getInstance().getUser() instanceof Buyer)) {
            Music.getInstance().error();
            error.setText("you are not buyer");
        }
        if (!BuyerProcessor.getInstance().isUserLoggedIn()) {
            Music.getInstance().error();
            error.setText("first log in please");
        } else if (!((Buyer) BuyerProcessor.getInstance().getUser()).hasBuyProduct(product)) {
            Music.getInstance().error();
            error.setText("you didn't buy this product");
        } else if (product.getScore().isUserRatedBefore(BuyerProcessor.getInstance().getUser())) {
            Music.getInstance().error();
            error.setText("you rated before");
        } else {
            client.rateProduct(product, (int) rating.getRating(), BuyerProcessor.getInstance().getUser());
            product.rateProduct((int) rating.getRating(), BuyerProcessor.getInstance().getUser());
            Music.getInstance().confirmation();
            error.setText("done");
        }
    }

    public void showComment() {
        if (comments.getItems().size() != 0) {
            int index = comments.getSelectionModel().getSelectedIndex();
            comment.setText(product.getComments().get(index).getCommentText());
        }
    }

    public void addComment() {
        if (!BuyerProcessor.getInstance().isUserLoggedIn()) {
            Music.getInstance().error();
            new Alert(Alert.AlertType.ERROR, "first log in").showAndWait();
        } else {
            Music.getInstance().confirmation();
            AddCommentWindow.getInstance().setProduct(product);
            AddCommentWindow.getInstance().start(MainWindow.getInstance().getStage());
            isBackToComment = true;
        }
    }


    public void zoom(MouseEvent event) {
        productImage.setPreserveRatio(true);
        ivTarget.setPreserveRatio(false);
        int x = (int) event.getX();
        int y = (int) event.getY();
        PixelReader reader = productImage.getImage().getPixelReader();
        Image currentImage;
        currentImage = new WritableImage(reader, x, y, 50, 50);
        ivTarget.setImage(currentImage);
        ivTarget.setVisible(true);
    }

    public void endZoom() {
        ivTarget.setVisible(false);
    }

    public void compare() {
        Product product2;
        if (!Product.hasProductWithId(secondProductId.getText())) {
            new Alert(Alert.AlertType.ERROR, "no product with this id").showAndWait();
            return;
        }
        product2 = Product.getProductById(secondProductId.getText());
        if (!product2.getCategory().getName().equals(product.getCategory().getName())) {
            new Alert(Alert.AlertType.ERROR, "not same category").showAndWait();
            return;
        }
        featureForCompare.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        featureOfProduct1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getKey()));
        featureOfProduct2.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().getValue()));
        ArrayList<Pair<String, Pair<String, String>>> list = new ArrayList<>();
        for (String s : product2.getCategory().getFeatures()) {
            Pair<String, Pair<String, String>> pairPair = new Pair<>(s, new Pair<>(product.getFeaturesMap().get(s),
                    product2.getFeaturesMap().get(s)));
            list.add(pairPair);
        }
        ObservableList<Pair<String, Pair<String, String>>> details = FXCollections.observableArrayList(list);
        compareTable.setItems(details);
    }

    public void play() {
        new Thread(() -> {
            if (mediaPlayer.getCurrentTime() == mediaPlayer.getStartTime()) {
                mediaPlayer.stop();
                System.out.println(1);
            }
            mediaPlayer.play();
        }).start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void repeat() {
        timeSlider.adjustValue(0);
        mediaPlayer.stop();
        mediaPlayer.seek(mediaPlayer.getStartTime());
        mediaPlayer.play();
        mediaPlayer.pause();
        mediaPlayer.play();

    }


    public void updatesValues() {
        Platform.runLater(() -> {
            timeSlider.setValue(((mediaPlayer.getCurrentTime().toMillis()) / (mediaPlayer.getTotalDuration().toMillis())) * 100);
            currentTimeOfVideo.setText((int) (mediaPlayer.getCurrentTime().toSeconds()) + "");
            allTimeOfVideo.setText((int) (mediaPlayer.getStopTime().toSeconds()) + "");
        });
    }

    public void open() {
        Music.getInstance().open();
    }

    @Override
    public void userPanelButtonClicked() {
        BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
        Music.getInstance().open();
        if (!(buyerProcessor.isUserLoggedIn())) {
            LoggedOutStatusWindow.getInstance().setParent(ProductWindow.getInstance(), product);
            LoggedOutStatusWindow.getInstance().start(stage);
        } else if (buyerProcessor.getUser().getUserType() == UserType.MANAGER) {
            ManagerUserWindow.getInstance().setParent(ProductWindow.getInstance(), product);
            ManagerUserWindow.getInstance().start(stage);
        } else if (buyerProcessor.getUser().getUserType() == UserType.SELLER) {
            SellerUserWindow.getInstance().setParent(ProductWindow.getInstance(), product);
            SellerUserWindow.getInstance().start(stage);
        } else {
            BuyerUserWindow.getInstance().setParent(ProductWindow.getInstance(), product);
            BuyerUserWindow.getInstance().start(stage);
        }
    }

    private void init() {
        file = new File("src/main/resources/videos/products/" + product.getProductId() + ".mp4");
        if (!file.canRead()) {
            file = new File("src/main/resources/video.mp4");
        }
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);
        mediaView.setFitHeight(400);
        mediaView.setFitWidth(820);
        videoPane.getChildren().add(mediaView);
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> updatesValues());

        timeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (timeSlider.isPressed()) {
                mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(timeSlider.getValue() / 100));
            }
        });
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (volumeSlider.isPressed()) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100);
            }
        });
    }
}
