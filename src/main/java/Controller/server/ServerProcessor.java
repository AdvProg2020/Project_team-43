package controller.server;

import controller.client.BuyerProcessor;
import model.Buyer;
import model.User;
import model.UserType;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
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

    public String createToken() {
        return UUID.randomUUID().toString();
    }
}
