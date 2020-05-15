package model.database;

import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class Loader {

    public static Object load(Class type, String address) throws FileNotFoundException {
        Reader reader = new FileReader(address);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Object objects = gson.fromJson(reader, type);
        return objects;
    }
}