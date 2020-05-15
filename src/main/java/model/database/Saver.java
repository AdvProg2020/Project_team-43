package model.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Buyer;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Saver {

    public static void save(ArrayList objects, String address) throws IOException {
        Writer writer = new FileWriter(address);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        gson.toJson(objects, writer);
        writer.close();
    }
}

