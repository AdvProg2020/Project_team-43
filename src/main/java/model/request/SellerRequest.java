package model.request;

import model.Manager;
import model.Seller;
import model.User;
import model.UserPersonalInfo;
import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class SellerRequest extends Request {
    private final static String fileAddress = "database/SellerRequest.dat";
    private Seller seller;

    public SellerRequest(UserPersonalInfo personalInfo, String companyName, String username) {
        super("seller");
        seller = new Seller(username, personalInfo, companyName);
    }

    public static void addSellerRequest(UserPersonalInfo personalInfo, String username, String companyName) {
        new SellerRequest(personalInfo, companyName, username);
    }

    public Seller getSeller() {
        return seller;
    }

    public static void load() throws FileNotFoundException {
        SellerRequest[] sellerRequests = (SellerRequest[]) Loader.load(SellerRequest[].class, fileAddress);
        if (sellerRequests != null) {
            ArrayList<Request> requests = new ArrayList<>(Arrays.asList(sellerRequests));
            Request.addAll(requests);
        }
    }

    public static void save() throws IOException {
        ArrayList<SellerRequest> sellerRequests = new ArrayList<>();
        for (Request request : allRequests) {
            if (request.requestType.equals("seller"))
                sellerRequests.add((SellerRequest) request);
        }
        Saver.save(sellerRequests, fileAddress);
    }

    public static void loadAllFields() {
        for (Request request : allRequests) {
            if (request.getRequestType().equals("seller"))
                ((SellerRequest) request).loadSeller();
        }
    }

    public void loadSeller() {
        User.removeUser(this.seller);
        this.seller = new Seller(this.seller.getUsername(), this.seller.getUserPersonalInfo(), this.seller.getCompany().getName());

    }

    @Override
    public String toString() {
        return "SellerRequest{" +
                ", requestId='" + requestId + '\'' +
                "seller=" + seller +
                ", requestType='" + requestType + '\'' +
                '}';
    }
}
