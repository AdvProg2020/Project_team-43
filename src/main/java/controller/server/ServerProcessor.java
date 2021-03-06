package controller.server;

import model.User;
import model.UserPersonalInfo;

import java.util.Objects;
import java.util.UUID;

public class ServerProcessor {
    public String login(String username, String password) {
        if (!User.hasUserWithUserName(username))
            return "there is no user with this username";
        if (!Objects.requireNonNull(User.getUserByUserName(username)).getUserPersonalInfo().getPassword().equals(password))
            return "incorrect password";
        return "logged in successful";
    }

    public String checkUsernamePassword(String username, String password) {
        if (!User.hasUserWithUserName(username))
            return "invalid";
        if (!Objects.requireNonNull(User.getUserByUserName(username)).getUserPersonalInfo().getPassword().equals(password))
            return "invalid";
        return "valid";
    }

    public String createToken() {
        return UUID.randomUUID().toString();
    }

    public void chargeUser(String amount, User user) {
        user.setBalance(user.getBalance() + Integer.parseInt(amount));
    }

    public void withdraw(String amount, User user) {
        user.setBalance(user.getBalance() - Integer.parseInt(amount));
    }

    public void updateUser(String firstName, String lastName, String email, String phoneNumber, String password, User user) {
        user.setUserPersonalInfo(new UserPersonalInfo(firstName, lastName, email, phoneNumber, password));
    }
}

