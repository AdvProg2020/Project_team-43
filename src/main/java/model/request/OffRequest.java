package model.request;

import model.Manager;
import model.Off;
import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class OffRequest extends Request {
    private static final String fileAddress = "database/OffRequest.dat";
    private transient Off off;
    private String offId;

    public OffRequest(Off off) {
        super("off");
        this.off = off;
    }

    public Off getOff() {
        return off;
    }

    public static void load() throws FileNotFoundException {
        OffRequest[] offRequests = (OffRequest[]) Loader.load(OffRequest[].class, fileAddress);
        if (offRequests != null) {
            Request.addAll(new ArrayList<>(Arrays.asList(offRequests)));
        }
    }

    public static void save() throws IOException {
        ArrayList<OffRequest> offRequests = new ArrayList<>();
        for (Request request : allRequests) {
            if (request.getRequestType().equals("off")) {
                offRequests.add((OffRequest) request);
            }
        }
        Saver.save(offRequests, fileAddress);

    }

    public static void loadFields() {
        loadAllOffs();
    }

    public static void saveFields() {
        saveAllOffs();
    }

    public static void loadAllOffs() {
        for (Request request : allRequests) {
            if (request.getRequestType().equals("off")) {
                ((OffRequest) request).loadOff();
            }
        }

    }

    public void loadOff() {
        off = Off.getAllOffById(offId);
    }

    public static void saveAllOffs() {
        for (Request request : allRequests) {
            if (request.getRequestType().equals("off")) {
                ((OffRequest) request).saveOff();
            }
        }
    }

    public void saveOff() {
        offId = off.getOffId();
    }

    @Override
    public String toString() {
        return "OffRequest{" +
                ", requestId='" + requestId + '\'' +
                "off=" + off +
                ", requestType='" + requestType + '\'' +
                '}';
    }
}
