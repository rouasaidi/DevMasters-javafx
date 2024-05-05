package controls;

import entites.user;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.usercrud;
import utlis.MyBD;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Userinfo {
    @FXML
    private Button back;
    @FXML
    private Button Disable;

    @FXML
    private Button update;

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
    private user user1 =new user();

    public void initData(String name, String email,String password,String cin,String phone,String roles,String image) {
        usernametextfiled.setText( name);
        usereamiltextfiled.setText( email);
        userpasswordtextfiled.setText( password);
       usercintextfiled.setText( cin );
       userphonetextfiled.setText( phone);
       userrolesextfiled.setText( roles);
       userimagetextfiled.setText( image);



        // Initialisez d'autres champs avec les informations de l'utilisateur selon vos besoins
    }

    private final usercrud up=new usercrud();

    public void update(javafx.event.ActionEvent event) {


        try {
            // Récupérer les valeurs des champs de texte

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
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            up.updatefront(modifiedUser);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/menufront.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Afficher la fenêtre de connexion
            Stage loginStage = new Stage();
            loginStage.setScene(scene);
            loginStage.show();
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void back(javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menufront.fxml"));
            Stage stage = (Stage) back.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public void Disable(javafx.event.ActionEvent event) {
        // Récupérer les informations de l'utilisateur actuellement connecté (par exemple, à partir des champs du formulaire de connexion)
        String email = usereamiltextfiled.getText();
        // Créer un objet utilisateur avec l'email récupéré
        user currentUser = new user();
        currentUser.setEmail(email);

        // Appeler la fonction de suppression en utilisant l'objet utilisateur créé
        try {
            up.deletefront(currentUser);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            // Charger l'interface de connexion (interface de login)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Afficher la fenêtre de connexion
            Stage loginStage = new Stage();
            loginStage.setScene(scene);
            loginStage.show();
        } catch (SQLException e) {
            // Gérer les exceptions liées à la suppression de l'utilisateur
            e.printStackTrace();
            // Afficher un message d'erreur à l'utilisateur si la suppression échoue
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de la suppression de l'utilisateur.");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
