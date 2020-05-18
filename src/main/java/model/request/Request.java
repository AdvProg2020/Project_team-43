package model.request;



import model.Manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Request {
    public static int constructId = 0;
    protected static ArrayList<Request> allRequests = new ArrayList<>();
    protected String requestId;
    protected String requestType;

    public Request(String requestType) {
        this.requestId = "" + constructId;
        this.requestType = requestType;
        constructId += 1;
        Manager.allRequest.add(this);
    }

    public static ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public String getRequestType() {
        return requestType;
    }

    public static Request getRequestById(String requestId){
        for (Request request : allRequests) {
            if(request.requestId.equalsIgnoreCase(requestId)){
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
    }

    public static void loadFields(){
        EditOffRequest.loadFields();
        EditProductRequest.loadFields();
    }

    public static void save() throws IOException {
        EditProductRequest.save();
        EditOffRequest.save();
    }

    public static void saveFields(){
        EditOffRequest.saveFields();
        EditProductRequest.saveFields();
    }


}
