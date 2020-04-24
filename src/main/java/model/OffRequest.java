package model;

public class OffRequest extends Request {
    private Off off;
    public OffRequest(String requestId, Off off) {
        super(requestId,"offType");
        this.off=off;
    }

    public Off getOff() {
        return off;
    }
}
