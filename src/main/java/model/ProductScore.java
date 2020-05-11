package model;

public class ProductScore {
    private int buyers;
    private double avgScore;

    public ProductScore() {
        buyers = 0;
        avgScore = 0;
    }

    public void addBuyer(double score) {
        buyers++;
        avgScore = (avgScore * (buyers - 1) + score) / buyers;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }
}
