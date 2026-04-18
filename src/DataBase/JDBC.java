package DataBase;

import java.sql.*;

public class JDBC {

    static private Connection con;
    static String className = "com.mysql.cj.jdbc.Driver";
    static String url = "jdbc:mysql://127.0.0.1:4306/code_company?user=root&password=";

    private JDBC() {}

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                System.out.println("Reconnecting to the database...");
                Class.forName(className);
                con = DriverManager.getConnection(url);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }
        return con;
    }

    public static PreparedStatement prepareStatement(String sql) {
        PreparedStatement pstmt = null;
        try {
            pstmt = getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstmt;
    }
}
