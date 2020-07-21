package model.database;

import View.GraphicController.ProductPanelController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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

    public void loadManager() {
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

    public void saveProduct(ArrayList<Product> products) {
        String sqlDelete = "DELETE FROM product";
        try {
            conn.prepareStatement(sqlDelete).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO product(productId,productState,name,companyName,price,visit,date,availableCount,featureMap,description,productScore,comments,categoryName,sellersName) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        for (Product product : products) {
            String productId = product.getProductId();
            String productState = this.objectToString(product.getProductState());
            String name = this.objectToString(product.getName());
            String companyName = product.getCompany().getName();
            double price = product.getPrice();
            int visit = product.getVisit();
            String date = product.getDate().toString();
            int availableCount = product.getAvailableCount();
            String featureMap = this.objectToString(product.getFeaturesMap());
            String description = product.getDescription();
            String productScore = this.objectToString(product.getScore());
            Comment[] commentsArray = (Comment[]) product.getComments().toArray();
            String comments = this.objectToString(commentsArray);
            String categoryName = product.getCategory().getName();
            String sellersName = this.objectToString(product.getSellersName());

            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, productId);
                pstmt.setString(2, productScore);
                pstmt.setString(3, name);
                pstmt.setString(4, companyName);
                pstmt.setDouble(5, price);
                pstmt.setInt(6, visit);
                pstmt.setString(7, date);
                pstmt.setInt(8, availableCount);
                pstmt.setString(9, featureMap);
                pstmt.setString(10, description);
                pstmt.setString(11, productScore);
                pstmt.setString(12, comments);
                pstmt.setString(13, categoryName);
                pstmt.setString(14, sellersName);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadProduct() {
        String sql = "SELECT productId,productState,name,companyName,price,visit,date,availableCount,featureMap,description,productScore,comments,categoryName,sellersName FROM product";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String productId = rs.getString(1);
                State.ProductState productState = (State.ProductState) this.stringToObject(rs.getString(2), State.ProductState.class);
                String name = rs.getString(3);
                String companyName = rs.getString(4);
                double price = rs.getDouble(5);
                int visit = rs.getInt(6);
                String date = rs.getString(7);
                int availableCount = rs.getInt(8);
                HashMap<String, String> featureMap = (HashMap<String, String>) this.stringToObject(rs.getString(9), HashMap.class);
                String description = rs.getString(10);
                ProductScore productScore = (ProductScore) this.stringToObject(rs.getString(11), ProductScore.class);
                Comment[] commentsArray = (Comment[]) this.stringToObject(rs.getString(12), Comment[].class);
                ArrayList<Comment> comments = new ArrayList<>(Arrays.asList(commentsArray));
                String categoryName = rs.getString(13);
                ArrayList<String> sellersName = (ArrayList<String>) this.stringToObject(rs.getString(14), ArrayList.class);
                new Product(productId, productState, name, companyName, price, visit, date, availableCount, featureMap, description, productScore, comments, categoryName, sellersName);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveSellOrder(ArrayList<SellOrder> sellOrders) {
        String sqlDelete = "DELETE FROM sellOrder";
        try {
            conn.prepareStatement(sqlDelete).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO sellOrder(payment,offAmount,productId,buyerUserName,deliveryStatus,number) VALUES(?,?,?,?,?,?)";
        for (SellOrder sellOrder : sellOrders) {
            double payment = sellOrder.getPayment();
            double offAmount = sellOrder.getOffAmount();
            String productId = sellOrder.getProduct().getProductId();
            String buyerUserName = sellOrder.getBuyer().getUsername();
            String deliveryStatus = this.objectToString(sellOrder.getDeliveryStatus());
            int number = sellOrder.getNumber();
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setDouble(1, payment);
                pstmt.setDouble(2, offAmount);
                pstmt.setString(3, productId);
                pstmt.setString(4, buyerUserName);
                pstmt.setString(5, deliveryStatus);
                pstmt.setInt(6, number);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadSellOrder() {
        String sql = "SELECT payment,offAmount,date,productId,buyerUserName,deliveryStatus,number FROM sellOrder";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                double payment = rs.getDouble(1);
                double offAmount = rs.getDouble(2);
                String date = rs.getString(3);
                String productId = rs.getString(4);
                String buyerUserName = rs.getString(5);
                DeliveryStatus deliveryStatus = (DeliveryStatus)this.stringToObject(rs.getString(6), DeliveryStatus.class);
                int number = rs.getInt(7);
                new SellOrder(payment,offAmount,changeDate(date),productId,buyerUserName,deliveryStatus,number);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public Date changeDate(String date){
        Date theSameDate = null;
        try {
            theSameDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return theSameDate;
    }




}



