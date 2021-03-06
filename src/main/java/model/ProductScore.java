package model;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductScore  implements Serializable {
    private int buyers;
    private double avgScore;
    private final ArrayList<String> userNamesOfUsersRated;

    public int getBuyers() {
        return buyers;
    }

    public ProductScore() {
        buyers = 0;
        avgScore = 0;
        userNamesOfUsersRated = new ArrayList<>();
    }

    public void addBuyer(double score) {
        buyers++;
        avgScore = (avgScore * (buyers - 1) + score) / buyers;
    }

    public void addBuyer(double score, String userName) {
        userNamesOfUsersRated.add(userName);
        buyers++;
        avgScore = (avgScore * (buyers - 1) + score) / buyers;
    }

    public boolean isUserRatedBefore(User user) {
        return userNamesOfUsersRated.contains(user.getUsername());
    }

    public double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }
}
