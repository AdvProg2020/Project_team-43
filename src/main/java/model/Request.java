package model;


public abstract class Request {
    protected String requestId;
    protected String requestType;

    public Request(String requestId) {
        this.requestId = requestId;
    }
}
