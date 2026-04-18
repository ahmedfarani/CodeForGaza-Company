package Controller;

import Model.MD5Encryptor;
import Model.Utils;
import java.sql.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SigninController implements Initializable {

    public static String title = "CFG Company";

    @FXML
    private TextField Uname_Login;
    @FXML
    private PasswordField pass_Login;
    @FXML
    private CheckBox re_Login;
    @FXML
    private Button btn_Login;
    @FXML
    private Hyperlink SignUp_link;
    @FXML
    private Hyperlink forget_pass_link;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        remember();
    }

    @FXML
    private void login(ActionEvent event) throws IOException {
        String username = Uname_Login.getText();
        String password = pass_Login.getText();

        if (!isValidation()) {
            showDialog("Login Error", "Please enter username and password.");
            return;
        }

        String encryptedPassword=MD5Encryptor.encrypt(password);

        DataBase.UserDAO userDAQ = new DataBase.UserDAO();
        String role=userDAQ.authenticateUser(username, encryptedPassword);

        if (role != null){
            Parent root;
             if ("admin".equalsIgnoreCase(role)){
                 root = FXMLLoader.load(getClass().getResource("/View/admissionAdmin.fxml"));
             } else{
                 root = FXMLLoader.load(getClass().getResource("/View/admissionUser.fxml"));
             }

             Scene s = new Scene(root);
             Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Utils.setSceneKeyHandler(s);

            stage.setScene(s);
            stage.show();
        } else {
            showDialog("Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    private void handleRemember(ActionEvent event) {
        if (re_Login.isSelected()) {
            Preferences prefs = Preferences.userNodeForPackage(getClass());
            prefs.put("rememberedUsername", Uname_Login.getText());
        } else {
            Preferences prefs = Preferences.userNodeForPackage(getClass());
            prefs.remove("rememberedUsername");
        }
    }

    private void remember() {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        String savedUsername = prefs.get("rememberedUsername", "");
        Uname_Login.setText(savedUsername);
        if (!savedUsername.isEmpty()) {
            re_Login.setSelected(true);
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
        if (this.Uname_Login.getText().trim().length() == 0) {
            temp = false;
        }
        if (this.pass_Login.getText().trim().length() == 0) {
            temp = false;
        }
        return temp;
    }

    @FXML
    private void goToForgetPassword(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/ChangePassword.fxml"));

        Scene s = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Utils.setSceneKeyHandler(s);

        stage.setScene(s);
        stage.show();
    }

    @FXML
    private void goToSignUp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/signUp.fxml"));

        Scene s = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Utils.setSceneKeyHandler(s);

        stage.setScene(s);
        stage.show();
    }
}
