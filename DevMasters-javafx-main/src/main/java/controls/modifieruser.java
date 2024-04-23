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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.usercrud;

import java.io.IOException;
import java.sql.SQLException;

public class modifieruser {

    @FXML
    private AnchorPane Ap_modifier;
    @FXML
    private Button back;
    @FXML
    private TextField iduser;

    @FXML
    private Button ok;

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
    private final usercrud ps=new usercrud();
private int ID;
    public void setID(int id,String email ,String name ,int phone ,int cin,String image,String roles ,String password)
    {
        this.iduser.setText(Integer.toString(id));
        this.usereamiltextfiled.setText(email);
        this.usernametextfiled.setText(name);
        this.userphonetextfiled.setText(Integer.toString(phone));
        this.usercintextfiled.setText(Integer.toString(cin));
        this.userimagetextfiled.setText(image);
        this.userpasswordtextfiled.setText(password);


        // Do something with the variable if needed
    }


    @FXML
    void modifieruser(ActionEvent event) {
            try {
                // Récupérer les valeurs des champs de texte
                int id = Integer.parseInt(iduser.getText());
                String email = usereamiltextfiled.getText();
                String name = usernametextfiled.getText();
                int phone = Integer.parseInt(userphonetextfiled.getText());
                int cin = Integer.parseInt(usercintextfiled.getText());
                String image = userimagetextfiled.getText();
                String roles = userrolesextfiled.getText();
                String password = userpasswordtextfiled.getText();

                // Valider les données saisies

                // Vérification si les champs ne sont pas vides
                if (email.isEmpty() || name.isEmpty() || image.isEmpty() || roles.isEmpty() || password.isEmpty()) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Veuillez remplir tous les champs.");
                    errorAlert.showAndWait();
                    return; // Arrêter l'exécution de la méthode si des champs sont vides
                }
                // Validation de l'adresse e-mail

                if (!email.matches("^[a-zA-Z0-9._%+-]+@(?:esprit\\.tn|gmail\\.com)$")) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Please enter a valid email address (example@esprit.tn or example@gmail.com).");
                    errorAlert.showAndWait();
                    return; // Arrêter l'exécution de la méthode si l'adresse e-mail est invalide
                }

                if (userpasswordtextfiled.getText().length() < 8) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Password must be at least 8 characters long.");
                    errorAlert.showAndWait();
                    return; // Arrêter l'exécution de la méthode si le mot de passe est trop court
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

                // Appeler la méthode pour modifier l'utilisateur
                user modifiedUser = new user(name, email, cin, phone, roles, image, password);
                modifiedUser.setId(id);

                ps.modifieruser(modifiedUser);
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("SUCESSCE");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Utilisateur modifié avec succès.");
                errorAlert.showAndWait();
            } catch (NumberFormatException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Veuillez saisir des valeurs numériques valides pour le téléphone et le CIN");
                errorAlert.showAndWait();
            } catch (SQLException e) {


                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Error");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Erreur lors de la modification de l'utilisateur : " + e.getMessage());
                successAlert.showAndWait();
            }
    }

    @FXML
    void back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficheruser.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
