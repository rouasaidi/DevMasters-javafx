package controls;

import entites.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.usercrud;

import java.io.IOException;
import java.sql.SQLException;

public class ajouterusercontrol {

    @FXML
    private Button ok;
    @FXML
    private Button back;

    @FXML
    private TextField usercintextfiled;

    @FXML
    private TextField usereamiltextfiled;

    @FXML
    private TextField userimagetextfiled;

    @FXML
    private TextField usernametextfiled;

    @FXML
    private TextField userpasswordtextfiled;

    @FXML
    private TextField userphonetextfiled;

    @FXML
    private TextField userrolesextfiled;
    private final usercrud  usercrud=new usercrud();

        @FXML
        void ajouter(ActionEvent event) throws SQLException {
            if (usernametextfiled.getText().isEmpty() || userpasswordtextfiled.getText().isEmpty() || usereamiltextfiled.getText().isEmpty() ||
                    usercintextfiled.getText().isEmpty() || userphonetextfiled.getText().isEmpty()) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Please fill in all fields.");
                errorAlert.showAndWait();
                return; // Arrêter l'exécution de la méthode si des champs sont vides
            }

            // Validation de la longueur du mot de passe
            if (userpasswordtextfiled.getText().length() < 8) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Password must be at least 8 characters long.");
                errorAlert.showAndWait();
                return; // Arrêter l'exécution de la méthode si le mot de passe est trop court
            }

            // Validation de l'adresse e-mail
            String email = usereamiltextfiled.getText();
            if (!email.matches("^[a-zA-Z0-9._%+-]+@(?:esprit\\.tn|gmail\\.com)$")) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Please enter a valid email address (example@esprit.tn or example@gmail.com).");
                errorAlert.showAndWait();
                return; // Arrêter l'exécution de la méthode si l'adresse e-mail est invalide
            }

            // Validation du numéro de téléphone et du numéro de CIN
            String phoneNumber = userphonetextfiled.getText();
            String cinNumber = usercintextfiled.getText();
            if (phoneNumber.length() != 8 || cinNumber.length() != 8 || !phoneNumber.matches("[0-9]+") || !cinNumber.matches("[0-9]+") || phoneNumber.startsWith("+") || phoneNumber.startsWith("-") || phoneNumber.startsWith("/") || phoneNumber.startsWith("*")) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Please enter a valid 8-digit phone number that does not start with +, -, /, or *.");
                errorAlert.showAndWait();
                return; // Arrêter l'exécution de la méthode si les numéros de téléphone ou de CIN sont invalides
            }
            // Ajout de l'utilisateur si toutes les validations passent
            try {
                usercrud.ajouteruser(new user(Integer.parseInt(phoneNumber), Integer.parseInt(cinNumber), usernametextfiled.getText(), userpasswordtextfiled.getText(), userimagetextfiled.getText(), email, userrolesextfiled.getText()));

                // Afficher une alerte de succès si l'ajout de l'utilisateur est réussi
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("User added successfully!");
                successAlert.showAndWait();
            } catch (SQLException e) {
                // Afficher une alerte d'erreur si une exception SQL est levée
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Failed to add user. SQLException: " + e.getMessage());
                errorAlert.showAndWait();
            }

        }
    @FXML
    void naviguer(ActionEvent event) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/afficheruser.fxml"));
                usernametextfiled.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }



    }
    @FXML
    void back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}



