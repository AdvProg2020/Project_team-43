package model;

import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Comment {
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

    public Comment(Product product, String opinionText, boolean isBuy, Buyer buyer) {
        this.product = product;
        this.productId = product.getProductId();
        this.buyer = buyer;
        this.buyerUserName = this.buyer.getUsername();
        this.commentText = opinionText;
        this.opinionState = State.OpinionState.WAITING_CONFIRMATION;
        this.isBuy = isBuy;
        inQueueExpectation.add(this);
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

    }

    public static void load() throws FileNotFoundException {
        Comment[] comments = (Comment[]) Loader.load(Comment[].class, fileAddress);
        if (comments != null) {
            allComments = new ArrayList<>(Arrays.asList(comments));
            loadComments();
        }
    }

    private static void loadComments() {
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
        allComments.clear();
        allComments.addAll(acceptedComments);
        allComments.addAll(inQueueExpectation);
        allComments.addAll(declineComments);
        Saver.save(allComments, fileAddress);
    }
}
