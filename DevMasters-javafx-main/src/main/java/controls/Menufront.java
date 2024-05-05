package controls;

import entites.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import services.usercrud;
import utlis.MyBD;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Menufront {

    @FXML
    private Button profil;
    @FXML
    private Button logout;

    @FXML
    private Button singin;

    @FXML
    void profil(ActionEvent event) {

        try {
            // Charger l'interface UserInfo
            FXMLLoader userInfoLoader = new FXMLLoader(getClass().getResource("/userinfo.fxml"));
            Parent userInfoRoot = userInfoLoader.load();

            // Récupérer le contrôleur de l'interface UserInfo
            Userinfo userInfoController = userInfoLoader.getController();

            // Passer les informations de l'utilisateur au contrôleur de l'interface UserInfo
            String name = ""; // Récupérer le nom d'utilisateur depuis la base de données
            String cin ="" ;
            String  phone ="" ; // Récupérer le nom d'utilisateur depuis la base de données
            String roles = "";
            String image = "";
            String email = "";
            String password="";

            // Appeler la méthode initData sur le contrôleur de l'interface UserInfo
            userInfoController.initData(name, email, password, cin, phone, roles, image);

            // Afficher l'interface UserInfo dans une nouvelle fenêtre ou un nouveau volet
            Stage userInfoStage = new Stage();
            userInfoStage.setScene(new Scene(userInfoRoot));
            userInfoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    void singin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/singin.fxml"));
            Stage stage = (Stage) singin.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
   /* public user recupererInfosUtilisateur(String email) {
        user user = null;
        try {
            // Établir la connexion à la base de données
            Connection conn = MyBD.getInstance().getConn();

            // Préparer la requête SQL pour récupérer les informations de l'utilisateur
            String sql = "SELECT * FROM user WHERE email = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);

            // Exécuter la requête SQL pour récupérer les informations de l'utilisateur
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Si une ligne est trouvée, créer un objet User avec les informations récupérées
                String name = resultSet.getString("name");
                int cin = resultSet.getInt("cin");
                int phone = resultSet.getInt("phone");
                String roles = resultSet.getString("roles");
                String image = resultSet.getString("image");

                user = new user(name, email, cin, phone, roles, image);
            } else {
                // Gérer le cas où aucun utilisateur correspondant à l'email n'est trouvé
                System.out.println("Aucun utilisateur trouvé avec cet email : " + email);
            }
        } catch (SQLException e) {
            // Gérer les exceptions liées à l'exécution de la requête SQL
            e.printStackTrace();
        }
        return user;
    }
*/


    @FXML
    void logout(ActionEvent event) throws IOException {
        // Fermer la fenêtre du menu
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
        // Afficher à nouveau l'écran de connexion
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent loginRoot = loginLoader.load();
        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(loginRoot));
        loginStage.show();

    }
}

