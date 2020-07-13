package controller.server;

import model.User;

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
}

