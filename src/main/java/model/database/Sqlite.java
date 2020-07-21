package model.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

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
            String url = "jdbc:sqlite:sqlDataBase/SQL.db";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void saveBuyer(ArrayList<Buyer> buyers) {
        String sqlDelete = "DELETE FROM buyer";
        try {
            conn.prepareStatement(sqlDelete).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                HashMap<String, Double> codedDiscountId = (HashMap) this.stringToObject(rs.getString(5), HashMap.class);
                HashMap<String, Integer> codedDiscountId2 = new HashMap<>();
                for (String key : codedDiscountId.keySet()) {
                    codedDiscountId2.put(key, codedDiscountId.get(key).intValue());
                }
                ArrayList<String> buyOrdersId = (ArrayList<String>) this.stringToObject(rs.getString(6), ArrayList.class);
                HashMap<String, String> filesId = (HashMap<String, String>) this.stringToObject(rs.getString(7), HashMap.class);
                new Buyer(username, userPersonalInfo, balance, sumOfPaymentForCoddedDiscount, codedDiscountId2, buyOrdersId, filesId);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveSeller(ArrayList<Seller> sellers) {
        String sqlDelete = "DELETE FROM seller";
        try {
            conn.prepareStatement(sqlDelete).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "INSERT INTO seller(username,userPersonalInfo,companyName,productsNumberWithId,offsId,sellOrdersId,filesId) VALUES(?,?,?,?,?,?,?)";
        for (Seller seller : sellers) {
            String username = seller.getUsername();
            String userPersonalInfo = this.objectToString(seller.getUserPersonalInfo());
            String companyName = seller.getCompany().getName();
            String productsNumberWithId = this.objectToString(seller.getProductsNumberWithId());
            String offsId = this.objectToString(seller.getOffsId());
            String sellOrdersId = this.objectToString(seller.getSellOrdersId());
            String filesId = this.objectToString(seller.getFilesId());
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, userPersonalInfo);
                pstmt.setString(3, companyName);
                pstmt.setString(4, productsNumberWithId);
                pstmt.setString(5, offsId);
                pstmt.setString(6, sellOrdersId);
                pstmt.setString(7, filesId);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadSeller() {
        String sql = "SELECT  username,userPersonalInfo,compnayName,productsNumberWithId,offsId,sellOrdersId,filesId FROM seller";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String username = rs.getString(1);
                UserPersonalInfo userPersonalInfo = (UserPersonalInfo) this.stringToObject(rs.getString(2), UserPersonalInfo.class);
                String compnayName = rs.getString(3);
                HashMap<String, Double> productsId = (HashMap) this.stringToObject(rs.getString(4), HashMap.class);
                HashMap<String, Integer> productsId2 = new HashMap<>();
                for (String key : productsId.keySet()) {
                    productsId2.put(key, productsId.get(key).intValue());
                }
                ArrayList<String> offsId = (ArrayList<String>) this.stringToObject(rs.getString(5), ArrayList.class);
                ArrayList<String> sellOrdersId = (ArrayList<String>) this.stringToObject(rs.getString(6), ArrayList.class);
                ArrayList<String> filesId = (ArrayList<String>) this.stringToObject(rs.getString(7), ArrayList.class);
                new Seller(username, userPersonalInfo, compnayName, productsId2, offsId, sellOrdersId, filesId);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveManager(ArrayList<Manager> managers) {
        String sqlDelete = "DELETE FROM manager";
        try {
            conn.prepareStatement(sqlDelete).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO manager(username,userPersonalInfo) VALUES(?,?)";
        for (Manager manager : managers) {
            String username = manager.getUsername();
            String userPersonalInfo = this.objectToString(manager.getUserPersonalInfo());
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, userPersonalInfo);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadManager(){
        String sql = "SELECT  username,userPersonalInfo FROM manager";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String username = rs.getString(1);
                UserPersonalInfo userPersonalInfo = (UserPersonalInfo) this.stringToObject(rs.getString(2), UserPersonalInfo.class);
                new Manager(username, userPersonalInfo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



}
