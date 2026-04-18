package Controller;

import Model.MD5Encryptor;
import Model.Utils;
import java.sql.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController implements Initializable {

    public static String title = "CFG Company";

    @FXML
    private TextField Uname_SignUp;
    @FXML
    private PasswordField pass_SignUp;
    @FXML
    private Button btn_SignUp;
    @FXML
    private Hyperlink Login_Link;
    @FXML
    private TextField Fname_SignUp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btnSignUp(ActionEvent event) {
        String FullName = Fname_SignUp.getText().trim();
        String username = Uname_SignUp.getText().trim();
        String password = pass_SignUp.getText().trim();

        if (!isValidation()) {
            showDialog("Error", "All fields are required!");
            return;
        }

        String encryptedPassword = MD5Encryptor.encrypt(password);

        DataBase.UserDAO userDAO = new DataBase.UserDAO();

        if (userDAO.isUsernameTaken(username)){
            showDialog("Registration Error","This username already exists. Please choose another username.");
        }

        boolean isRegisterd=userDAO.registerUser(FullName, username, encryptedPassword, "user");

        if (isRegisterd){
            showDialog("Success", "Registration successful!");
            try {
                goToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showDialog("Error", "Failed to register user.");
        }
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

    private boolean isValidation() {
        boolean temp = true;
        if (this.Fname_SignUp.getText().trim().length() == 0) {
            temp = false;
        }
        if (this.Uname_SignUp.getText().trim().length() == 0) {
            temp = false;
        }
        if (this.pass_SignUp.getText().trim().length() == 0) {
            temp = false;
        }
        return temp;
    }

    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/signin.fxml"));

        Scene s = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Utils.setSceneKeyHandler(s);

        stage.setScene(s);
        stage.show();
    }
}
