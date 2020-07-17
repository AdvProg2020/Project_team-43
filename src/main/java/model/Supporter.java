package model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Supporter extends User {
    private Map<String, List<String>> users;
    private boolean isOnline = false;

    public Supporter(String username, UserPersonalInfo userPersonalInfo) {
        super(username, userPersonalInfo);
        users = new HashMap<>();
        allUsers.add(this);

    }

    @Override
    public void setUserType() {
        userType = UserType.Supporter;
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
}
