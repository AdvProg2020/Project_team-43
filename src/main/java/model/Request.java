package model;

public abstract class Request {
    protected String requestId;
    protected String requestType;

    public Request(String requestId, String requestType) {
        this.requestId = requestId;
        this.requestType = requestType;
        Manager.allRequest.add(this);
    }

    public String getRequestId() {
        return requestId;
    }

    public String getRequestType() {
        return requestType;
    }

    @Override
    public String toString() {
        return "request type : " + requestType + "\n"
                + "request Id : " + requestId;
    }
}
