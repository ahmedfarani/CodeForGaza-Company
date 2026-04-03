// -----------------------------------------------------------------------------
// ---> Student Name: Ahmed Mohammed Al-Farani
// ---> Student ID: 1320236338
// ---> Engeneer Name: Mahmoud Ashour
// ---> Final Project: Code For Gaza Company
// -----------------------------------------------------------------------------
package Controller;

import Model.Utils;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DefinitionController implements Initializable {

    @FXML
    private Hyperlink link_Insta;
    @FXML
    private Hyperlink link_Face;
    @FXML
    private Button btn_Back;
    @FXML
    private Text descriptionText;

    private String previousView;

    public void setPreviousView(String fxmlPath) {
        this.previousView = fxmlPath;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void insta(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://www.instagram.com/ahmad_farani19"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void face(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://www.facebook.com/ahmad.farani.73"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(previousView));

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
