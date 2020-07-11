package View.GraphicController;

import Controller.console.BuyerProcessor;
import View.Client;
import View.graphic.*;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Product;
import model.User;
import model.UserType;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public abstract class Controller {
    protected static Client client;

    public static void setClient(Client client) {
        Controller.client = client;
    }

    protected Stage stage = MainWindow.getInstance().getStage();
    protected Application parent;
    protected Product product;

    public void setParent(Application parent, Product product) {
        this.parent = parent;
        this.product = product;
    }


    public void goBack() {
        Music.getInstance().backPage();
        MainWindow.getInstance().start(MainWindow.getInstance().getStage());
    }

    public void userPanelButtonClicked() {
        BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
        Music.getInstance().open();
        if (!(buyerProcessor.isUserLoggedIn())) {
            LoggedOutStatusWindow.getInstance().setParent(parent, null);
            LoggedOutStatusWindow.getInstance().start(stage);
        } else if (buyerProcessor.getUser().getUserType() == UserType.MANAGER) {
            ManagerUserWindow.getInstance().setParent(parent, null);
            ManagerUserWindow.getInstance().start(stage);
        } else if (buyerProcessor.getUser().getUserType() == UserType.SELLER) {
            SellerUserWindow.getInstance().setParent(parent, null);
            SellerUserWindow.getInstance().start(stage);
        } else {
            BuyerUserWindow.getInstance().setParent(parent, null);
            BuyerUserWindow.getInstance().start(stage);
        }
    }

    public void userPanelGoBack() {
        Music.getInstance().backPage();
        try {
            if (parent instanceof ProductWindow) {
                ProductWindow.getInstance().setProduct(product, ProductPanelWindow.getInstance());
                ProductWindow.getInstance().start(stage);
            } else {
                parent.start(stage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setProductImage(Product product, ImageView imageView) {
        File file = new File("src/main/resources/photos/products/" + product.getProductId() + ".jpg");
        if (file.exists()) {
            imageView.setImage(new Image("file:" + "src/main/resources/photos/products/" + file.getName()));
            return;
        }
        file = new File("src/main/resources/photos/products/" + product.getProductId() + ".png");
        if (file.exists()) {
            imageView.setImage(new Image("file:" + "src/main/resources/photos/products/" + file.getName()));
            return;
        }
        imageView.setImage(new Image("file:" + "src/main/resources/product.jpg"));
    }

    public void setProductImage(Product product, ImageView imageView, int height, int width) {
        File file = new File("src/main/resources/photos/products/" + product.getProductId() + ".jpg");
        if (file.exists()) {
            imageView.setImage(new Image("file:" + "src/main/resources/photos/products/" + file.getName(), width, height, false, false));
            return;
        }
        file = new File("src/main/resources/photos/products/" + product.getProductId() + ".png");
        if (file.exists()) {
            imageView.setImage(new Image("file:" + "src/main/resources/photos/products/" + file.getName(), width, height, false, false));
            return;
        }
        imageView.setImage(new Image("file:" + "src/main/resources/product.jpg", width, height, false, false));
    }

    protected void setUserImage(User user, ImageView imageView) {
        File file = new File("src/main/resources/photos/users/" + user.getUsername() + ".jpg");
        if (file.exists()) {
            imageView.setImage(new Image("file:" + "src/main/resources/photos/users/" + file.getName()));
            return;
        }
        file = new File("src/main/resources/photos/users/" + user.getUsername() + ".png");
        if (file.exists()) {
            imageView.setImage(new Image("file:" + "src/main/resources/photos/users/" + file.getName()));
            return;
        }
        imageView.setImage(new Image("file:" + "src/main/resources/user.png"));

    }

    public void browsePhotoUser(User user, ImageView profilePhoto) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.jpg"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            final File folder = new File("src/main/resources/photos/users");
            for (final File photo : folder.listFiles()) {
                if (photo.isFile()) {
                    String fileName = FilenameUtils.getBaseName(photo.getAbsolutePath());
                    if (user.getUsername().equals(fileName)) {
                        photo.delete();
                    }
                }
            }
            Files.copy(file.toPath(), new File("src/main/resources/photos/users/" + user.getUsername() + "." + FilenameUtils.getExtension(file.getAbsolutePath()).toLowerCase()).toPath(), StandardCopyOption.REPLACE_EXISTING);
            setUserImage(user, profilePhoto);
        }
    }

    public void logout() {
        BuyerProcessor.getInstance().logout();
        userPanelGoBack();
    }

}
