package model;

import java.util.ArrayList;

public class Company {
    public static ArrayList<Company> allCompanies = new ArrayList<Company>();
    private String name;
    private String info;

    public Company(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public static Company getCompanyByName(String name) {

        for (Company company : allCompanies) {
            if (company.name.equals(name)) {
                return company;
            }
        }
        return null;
    }

    public static boolean hasCompanyWithName(String name) {

        for (Company company : allCompanies) {
            if (company.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

}
