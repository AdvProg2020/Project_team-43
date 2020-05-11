package model.request;



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

    public static Request getRequestById(String requestId){
        for (Request request : allRequests) {
            if(request.requestId.equalsIgnoreCase(requestId)){
                return request;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId='" + requestId + '\'' +
                ", requestType='" + requestType + '\'' +
                '}';
    }
}
