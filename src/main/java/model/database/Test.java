package model.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Test {
    public static void main(String[] args) throws Exception {
        getConnection();
    }

    public static Connection getConnection() throws Exception {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url1 = "jdbc:mysql://localhost:3306/database";
            String url = "jdbc:mysql://localhost:3306/C:/Users/Parsa/Desktop/Daneshgah/apProject/phase2/sqlDataBase";
            String username = "fuck";
            String password = "pass";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url);
            System.out.println("Connected");
            return conn;
        } catch (Exception e) {
            System.out.println("fuck");
            e.printStackTrace();
        }
        return null;
    }
}
