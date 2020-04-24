package model;

import java.util.ArrayList;

public abstract class User {
    private static final int money = 100000;
    public static ArrayList<User> allUsers = new ArrayList<User>();
    protected String username;
    protected UserPersonalInfo userPersonalInfo;
    protected double balance;//hamoon etebare
    protected UserType userType;

    public UserPersonalInfo getUserPersonalInfo() {
        return userPersonalInfo;
    }

    public User(String username, UserPersonalInfo userPersonalInfo) {
        this.username = username;
        this.userPersonalInfo = userPersonalInfo;
        setUserType();
        balance = money;
    }

    public abstract void setUserType();

    public static boolean hasUserWithUserName(String username) {
        for (User user : allUsers) {
            if (user.username.equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static User getUserByUserName(String username) {
        for (User user : allUsers) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "user type : " + userType + "\n"
                + "user name : " + username + "\n"
                + "first name : " + userPersonalInfo.getFirstName() + "\n"
                + "last name : " + userPersonalInfo.getLastName() + "\n"
                + "email : " + userPersonalInfo.getEmail() + "\n"
                + "phone number : " + userPersonalInfo.getPhoneNumber() + "\n"
                + "balance : " + balance;

    }
}
