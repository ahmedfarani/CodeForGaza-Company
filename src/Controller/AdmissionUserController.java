// -----------------------------------------------------------------------------
// ---> Student Name: Ahmed Mohammed Al-Farani
// ---> Student ID: 1320236338
// ---> Engeneer Name: Mahmoud Ashour
// ---> Final Project: Code For Gaza Company
// -----------------------------------------------------------------------------
package Controller;

import DataBase.JDBC;
import Model.Utils;
import java.sql.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AdmissionUserController implements Initializable {

    public static String title = "CFG Company";

    @FXML
    private TextField tf_Id;
    @FXML
    private ComboBox<String> cb_Course;
    @FXML
    private DatePicker tf_Date;
    @FXML
    private ToggleGroup Gender;
    @FXML
    private TextField tf_Fname;
    @FXML
    private TextField tf_Lname;
    @FXML
    private TextField tf_Email;
    @FXML
    private TextField tf_Phone;
    @FXML
    private RadioButton gender_Male;
    @FXML
    private RadioButton gender_Female;
    @FXML
    private TextArea tf_Address;
    @FXML
    private Button btn_Add;
    @FXML
    private Button btn_Clear;
    @FXML
    private Hyperlink Definition_link;
    @FXML
    private Hyperlink back_link;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCourses();
    }

    private void loadCourses() {
        ObservableList<String> courseList = FXCollections.observableArrayList();
        String query = "SELECT course_name FROM courses";

        try {
            JDBC.getConnection();
            PreparedStatement pstmt = JDBC.prepareStatement(query);
            ResultSet res = pstmt.executeQuery();

            while (res.next()) {
                courseList.add(res.getString("course_name"));
            }

            cb_Course.setItems(courseList);

        } catch (SQLException e) {
            System.out.println("Error loading courses: " + e.getMessage());
        }
    }

    @FXML
    private void addStudent(ActionEvent event) {
        if (!isValidation()) {
            showDialog("Error", "Please fill in all the fields!");
            return;
        }

        try {
            Integer id = Integer.parseInt(tf_Id.getText());
            String f_name = tf_Fname.getText();
            String l_name = tf_Lname.getText();
            String email = tf_Email.getText();
            String phone = tf_Phone.getText();
            String date = tf_Date.getValue().toString();
            Toggle selectedToggle = Gender.getSelectedToggle();
            String gender = "";
            if (selectedToggle != null) {
                RadioButton selectedRadio = (RadioButton) selectedToggle;
                gender = selectedRadio.getText();
            }
            String selectedCourse = cb_Course.getValue();
            String address = tf_Address.getText();

            JDBC.getConnection();

            String query = "INSERT INTO student (id, F_name, L_name, email, phone, date, gender, course, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = JDBC.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, f_name);
            pstmt.setString(3, l_name);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.setString(6, date);
            pstmt.setString(7, gender);
            pstmt.setString(8, selectedCourse);
            pstmt.setString(9, address);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                showDialog("Success", "Student data saved successfully!");
                clearFields();
            } else {
                showDialog("Error", "Failed to save student data.");
            }

        } catch (SQLException | NumberFormatException e) {
            showDialog("Database Error", "Error saving data: " + e.getMessage());
        }
    }

    @FXML
    private void clear(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        tf_Id.clear();
        tf_Fname.clear();
        tf_Lname.clear();
        tf_Email.clear();
        tf_Phone.clear();
        tf_Date.setValue(null);
        Gender.selectToggle(null);
        cb_Course.getSelectionModel().clearSelection();
        cb_Course.setButtonCell(new ListCell<>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? cb_Course.getPromptText() : item);
            }
        });
        tf_Address.clear();
    }

    private boolean isValidation() {
        boolean temp = true;
        if (this.tf_Id.getText().trim().length() == 0) {
            temp = false;
        }
        if (this.tf_Fname.getText().trim().length() == 0) {
            temp = false;
        }
        if (this.tf_Lname.getText().trim().length() == 0) {
            temp = false;
        }
        if (this.tf_Email.getText().trim().length() == 0) {
            temp = false;
        }
        if (this.tf_Phone.getText().trim().length() == 0) {
            temp = false;
        }
        if (this.tf_Date.getValue() == null) {
            temp = false;
        }
        if (this.Gender.getSelectedToggle() == null) {
            temp = false;
        }
        if (this.cb_Course.getValue() == null) {
            temp = false;
        }
        if (this.tf_Address.getText().trim().length() == 0) {
            temp = false;
        }
        return temp;
    }

    private void showDialog(String header, String content) {
        Dialog d = new Dialog();
        d.setTitle(title);
        d.setHeaderText(header);
        d.setContentText(content);

        ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        d.getDialogPane().getButtonTypes().add(okBtn);

        d.showAndWait();
    }

    @FXML
    private void goToDefinition(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/definition.fxml"));
        Parent root = loader.load();
        DefinitionController controller = loader.getController();
        controller.setPreviousView("/View/admissionUser.fxml");
        
        Scene s = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Utils.setSceneKeyHandler(s);

        stage.setScene(s);
        stage.show();
    }

    @FXML
    private void backLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/signin.fxml"));

        Scene s = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Utils.setSceneKeyHandler(s);

        stage.setScene(s);
        stage.show();
    }
}

// -----------------------------------------------------------------------------
// ---> Student Name: Ahmed Mohammed Al-Farani
// ---> Student ID: 1320236338
// ---> Engeneer Name: Mahmoud Ashour
// ---> Final Project: Code For Gaza Company
// -----------------------------------------------------------------------------
