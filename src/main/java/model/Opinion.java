package model;

import java.util.ArrayList;

public class Opinion {
    public static ArrayList<Opinion> allOpinions = new ArrayList<Opinion>();
    private User user;
    private Product product;
    private String opinionText;
    private State.OpinionState opinionState;
    private boolean isBuy;

    public Opinion(Product product, String opinionText) {
        this.product = product;
        this.opinionText = opinionText;
        this.opinionState = State.OpinionState.waitingConfirmation;
    }
}
/////// class nomre nazadimm na mawloome hanooz