package model.request;

import model.Manager;
import model.Off;

public class OffRequest extends Request {
    private Off off;

    public OffRequest(Off off) {
        super("off");
        this.off=off;
        allRequests.add(this);
    }

    public Off getOff() {
        return off;
    }
}
