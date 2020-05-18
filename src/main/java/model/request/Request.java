package model.request;


import model.Manager;
import model.Product;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Request {
    private static int constructId = 0;
    protected static ArrayList<Request> allRequests = new ArrayList<>();
    protected String requestId;
    protected String requestType;

    public Request(String requestType) {
        this.requestId = "" + constructId;
        this.requestType = requestType;
        constructId += 1;
        allRequests.add(this);
    }

    public static void addAll(ArrayList<Request> requests) {
        allRequests.addAll(requests);
        constructId += requests.size();
    }

    public static ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public String getRequestType() {
        return requestType;
    }

    public static Request getRequestById(String requestId) {
        for (Request request : allRequests) {
            if (request.requestId.equalsIgnoreCase(requestId)) {
                return request;
            }
        }
        return null;
    }

    public String getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId='" + requestId + '\'' +
                ", requestType='" + requestType + '\'' +
                '}';
    }

    public static void load() throws FileNotFoundException {
        EditProductRequest.load();
        EditOffRequest.load();
        ProductRequest.load();
        SellerRequest.load();
        OffRequest.load();
    }

    public static void loadFields() {
        EditOffRequest.loadFields();
        EditProductRequest.loadFields();
        ProductRequest.loadFields();
        SellerRequest.loadAllFields();
        OffRequest.loadFields();
    }

    public static void save() throws IOException {
        EditProductRequest.save();
        EditOffRequest.save();
        ProductRequest.save();
        SellerRequest.save();
        OffRequest.save();
    }

    public static void saveFields() {
        EditOffRequest.saveFields();
        ProductRequest.saveFields();
        EditProductRequest.saveFields();
        OffRequest.saveFields();
    }


}
