package model;

import java.util.Comparator;

public class Sorting {
    private static Comparator<Product> comparator = SortingByView.getInstance();

    public static void setSortByView() {
        comparator = SortingByView.getInstance();
    }

    public static void setSortByScore() {
        comparator = SortingByScore.getInstance();
    }

    public static void setSortByDate() {
        comparator = SortingByDate.getInstance();
    }


    public static Comparator<Product> getComparator() {
        return comparator;
    }
}

class SortingByView implements Comparator<Product> {


    private static final SortingByView instance = new SortingByView();

    private SortingByView() {
    }

    public static SortingByView getInstance() {
        return instance;
    }

    @Override
    public int compare(Product o1, Product o2) {
        if (o1.getVisit() > o2.getVisit())
            return -1;
        else if (o1.getVisit() < o2.getVisit())
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "by view";
    }
}

class SortingByScore implements Comparator<Product> {
    private static final SortingByScore instance = new SortingByScore();

    private SortingByScore() {
    }

    public static SortingByScore getInstance() {
        return instance;
    }

    @Override
    public int compare(Product o1, Product o2) {
        if (o1.getScore().getAvgScore() > o2.getScore().getAvgScore()) {
            return -1;
        } else if (o1.getScore().getAvgScore() < o2.getScore().getAvgScore()) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "by score";
    }
}

class SortingByDate implements Comparator<Product> {

    private static final SortingByDate instance = new SortingByDate();

    private SortingByDate() {
    }

    public static SortingByDate getInstance() {
        return instance;
    }

    @Override
    public int compare(Product o1, Product o2) {
        if (o1.getDate().after(o2.getDate())) {
            return 1;
        } else if (o2.getDate().after(o1.getDate())) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "by time";
    }
}
