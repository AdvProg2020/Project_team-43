package model;

public class OffRequest extends Request {
    private Off off;
    public OffRequest(String requestId, Off off) {
        super(requestId);
        this.off=off;
        Manager.allRequest.add(this);
    }
}
