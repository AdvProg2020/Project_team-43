package model;

public class OffRequest extends Request {
    private Off off;
    public OffRequest(Off off) {
        super("off");
        this.off=off;
        Manager.allRequest.add(this);
    }

    public Off getOff() {
        return off;
    }
}
