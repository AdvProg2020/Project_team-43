package model;

import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Company {
    private static String fileAddress = "database/Company.dat";
    public static ArrayList<Company> allCompanies = new ArrayList<>();

    private String name;
    private String info;


    public Company(String name, String info) {
        this.name = name;
        this.info = info;
        allCompanies.add(this);
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public static Company getCompanyByName(String name) {
        for (Company company : allCompanies) {
            if (company.name.equalsIgnoreCase(name)) {
                return company;
            }
        }
        return null;
    }

    public static boolean hasCompanyWithName(String name) {
        for (Company company : allCompanies) {
            if (company.name.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }


    public static void load() throws FileNotFoundException {
        Company[] companies = (Company[]) Loader.load(Company[].class, fileAddress);
        if (companies != null) {
            allCompanies = new ArrayList<>(Arrays.asList(companies));
        }
    }


    public static void save() throws IOException {
        Saver.save(allCompanies, fileAddress);
    }

    public static ArrayList<Company> getAllCompanies() {
        return allCompanies;
    }
}
