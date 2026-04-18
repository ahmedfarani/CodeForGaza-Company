package Controller;

import DataBase.StudentDAO;
import Model.Utils;
import Model.student;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AdmissionAdminController implements Initializable {

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
    private Button btn_Delete;
    @FXML
    private Button btn_Edit;
    @FXML
    private Button btn_Search;
    @FXML
    private Button btn_Refresh;
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
    @FXML
    private TableView<student> student_table;
    @FXML
    private TableColumn<student, Integer> col_id;
    @FXML
    private TableColumn<student, String> col_Fname;
    @FXML
    private TableColumn<student, String> col_Lname;
    @FXML
    private TableColumn<student, String> col_Email;
    @FXML
    private TableColumn<student, String> col_Phone;
    @FXML
    private TableColumn<student, String> col_dob;
    @FXML
    private TableColumn<student, String> col_Gender;
    @FXML
    private TableColumn<student, String> col_Course;
    @FXML
    private TableColumn<student, String> col_Address;

    ObservableList<student> list;
    int index;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTable();
        loadCourses();
    }

    private void loadCourses() {
        ObservableList<String> courseList = FXCollections.observableArrayList(StudentDAO.getAllCourseName());
        cb_Course.setItems(courseList);
    }

    @FXML
    private void addStudent(ActionEvent event) {
        if (!isValidation()) {
            showDialog("Error", "Please fill in all the fields!");
            return;
        }

        try {
            String gender = "";
            if (Gender.getSelectedToggle() != null) {
                gender = ((RadioButton) Gender.getSelectedToggle()).getText();
            }

            student s = new student(
                    Integer.parseInt(tf_Id.getText()), tf_Fname.getText(), tf_Lname.getText(),
                    tf_Email.getText(), tf_Phone.getText(), tf_Date.getValue().toString(),
                    gender, cb_Course.getValue(), tf_Address.getText()
            );

            if (StudentDAO.addStudent(s)) {
                showDialog("Success", "Student data saved successfuy!");
                fillTable();
                clearFields();
            } else {
                showDialog("Error", "Failed to save student data. ID might already exist.");
            }
    } catch (NumberFormatException e) {
            showDialog("Error", "Please enter valid numbers in ID field.");
        }
    }
    @FXML
    private void deleteStudent(ActionEvent event) {
        student selectedStudent = student_table.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showDialog("Error", "Please select a student to delete.");
            return;
        }
        Dialog d = new Dialog();
        d.setTitle(title);
        d.setHeaderText("Confirm deletion");
        d.setContentText("Are you sure you want to delete this item?");

        ButtonType yesBtn = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noBtn = new ButtonType("No", ButtonBar.ButtonData.NO);
        d.getDialogPane().getButtonTypes().addAll(yesBtn, noBtn);

        Optional<ButtonType> result = d.showAndWait();
        if (result.isPresent() && result.get() == yesBtn) {
             if (StudentDAO.deleteStudent(selectedStudent.getId())){
                 student_table.getItems().remove(selectedStudent);
                 clearFields();
                 showDialog("Success", "Student deleted successfully!");
             } else {
                 showDialog("Error", "Failed to delete student.");
             }
        }
    }

    @FXML
    private void editStudent(ActionEvent event) {
        getData();
    }

    @FXML
    private void searchTable(ActionEvent event) {
        TextInputDialog tid = new TextInputDialog();
        tid.setTitle(title);
        tid.setHeaderText("Search!");
        tid.setContentText("Enter Student ID");

        tid.showAndWait().ifPresent(input -> {
            try {
                int searchId = Integer.parseInt(input.trim());

                ObservableList<student> data = student_table.getItems();
                student found = null;
                for (student person : data) {
                    if (person.getId() == searchId) {
                        found = person;
                        break;
                    }
                }

                if (found != null) {
                    student_table.getSelectionModel().select(found);
                    student_table.scrollTo(found);
                    getSelected(null);
                } else {
                    showDialog("The person was not found", "There is no person with ID number: " + searchId);
                }

            } catch (NumberFormatException e) {
                showDialog("Error", "Please enter a valid number!");
            }
        });
    }

    @FXML
    private void clear(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void refreshTable(ActionEvent event) {
        fillTable();
    }

    @FXML
    private void getSelected(MouseEvent event) {
        index = student_table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        tf_Id.setText(col_id.getCellData(index).toString());
        tf_Fname.setText(col_Fname.getCellData(index).toString());
        tf_Lname.setText(col_Lname.getCellData(index).toString());
        tf_Email.setText(col_Email.getCellData(index).toString());
        tf_Phone.setText(col_Phone.getCellData(index).toString());
        tf_Date.setValue(LocalDate.parse(col_dob.getCellData(index).toString()));
        String gender = col_Gender.getCellData(index).toString();
        if (gender.equals("Male")) {
            Gender.selectToggle(gender_Male);
        } else if (gender.equals("Female")) {
            Gender.selectToggle(gender_Female);
        }
        cb_Course.setValue(col_Course.getCellData(index).toString());
        tf_Address.setText(col_Address.getCellData(index).toString());
    }

    private void getData() {
        student selectedStudent = student_table.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showDialog("Error", "Please select a student to edit.");
            return;
        }

        try {
            String gender = "";
            if (Gender.getSelectedToggle() != null) {
                gender = ((RadioButton) Gender.getSelectedToggle()).getText();
            }

            student s = new student(
                    Integer.parseInt(tf_Id.getText()), tf_Fname.getText(), tf_Lname.getText(),
                    tf_Email.getText(), tf_Phone.getText(), tf_Date.getValue().toString(),
                    gender, cb_Course.getValue(), tf_Address.getText()
            );

            if (StudentDAO.updateStudent(s)) {
                showDialog("Success", "Student data saved successfuy!");
                fillTable();
                clearFields();
            } else {
                showDialog("Error", "Failed to save student data. ID might already exist.");
            }
        } catch (NumberFormatException e) {
            showDialog("Error", "Please enter valid numbers in ID field.");
        }
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
        student_table.getSelectionModel().clearSelection();
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

    private void fillTable() {
        list = FXCollections.observableArrayList(StudentDAO.getAllStudents());
            student_table.setItems(list);
            col_id.setCellValueFactory(new PropertyValueFactory<student, Integer>("id"));
            col_Fname.setCellValueFactory(new PropertyValueFactory<student, String>("f_name"));
            col_Lname.setCellValueFactory(new PropertyValueFactory<student, String>("l_name"));
            col_Email.setCellValueFactory(new PropertyValueFactory<student, String>("email"));
            col_Phone.setCellValueFactory(new PropertyValueFactory<student, String>("phone"));
            col_dob.setCellValueFactory(new PropertyValueFactory<student, String>("date"));
            col_Gender.setCellValueFactory(new PropertyValueFactory<student, String>("gender"));
            col_Course.setCellValueFactory(new PropertyValueFactory<student, String>("course"));
            col_Address.setCellValueFactory(new PropertyValueFactory<student, String>("address"));
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

    @FXML
    private void goToDefinition(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/definition.fxml"));
        Parent root = loader.load();
        DefinitionController controller = loader.getController();
        controller.setPreviousView("/View/admissionAdmin.fxml");
        
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
