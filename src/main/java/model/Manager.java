package model;

import java.util.ArrayList;

public class Manager extends User {
    public static ArrayList<Request> allRequest = new ArrayList<Request>();

    public Manager(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password);
        this.userType = UserType.MANAGER;
        User.allUsers.add(this);
    }

    public static Request getRequestById(String requestId) {

        return null;
    }

    public void viewPersonalInfo() {

    }

    public void editFields(String field) {

    }

    public void deleteUser(String username) {

    }

    public void viewUser(String username) {

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
