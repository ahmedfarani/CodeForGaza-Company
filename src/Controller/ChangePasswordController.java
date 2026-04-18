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

public class ChangePasswordController implements Initializable {

    public static String title = "CFG Company";

    @FXML
    private TextField U_name;
    @FXML
    private TextField new_Pass;
    @FXML
    private PasswordField confirm_Pass;
    @FXML
    private Button btn_ChangePassword;
    @FXML
    private TextField current_pass;
    @FXML
    private Hyperlink link_Back;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btnChange(ActionEvent event) {
        String username = U_name.getText();
        String currentPassword = current_pass.getText();
        String newPassword = new_Pass.getText();
        String confirmPassword = confirm_Pass.getText();

        if (!isValidation()) {
            showDialog("Error", "Please fill all the fields.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showDialog("Error", "New passwords do not match.");
            return;
        }

        String encryptedOld = MD5Encryptor.encrypt(currentPassword);
        String encryptedNew = MD5Encryptor.encrypt(newPassword);

        DataBase.UserDAO userDAO = new DataBase.UserDAO();
        boolean success = userDAO.updatePassword(username, encryptedOld, encryptedNew);

        if (success){
            showDialog("Success","Password changed successfuly");
            try {
                backLogin(event);
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            showDialog("Error", "Invalid username or current password.");
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
        if (this.U_name.getText().trim().length() == 0) {
            temp = false;
        }
        if (this.current_pass.getText().trim().length() == 0) {
            temp = false;
        }
        if (this.new_Pass.getText().trim().length() == 0) {
            temp = false;
        }
        if (this.confirm_Pass.getText().trim().length() == 0) {
            temp = false;
        }
        return temp;
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
