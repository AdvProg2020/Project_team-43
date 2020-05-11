package model.request;

import model.Manager;
import model.Seller;
import model.UserPersonalInfo;

import java.util.UUID;

public class SellerRequest extends Request {
    private Seller seller;
    public SellerRequest(UserPersonalInfo personalInfo, String companyName, String username) {
        super("sellerType");
        seller=new Seller(username,personalInfo,companyName);
        Manager.allRequest.add(this);
        allRequests.add(this);
    }
    public static void addSellerRequest(UserPersonalInfo personalInfo, String username, String companyName) {
        new SellerRequest(personalInfo, companyName, username);
    }

    public Seller getSeller() {
        return seller;
    }
}
