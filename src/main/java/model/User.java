package model;

import java.util.ArrayList;

public abstract class User {
    public static ArrayList<User> allUsers = new ArrayList<User>();
    protected final String nickName;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String password;
    protected double balance;
    protected ArrayList<Discount> discounts;
    protected ArrayList<Order> orders;
    protected int userType;


    public User(String nickName, String firstName, String lastName, String email, String phoneNumber, String password) {
        this.nickName = nickName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        /////balance
        discounts = new ArrayList<Discount>();
        orders = new ArrayList<Order>();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static User getUserByUserName(String userName){

        return null;
    }

    public void viewPersonalInfo(){

    }

    public void editFields(String field) {

    }


}
