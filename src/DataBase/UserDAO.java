package DataBase;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public String authenticateUser(String username, String encryptedPassword){
        String role=null;
        String query="SELECT role FROM users WHERE username = ? AND password = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt=conn.prepareStatement(query)){

            pstmt.setString(1, username);
            pstmt.setString(2, encryptedPassword);

            try (ResultSet res = pstmt.executeQuery()){
                if (res.next()){
                    role=res.getString("role");
                }
            }
        } catch (SQLException e){
            System.out.println("Database Error durring login: "+ e.getMessage());
        }
        return role;
    }

    public boolean isUsernameTaken(String username){
        String query ="SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = JDBC.getConnection();
            PreparedStatement pstmt= conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            try (ResultSet res=pstmt.executeQuery()){
                if (res.next()&&res.getInt(1)>0){
                    return true;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean registerUser(String fullName, String username, String encryptedPassword, String role){
        String query = "INSERT INTO users (FullName, username,  password, role) VALUE (?,?,?,?)";
        try (Connection conn = JDBC.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, fullName);
            pstmt.setString(2, username);
            pstmt.setString(3, encryptedPassword);
            pstmt.setString(4, role);

            int rowsAffected =pstmt.executeUpdate();
            return rowsAffected>0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword (String username, String oldPassword, String newPassword){
        String checkQuery = "SELECT id FROM users WHERE username =? AND password=?";
        String UpdateQuery="Update users SET password =? WHERE username=?";

        try (Connection conn = JDBC.getConnection()){
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)){
                checkStmt.setString(1, username);
                checkStmt.setString(2, oldPassword);
                try (ResultSet res = checkStmt.executeQuery()){
                    if (!res.next()){
                        return false;
                    }
                }
            }

            try (PreparedStatement updateStmt = conn.prepareStatement(UpdateQuery)){
                updateStmt.setString(1, newPassword);
                updateStmt.setString(2, username);
                return updateStmt.executeUpdate()>0;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
