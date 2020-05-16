package model.request;

import model.Manager;
import model.Off;
import model.State;

public class EditOffRequest extends Request {
    Off off;
    String field;
    String input;

    public EditOffRequest(Off off, String field, String input) {
        super("edit off");
        this.off = off;
        this.field = field;
        this.input = input;
        off.setOffState(State.OffState.EDITING_PROCESS);
        allRequests.add(this);
    }

    public Off getOff() {
        return off;
    }

    public String getField() {
        return field;
    }

    public String getInput() {
        return input;
    }
}
