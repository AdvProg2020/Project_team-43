package model.filters;

import model.Off;
import model.Product;

import java.util.ArrayList;

public class CriteriaOff implements Criteria {
    private final String name = "off";

    @Override
    public ArrayList<Product> meetCriteria(ArrayList<Product> products) {
        ArrayList<Product> meetCriteria = new ArrayList<>();
        for (Product product : products) {
            if (Off.isProductInOff(product) != 0)
                meetCriteria.add(product);
        }
        return meetCriteria;
    }

    @Override
    public String getName() {
        return name;
    }
}
