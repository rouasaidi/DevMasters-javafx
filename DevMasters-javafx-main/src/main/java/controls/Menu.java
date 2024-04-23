package controls;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu {

    @FXML
    private Button addmenu;

    @FXML
    private Button affichermenu;

    @FXML
    void addmenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ajouteruser.fxml"));
            Stage stage = (Stage) addmenu.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void affichermenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficheruser.fxml"));
            Stage stage = (Stage) affichermenu.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
