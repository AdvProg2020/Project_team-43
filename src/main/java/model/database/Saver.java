package model.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.*;
import java.util.ArrayList;


public class Saver {

    public static void save(ArrayList objects, String address) throws IOException {
        Writer writer = new FileWriter(address);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        gson.toJson(objects, writer);
        writer.close();
    }

}

