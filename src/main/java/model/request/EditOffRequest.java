package model.request;

import model.Off;

public class EditOffRequest extends Request {
    String oldOffId;
    Off newOff;

    public EditOffRequest(String requestType, String oldOffId, Off newOff) {
        super(requestType);
        this.oldOffId = oldOffId;
        this.newOff = newOff;
    }

    public String getOldOffId() {
        return oldOffId;
    }

    public Off getNewOff() {
        return newOff;
    }
}
