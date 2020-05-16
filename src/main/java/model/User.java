package model;

import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class User {
    private static final int startMoney = 100000;

    protected static ArrayList<User> allUsers = new ArrayList<>();
    private static String fileAddress = "database/User.dat";


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

    public void editFields(String field) {

    }

    public void viewCredit() {

    }

    public static void addUser(User user){
        allUsers.add(user);
    }

    public static void addAll(ArrayList<User> users){
        allUsers.addAll(users);
    }

    public static void load() throws FileNotFoundException {
        Buyer.load();
        Seller.load();
        Manager.load();
    }


    public static void save() throws IOException {
        Buyer.save();
        Seller.save();
        Manager.save();
    }

    @Override
    public String toString() {
        return userPersonalInfo.toString();
    }
}
