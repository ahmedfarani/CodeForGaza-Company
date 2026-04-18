package Controller;

import DataBase.StudentDAO;
import Model.Utils;
import java.sql.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.student;
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

    private StudentDAO studentDAO = new StudentDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCourses();
    }

    private void loadCourses() {
        ObservableList<String> courseList = FXCollections.observableArrayList(studentDAO.getAllCourseName());
        cb_Course.setItems(courseList);
    }

    @FXML
    private void addStudent(ActionEvent event) {
        if (!isValidation()) {
            showDialog("Error", "Please fill in all the fields!");
            return;
        }

        try {String gender = "";
            if (Gender.getSelectedToggle() != null) {
                gender = ((RadioButton) Gender.getSelectedToggle()).getText();
            }

            student s = new student(
                    Integer.parseInt(tf_Id.getText()), tf_Fname.getText(), tf_Lname.getText(),
                    tf_Email.getText(), tf_Phone.getText(), tf_Date.getValue().toString(),
                    gender, cb_Course.getValue(), tf_Address.getText()
            );

            if (studentDAO.addStudent(s)) {
                showDialog("Success", "Student data saved successfuy!");
                clearFields();
            } else {
                showDialog("Error", "Failed to save student data. ID might already exist.");
            }
        } catch (NumberFormatException e) {
            showDialog("Error", "Please enter valid numbers in ID field.");
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
