import Controller.Processor;
import model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

public class CommentTest {
    Manager manager;
    UserPersonalInfo userPersonalInfo;
    Buyer buyer;
    Company company;
    Category category;
    Seller seller;
    Seller seller2;
    Product product1;
    Comment comment;
    Comment comment2;
    Comment comment3;
    Processor processor;

    @BeforeAll
    public void setAll() {
        userPersonalInfo = new UserPersonalInfo("firstName", "lastName", "email", "phoneNumber", "password");
        company = new Company("asus", "none");
        category = new Category("laptop", new ArrayList<>());
        buyer = new Buyer("alireza", userPersonalInfo);
        manager = new Manager("sadra", userPersonalInfo);
        seller = new Seller("parsa", userPersonalInfo, "asus");
        User.allUsers.add(seller);
        User.allUsers.add(seller2);
        product1 = new Product("a", company, 1, category);
        Product.allProductsInList.add(product1);
        comment = new Comment(product1, "opinionText", true, buyer);
        comment2 = new Comment(product1, "opinionText", true, buyer);
        comment3 = new Comment(product1, "opinionText", true, buyer);
        Comment.allComments.add(comment);
        Comment.allComments.add(comment2);
        Comment.allComments.add(comment3);
        comment.setOpinionState(State.OpinionState.WAITING_CONFIRMATION);
        comment2.setOpinionState(State.OpinionState.CONFIRMED);
        comment3.setOpinionState(State.OpinionState.UNCONFIRMED);

    }

    @Test
    public void getCommentText() {
        setAll();
        Assert.assertEquals(comment.getCommentText(), "opinionText");
    }

    @Test
    public void loadFieldTest() {
        setAll();
        Comment.loadFields();
        Assert.assertEquals(comment.getProduct(), product1);
    }

    @Test
    public void loadCommentsTest() {
        setAll();
        Comment.loadComments();
        Assert.assertTrue(Comment.inQueueExpectation.contains(comment));
    }

}
