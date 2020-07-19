package model;


import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Supporter extends User {
    private static final String fileAddress = "database/Supporter.dat";
    private Map<String, List<String>> users;
    private boolean isOnline = false;

    public Supporter(String username, UserPersonalInfo userPersonalInfo) {
        super(username, userPersonalInfo);
        users = new HashMap<>();
        allUsers.add(this);

    }

    @Override
    public void setUserType() {
        userType = UserType.SUPPORTER;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void online() {
        isOnline = true;
    }

    public void offline() {
        isOnline = false;
    }

    public Map<String, List<String>> getUsers() {
        return users;
    }

    public static void load() {
        Supporter[] supporters = null;
        try {
            supporters = (Supporter[]) Loader.load(Supporter[].class, fileAddress);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (supporters != null) {
            ArrayList<Supporter> allSupporters = new ArrayList<>(Arrays.asList(supporters));
            allUsers.addAll(allSupporters);
        }
    }

    public static void save() {

        ArrayList<Supporter> allSupporters = new ArrayList<>();
        for (User user : allUsers) {
            if (user.userType == UserType.SUPPORTER) {
                allSupporters.add((Supporter) user);
            }
        }
        try {
            Saver.save(allSupporters, fileAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
