package View;

import model.Company;
import model.SellOrder;
import model.User;
import model.UserPersonalInfo;

import java.util.ArrayList;

public class SellerShowAndCatch {
    private static SellerShowAndCatch ourInstance = new SellerShowAndCatch();

    public static SellerShowAndCatch getInstance() {
        return ourInstance;
    }

    private SellerShowAndCatch() {
    }

    public void viewUser(UserPersonalInfo personalInfo) {
        System.out.println(personalInfo.toString());
    }

    public void showCompanyInfo(Company company) {
        System.out.println(company.toString());
    }

    public void showSellOrders(ArrayList<SellOrder> orders) {
        for (SellOrder order : orders) {
            System.out.println(order.toString());
        }
    }
}
