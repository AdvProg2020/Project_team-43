package model;

public class SellerRequest extends Request {
    private Seller seller;
    public SellerRequest(String requestId,UserPersonalInfo personalInfo,String companyName,String username) {
        super(requestId,"sellerType");
        seller=new Seller(username,personalInfo,companyName);
    }
}
