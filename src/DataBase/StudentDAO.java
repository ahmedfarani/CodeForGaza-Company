package DataBase;

import Model.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public static List<String> getAllCourseName(){
        List<String> courses = new ArrayList<>();
        String query = "SELECT course_name From courses";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet res = pstmt.executeQuery()) {
            while (res.next()){
                courses.add(res.getString("course_name"));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return courses;
    }

    public static List<student> getAllStudents(){
        List<student> list = new ArrayList<>();
        String query = "SELECT * FROM student";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet res = pstmt.executeQuery()){
            while (res.next()){
                list.add(new student(
                        res.getInt("id"), res.getString("F_name"), res.getString("L_name"),
                        res.getString("email"), res.getString("phone"), res.getString("date"),
                        res.getString("gender"), res.getString("course"), res.getString("address")
                ));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public static boolean addStudent(student s){
        String query = "INSERT INTO student (id, F_name, L_name, email, phone, date, gender, course, address) VALUE (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setInt(1, s.getId());
            pstmt.setString(2, s.getF_name());
            pstmt.setString(3, s.getL_name());
            pstmt.setString(4, s.getEmail());
            pstmt.setString(5, s.getPhone());
            pstmt.setString(6, s.getDate());
            pstmt.setString(7, s.getGender());
            pstmt.setString(8, s.getCourse());
            pstmt.setString(9, s.getAddress());

            return pstmt.executeUpdate()>0;
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateStudent(student s){
        String query = "UPDATE student SET f_name=?, l_name=?, email=?, phone=?, date=?, gender=?, course=?, address=? WHERE id=?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setString(1, s.getF_name());
            pstmt.setString(2, s.getL_name());
            pstmt.setString(3, s.getEmail());
            pstmt.setString(4, s.getPhone());
            pstmt.setString(5, s.getDate());
            pstmt.setString(6, s.getGender());
            pstmt.setString(7, s.getCourse());
            pstmt.setString(8, s.getAddress());
            pstmt.setInt(9, s.getId());

            return pstmt.executeUpdate()>0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteStudent(int id){
        String query = "DELETE FROM student WHERE id=?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, id);
            return pstmt.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
