// -----------------------------------------------------------------------------
// ---> Student Name: Ahmed Mohammed Al-Farani
// ---> Student ID: 1320236338
// ---> Engeneer Name: Mahmoud Ashour
// ---> Final Project: Code For Gaza Company
// -----------------------------------------------------------------------------
package DataBase;

import java.sql.*;

public class JDBC {

    static public Connection con;
    static public Statement stmt;
    static public ResultSet res;
    static String className = "com.mysql.cj.jdbc.Driver";
    static String url = "jdbc:mysql://127.0.0.1:4306/code_company?user=root&password=";

    public JDBC() {
        try {
            Class.forName(className);
            con = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                System.out.println("Reconnecting to the database...");
                con = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
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

// -----------------------------------------------------------------------------
// ---> Student Name: Ahmed Mohammed Al-Farani
// ---> Student ID: 1320236338
// ---> Engeneer Name: Mahmoud Ashour
// ---> Final Project: Code For Gaza Company
// -----------------------------------------------------------------------------
