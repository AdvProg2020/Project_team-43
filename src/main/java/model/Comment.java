package model;

import java.util.ArrayList;

public class Comment {
    public static ArrayList<Comment> acceptedComments = new ArrayList<Comment>();
    public static ArrayList<Comment> inQueueExpection = new ArrayList<Comment>();
    private Buyer user;
    private Product product;
    private String commentText;
    private State.OpinionState opinionState;
    private boolean isBuy;

    public Comment(Product product, String opinionText, boolean isBuy) {
        this.product = product;
        this.commentText = opinionText;
        this.opinionState = State.OpinionState.WAITING_CONFIRMATION;
        this.isBuy = isBuy;
        inQueueExpection.add(this);
    }

    public String getCommentText() {
        return commentText;
    }
}
