package model.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Buyer;
import model.Company;
import model.UserPersonalInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Sqlite {
    private Connection conn;
    private GsonBuilder gsonBuilder;
    Gson gson;

    public Sqlite() {
        conn = Sqlite.connect();
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    public String objectToString(Object object) {
        return gson.toJson(object);
    }

    public Object stringToObject(String string, Class type) {
        return gson.fromJson(string, type);
    }

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:sqlDataBase/company.db";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void saveCompany(String name, String info) {
        String sql = "INSERT INTO company(name,info) VALUES(?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, info);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Company> loadCompany() {
        String sql = "SELECT  name, info FROM company";
        ArrayList<Company> allCompanies = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                String info = rs.getString("info");
                allCompanies.add(new Company(name, info));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allCompanies;
    }

    public void saveBuyer(ArrayList<Buyer> buyers) {
        String sql = "INSERT INTO buyer(username,userPersonalInfo,balance,sumOfPaymentForCoddedDiscount,codedDiscountsId,buyOrdersId,filesId) VALUES(?,?,?,?,?,?,?)";
        for (Buyer buyer : buyers) {
            String username = buyer.getUsername();
            String userPersonalInfo = this.objectToString(buyer.getUserPersonalInfo());
            double balance = buyer.getBalance();
            double sumOfPaymentForCoddedDiscount = buyer.getSumOfPaymentForCoddedDiscount();
            String codedDiscountId = objectToString(buyer.getCodedDiscountsId());
            String buyOrdersId = objectToString(buyer.getBuyOrdersId());
            String filesId = objectToString(buyer.getFilesId());
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, userPersonalInfo);
                pstmt.setDouble(3, balance);
                pstmt.setDouble(4, sumOfPaymentForCoddedDiscount);
                pstmt.setString(5, codedDiscountId);
                pstmt.setString(6, buyOrdersId);
                pstmt.setString(7, filesId);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadBuyer() {
        String sql = "SELECT  username,userPersonalInfo,balance,sumOfPaymentForCoddedDiscount,codedDiscountsId,buyOrdersId,filesId FROM buyer";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String username = rs.getString(1);
                UserPersonalInfo userPersonalInfo = (UserPersonalInfo) this.stringToObject(rs.getString(2), UserPersonalInfo.class);
                double balance = rs.getDouble(3);
                double sumOfPaymentForCoddedDiscount = rs.getDouble(4);
                HashMap<String, Double> codedDiscountId = (HashMap)this.stringToObject(rs.getString(5), HashMap.class);
                HashMap<String , Integer> codedDiscountId2 = new HashMap<>();
                for (String key : codedDiscountId.keySet()) {
                    codedDiscountId2.put(key,codedDiscountId.get(key).intValue());
                }
                ArrayList<String> buyOrdersId = (ArrayList<String >)this.stringToObject(rs.getString(6), ArrayList.class);
                HashMap<String,String> filesId = (HashMap<String, String>)this.stringToObject(rs.getString(7), HashMap.class);
                new Buyer(username, userPersonalInfo, balance, sumOfPaymentForCoddedDiscount, codedDiscountId2, buyOrdersId, filesId);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
