package model;



import java.util.ArrayList;

public abstract class Request {
    protected static ArrayList<Request> allRequests=new ArrayList<Request>();
    protected String requestId;
    protected String requestType;

    public Request(String requestId,String requestType) {
        this.requestId = requestId;
        this.requestType=requestType;
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
