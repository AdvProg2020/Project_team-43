package model;

import java.util.ArrayList;

public abstract class User {
    public static ArrayList<User> allUsers = new ArrayList<User>();
    protected String username;

    public UserPersonalInfo getUserPersonalInfo() {
        return userPersonalInfo;
    }

    protected UserPersonalInfo userPersonalInfo;
    protected double credit;//hamoon etebare
    protected UserType userType;


    public User(String username, UserPersonalInfo userPersonalInfo) {
        this.username = username;
        this.userPersonalInfo = userPersonalInfo;
        setUserType();
        credit = 0;
        allUsers.add(this);
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

    public void viewPersonalInfo() {

    }

    public void editFields(String field) {

    }

    public void viewCredit() {

    }


}
