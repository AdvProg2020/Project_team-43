package model;

import java.util.ArrayList;

public class Manager extends User {
    public static ArrayList<Request> allRequest = new ArrayList<Request>();

    public Manager(String nickName, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(nickName, firstName, lastName, email, phoneNumber, password);
        this.userType = 3;
        User.allUsers.add(this);
    }

    public void viewPersonalInfo() {

    }

    public void editFields(String field) {

    }

    public void changeUser(String userName, int type) {
        User user = User.getUserByUserName(userName);
        if (type == 1 && user.userType != 1) {

        } else if (type == 2 && user.userType != 2) {

        } else if (type == 3 && user.userType != 3) {

        }
    }

    public void deleteUser(String userName) {

    }

    public void viewUser(String userName) {

    }

    public void createManager() {

    }

    public void removeProduct(String productId) {

    }

    public void createDiscountCode() {

    }

    public void viewDiscountCodes() {

    }

    public void viewDiscountCode(String discountId) {

    }

    public void editDiscount(String discountId) {

    }

    public void deleteDiscount(String discountId) {

    }

    public void detailRequest(String requestId) {

    }

    public void acceptRequest(String requestId) {

    }

    public void declineRequest(String requestId) {

    }

    public void editCategory(Category category) {

    }

    public void addCategory(Category category) {

    }

    public void removeCategory(Category category) {

    }

}
