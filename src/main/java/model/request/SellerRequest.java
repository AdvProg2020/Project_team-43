package model.request;

import model.Seller;
import model.UserPersonalInfo;

import java.util.UUID;

public class SellerRequest extends Request {
    private Seller seller;
    public SellerRequest(String requestId, UserPersonalInfo personalInfo, String companyName, String username) {
        super("sellerType");
        seller=new Seller(username,personalInfo,companyName);
        allRequests.add(this);
    }
    public static void addSellerRequest(UserPersonalInfo personalInfo, String username, String companyName) {
        new SellerRequest(UUID.randomUUID().toString(), personalInfo, companyName, username);
    }

    public Seller getSeller() {
        return seller;
    }
}