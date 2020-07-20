package model.database;

import model.Company;

import java.sql.*;
import java.util.ArrayList;

public class Sqlite {
    Connection conn;

    public Sqlite() {
        conn = Sqlite.connect();
    }

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:C:/Users/ASUS/IdeaProjects/newPro/sqlDataBase/company.db";
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

}
