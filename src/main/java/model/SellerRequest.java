package model;

public class SellerRequest extends Request {
    private Seller seller;
    public SellerRequest(UserPersonalInfo personalInfo,String companyName,String username) {
        super("sellerType");
        seller=new Seller(username,personalInfo,companyName);
        allRequests.add(this);
    }
    public static void addSellerRequest(UserPersonalInfo personalInfo, String username, String companyName) {
        new SellerRequest(personalInfo, companyName, username);
    }

    public Seller getSeller() {
        return seller;
    }
}
