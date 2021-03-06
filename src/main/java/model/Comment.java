package model;

import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Comment implements Serializable {
    private static String fileAddress = "database/Comment.dat";
    public static ArrayList<Comment> allComments = new ArrayList<>();
    public static ArrayList<Comment> acceptedComments = new ArrayList<Comment>();
    public static ArrayList<Comment> inQueueExpectation = new ArrayList<Comment>();
    public static ArrayList<Comment> declineComments = new ArrayList<>();
    private transient Buyer buyer;


    private String buyerUserName;
    private transient Product product;
    private String productId;
    private String commentText;
    private State.OpinionState opinionState;
    private boolean isBuy;

    public String getBuyerUserName() {
        return buyerUserName;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public static ArrayList<Comment> getInQueueExpectation() {
        return inQueueExpectation;
    }

    public State.OpinionState getOpinionState() {
        return opinionState;
    }

    public Comment(Product product, String opinionText, boolean isBuy, Buyer buyer) {
        this.product = product;
        this.productId = product.getProductId();
        this.buyer = buyer;
        this.commentText = opinionText;
        this.opinionState = State.OpinionState.WAITING_CONFIRMATION;
        this.isBuy = isBuy;
        buyerUserName = buyer.getUsername();
        inQueueExpectation.add(this);
    }

    public void setOpinionState(State.OpinionState opinionState) {
        this.opinionState = opinionState;
    }


    public String getCommentText() {
        return commentText;
    }

    private void loadProduct() {
        this.product = Product.getProductById(this.productId);
    }

    private void loadBuyer() {
        this.buyer = (Buyer) (User.getUserByUserName(this.buyerUserName));
    }

    public void accept() {
        this.opinionState = State.OpinionState.CONFIRMED;
        acceptedComments.add(this);
        inQueueExpectation.remove(this);
    }

    public void decline() {
        this.opinionState = State.OpinionState.UNCONFIRMED;
        declineComments.add(this);
        inQueueExpectation.remove(this);

    }

    private static void loadAllProduct() {
        for (Comment comment : allComments) {
            comment.loadProduct();
        }
    }

    private static void loadAllBuyer() {
        for (Comment comment : allComments) {
            comment.loadBuyer();
        }
    }

    public static void loadFields() {
        loadAllProduct();
        loadAllBuyer();
    }

    public static void saveFields() {
        allComments.clear();
        allComments.addAll(acceptedComments);
        allComments.addAll(inQueueExpectation);
        allComments.addAll(declineComments);
        saveAllBuyers();
        saveAllProducts();
    }

    private static void saveAllProducts() {
        for (Comment comment : allComments) {
            comment.saveProduct();
        }
    }

    private static void saveAllBuyers() {
        for (Comment comment : allComments) {
            comment.saveBuyer();
        }
    }

    private void saveBuyer() {
    }

    private void saveProduct() {
        productId = product.getProductId();
    }

    public static void load() throws FileNotFoundException {
        Comment[] comments = (Comment[]) Loader.load(Comment[].class, fileAddress);
        if (comments != null) {
            allComments = new ArrayList<>(Arrays.asList(comments));
            loadComments();
        }
    }

    public static void loadComments() {
        for (Comment comment : allComments) {
            if (comment.opinionState.equals(State.OpinionState.CONFIRMED)) {
                acceptedComments.add(comment);
            } else if (comment.opinionState.equals(State.OpinionState.UNCONFIRMED)) {
                declineComments.add(comment);
            } else if (comment.opinionState.equals(State.OpinionState.WAITING_CONFIRMATION)) {
                inQueueExpectation.add(comment);
            }
        }
    }

    public static void save() throws IOException {
        Saver.save(allComments, fileAddress);
    }
}
