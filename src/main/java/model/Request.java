package model;



import java.util.ArrayList;

public abstract class Request {
    private static ArrayList<Request> allRequests=new ArrayList<Request>();
    protected String requestId;
    protected String requestType;

    public Request(String requestId,String requestType) {
        this.requestId = requestId;
        this.requestType=requestType;
        allRequests.add(this);
    }

    public static ArrayList<Request> getAllRequests() {
        return allRequests;
    }
}
