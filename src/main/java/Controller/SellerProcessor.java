package Controller;

import model.Company;
import model.SellOrder;
import model.Seller;
import model.User;
import View.SellerShowAndCatch;

import java.util.ArrayList;

public class SellerProcessor extends Processor {
    private static SellerShowAndCatch sellerShowAndCatch = SellerShowAndCatch.getInstance();

    public void viewPersonalInfo() {
        sellerShowAndCatch.viewUser(user.getUserPersonalInfo());
        //override beshe behtare
    }

    //public void editField(String field){ }

    public void viewCompanyInfo() {
        //TODO : error handling
        sellerShowAndCatch.showCompanyInfo(((Seller) user).getCompany());
    }

    public void viewSalesHistory() {
        //TODO : error handling
        ArrayList<SellOrder> orders = ((Seller) user).getOrders();
        sellerShowAndCatch.showSellOrders(((Seller) user).getOrders());
    }


}
