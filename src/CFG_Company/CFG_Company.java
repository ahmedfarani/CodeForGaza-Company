// -----------------------------------------------------------------------------
// ---> Student Name: Ahmed Mohammed Al-Farani
// ---> Student ID: 1320236338
// ---> Engeneer Name: Mahmoud Ashour
// ---> Final Project: Code For Gaza Company
// -----------------------------------------------------------------------------
package CFG_Company;

import Model.Utils;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CFG_Company extends Application {

    public static String title = "CFG Company";

    public static void main(String[] args) {
        launch(args );
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent rooot;
        try {
            rooot = FXMLLoader.load(getClass().getResource("/View/signin.fxml"));

            Scene s = new Scene(rooot);

            Utils.setSceneKeyHandler(s);

            stage.setScene(s);
            stage.setTitle(title);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}

// -----------------------------------------------------------------------------
// ---> Student Name: Ahmed Mohammed Al-Farani
// ---> Student ID: 1320236338
// ---> Engeneer Name: Mahmoud Ashour
// ---> Final Project: Code For Gaza Company
// -----------------------------------------------------------------------------