package model;

import javafx.scene.image.Image;
import model.database.Loader;
import model.database.Saver;
import model.database.Sqlite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class User implements Serializable {
    private static final int startMoney = 0;

    public static ArrayList<User> allUsers = new ArrayList<>();

    private static String fileAddress = "database/DeletedUsers.dat";


    protected String username;
    protected UserPersonalInfo userPersonalInfo;
    protected double balance;//hamoon etebare
    protected UserType userType;

    public UserPersonalInfo getUserPersonalInfo() {
        return userPersonalInfo;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User(String username, UserPersonalInfo userPersonalInfo) {
        this.username = username;
        this.userPersonalInfo = userPersonalInfo;
        setUserType();
        balance = startMoney;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
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

    public void viewPersonalInfo() {
    }

    public static boolean hasManager() {
        for (User user : allUsers) {
            if (user.getUserType() == UserType.MANAGER)
                return true;
        }
        return false;

    }

    public static void removeUser(User user) {
        allUsers.remove(user);
    }

    public static void addAll(ArrayList<User> users) {
        allUsers.addAll(users);
    }

    public static void load() throws FileNotFoundException {
//        Buyer.load();
        new Sqlite().loadBuyer();
        Seller.load();
        Manager.load();

    }


    public static void save() throws IOException {
        Buyer.save();
        Seller.save();
        Manager.save();

    }

    public static void loadFields() {
        Buyer.loadFields();
        Seller.loadAllFields();
    }

    public static void saveFields() {
        Buyer.saveFields();
        Seller.saveAllFields();

    }

    @Override
    public String toString() {
        return username + ":" + userPersonalInfo.toString();
    }

    public void setUserPersonalInfo(UserPersonalInfo userPersonalInfo) {
        this.userPersonalInfo = userPersonalInfo;
    }

    public static void setAllUsers(ArrayList<User> allUsers) {
        User.allUsers = allUsers;
    }
}
