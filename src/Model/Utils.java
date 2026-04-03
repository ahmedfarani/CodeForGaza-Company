// -----------------------------------------------------------------------------
// ---> Student Name: Ahmed Mohammed Al-Farani
// ---> Student ID: 1320236338
// ---> Engeneer Name: Mahmoud Ashour
// ---> Final Project: Code For Gaza Company
// -----------------------------------------------------------------------------
package Model;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyCode;

public class Utils {

    public static void setSceneKeyHandler(Scene s) {
        s.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                Dialog d = new Dialog();
                d.setTitle("Exit Confirmation");
                d.setHeaderText(null);
                d.setContentText("Are You Sure You Want To Exit!");

                ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);

                d.getDialogPane().getButtonTypes().addAll(btnYes, btnNo);

                if (d.showAndWait().get() == btnYes) {
                    Platform.exit();
                }
            }
        });
    }
}

// -----------------------------------------------------------------------------
// ---> Student Name: Ahmed Mohammed Al-Farani
// ---> Student ID: 1320236338
// ---> Engeneer Name: Mahmoud Ashour
// ---> Final Project: Code For Gaza Company
// -----------------------------------------------------------------------------
