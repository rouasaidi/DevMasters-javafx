package controls;

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
import utlis.MyBD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    @FXML
    private Button login;
    @FXML
    private Button inscrit;



    @FXML
    private TextField usereamiltextfiled;



    @FXML
    private TextField userpasswordtextfiled;



    private final services.usercrud usercrud=new usercrud();

    @FXML
    void login(ActionEvent event) throws IOException {
        String email = usereamiltextfiled.getText();
        String password = userpasswordtextfiled.getText();


        // Validation des informations de connexion
        if (usercrud.isValidLogin(email, password)) {


            String name = ""; // Récupérer le nom d'utilisateur depuis la base de données
            String cin ="" ;
            String  phone ="" ; // Récupérer le nom d'utilisateur depuis la base de données
            String roles = "";
            String image = ""; // Récupérer le nom d'utilisateur depuis la base de données
            try (PreparedStatement statement = MyBD.getInstance().getConn().prepareStatement("SELECT * FROM user WHERE email = ?")) {

                statement.setString(1, email); // email est le champ que vous avez récupéré depuis l'interface de connexion

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Si une ligne est trouvée, récupérez le nom d'utilisateur et le CIN
                        name = resultSet.getString("name");
                        cin = resultSet.getString("cin");
                        phone=resultSet.getString("phone");
                        roles=resultSet.getString("roles");
                        image=resultSet.getString("image");



                    } else {
                        // Gérer le cas où aucun utilisateur correspondant à l'email n'est trouvé
                        System.out.println("Aucun utilisateur trouvé avec cet email.");
                    }
                }
            } catch (SQLException e) {
                // Gérer les exceptions liées à l'exécution de la requête SQL
                e.printStackTrace();
            }
            try {
                System.out.println("Valeur des rôles : " + roles);
                if ("[\"ROLE_ADMIN\"]".equals(roles))
                {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);

                        // Ajout d'un message de débogage pour vérifier que la scène est correctement configurée
                        System.out.println("Scène correctement configurée après le chargement de menu.fxml");

                        Stage stage = (Stage) usereamiltextfiled.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        // Gestion des erreurs liées au chargement de la ressource FXML
                        e.printStackTrace();
                    } catch (Exception ex) {
                        // Gestion des autres exceptions qui pourraient se produire lors de la configuration de la scène ou de la fenêtre
                        ex.printStackTrace();
                    }

                }
                else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/userinfo.fxml"));
            Parent root = loader.load();
            Userinfo userinfo = loader.getController();
            userinfo.initData(name,email, password,cin,phone,roles,image); // Supposons que vous avez déjà récupéré l'email et le CIN de l'utilisateur lors de la connexion

            Stage stage = (Stage) usereamiltextfiled.getScene().getWindow();
            stage.setScene(new Scene(root));

            // Authentification réussie
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Login Successful");
            successAlert.setHeaderText(null);
            System.out.println("Login successful! Welcome, " + email + "!");
            successAlert.setContentText("Login successful! Welcome, " + email + "!");
            successAlert.showAndWait();
            // Ici, vous pouvez rediriger l'utilisateur vers une autre interface ou lui donner accès à l'application
            } }catch (IOException e) {
                e.printStackTrace();
            }
        } else {

                // Afficher une boîte de dialogue d'alerte pour informer l'utilisateur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Invalid username or password");
                alert.setContentText("Please check your username and password and try again.");
                alert.showAndWait();

        }

    }

    @FXML
    void inscrit(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/singin.fxml"));
            Stage stage = (Stage) inscrit.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



}
