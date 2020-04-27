package model;



import java.util.ArrayList;

public abstract class Request {
    public static int constructId = 0;
    protected static ArrayList<Request> allRequests = new ArrayList<Request>();
    protected String requestId;
    protected String requestType;

    public Request(String requestType) {
        this.requestId = "" + constructId;
        this.requestType = requestType;
        constructId += 1;
    }

    public static ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public String getRequestType() {
        return requestType;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId='" + requestId + '\'' +
                ", requestType='" + requestType + '\'' +
                '}';
    }
}
