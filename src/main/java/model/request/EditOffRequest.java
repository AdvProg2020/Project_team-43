package model.request;

import model.Category;
import model.Manager;
import model.Off;
import model.State;
import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class EditOffRequest extends Request {
    private static String fileAddress = "database/EditOffRequest.dat";
    private transient Off off;
    private String offId;
    private String field;
    private String input;

    public EditOffRequest(Off off, String field, String input) {
        super("edit off");
        this.off = off;
        this.field = field;
        this.input = input;
        off.setOffState(State.OffState.EDITING_PROCESS);
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

    public static void load() throws FileNotFoundException {
        EditOffRequest[] editOffRequests = (EditOffRequest[]) Loader.load(EditOffRequest[].class, fileAddress);
        if (editOffRequests != null) {
            Request.addAll(new ArrayList<>(Arrays.asList(editOffRequests)));
        }
    }


    public static void save() throws IOException {
        ArrayList<EditOffRequest> editOffRequests = new ArrayList<>();
        for (Request request : allRequests) {
            if (request.getRequestType().equals("edit off")) {
                editOffRequests.add((EditOffRequest) request);
            }
        }
        Saver.save(editOffRequests, fileAddress);
    }

    private void loadOff() {
        this.off = Off.getAllOffById(offId);
    }

    private void saveOff() {
        this.offId = off.getOffId();
    }

    private static void loadAllOffs() {
        for (Request request : allRequests) {
            if (request.getRequestType().equals("edit off")) {
                ((EditOffRequest) request).loadOff();
            }
        }
    }

    private static void saveAllOffs() {
        for (Request request : allRequests) {
            if (request.getRequestType().equals("edit off")) {
                ((EditOffRequest) request).saveOff();
            }
        }
    }

    public static void loadFields() {
        loadAllOffs();
    }

    public static void saveFields() {
        saveAllOffs();
    }

    @Override
    public String toString() {
        return "EditOffRequest{" +
                "requestId='" + requestId + '\'' +
                ", off=" + off +
                ", field='" + field + '\'' +
                ", input='" + input + '\'' +
                ", requestType='" + requestType + '\'' +
                '}';
    }
}
