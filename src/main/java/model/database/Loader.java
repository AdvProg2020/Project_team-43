package model.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Company;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Loader {

    public static Object load(Class type, String address) throws FileNotFoundException {
        Reader reader = new FileReader(address);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Object objects = gson.fromJson(reader, type);
        return objects;
    }
}
