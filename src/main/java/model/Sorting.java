package model;

import java.util.Comparator;

public class Sorting {
    private static Comparator<Product> comparator;

    public static void setSortByView() {
        comparator = SortingByView.getInstance();
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
