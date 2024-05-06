package controls;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.UserSession;
import services.usercrud;
import utlis.MyBD;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.Paths;

public class Login {

    @FXML
    private Button login;
    @FXML
    private Button inscrit;
    @FXML
    private Button qccode;

    @FXML
    private Button forgetpaswword;

    @FXML
    private TextField usereamiltextfiled;


    @FXML
    private PasswordField userpasswordtextfiled;
/*
    @FXML
    private TextField userpasswordtextfiled;*/
    @FXML
    private CheckBox showpassword;



    private final services.usercrud usercrud=new usercrud();

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    @FXML
    void login(ActionEvent event) throws IOException {

        String email = usereamiltextfiled.getText();
        String password = userpasswordtextfiled.getText();
        String hashedPassword = hashPassword(password);


        // Validation des informations de connexion
        if (usercrud.isValidLogin(email, password)) {



            String name = ""; // Récupérer le nom d'utilisateur depuis la base de données
            String cin ="" ;
            String  phone ="" ; // Récupérer le nom d'utilisateur depuis la base de données
            String roles = "";
            String image = ""; // Récupérer le nom d'utilisateur depuis la base de données

            //String page="";
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
                       // page= resultSet.getString("page");
                         boolean isBanned = resultSet.getBoolean("is_banned");
                        if (isBanned) {
                            // Afficher une alerte indiquant que l'utilisateur est banni
                            Alert bannedAlert = new Alert(Alert.AlertType.WARNING);
                            bannedAlert.setTitle("Banned User");
                            bannedAlert.setHeaderText(null);
                            bannedAlert.setContentText("You are banned! Please contact support for further assistance.");
                            bannedAlert.showAndWait();
                            return; // Arrêter l'exécution de la méthode car l'utilisateur est banni
                        }




                    } else {
                        // Gérer le cas où aucun utilisateur correspondant à l'email n'est trouvé
                        System.out.println("Aucun utilisateur trouvé avec cet email.");
                    }
                }
            } catch (SQLException e) {
                // Gérer les exceptions liées à l'exécution de la requête SQL
                e.printStackTrace();
            }
            // Créer une session utilisateur
           // UserSession session = new UserSession(name, email, roles, image);

            // Stocker la session utilisateur
            //SessionManager.getInstance().setSession(session);

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
    @FXML
    void forgetpassword(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/codeverification.fxml"));
            Stage stage = (Stage) inscrit.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void qccode(ActionEvent event) {
        try {
            String data = "http://127.0.0.1:8000/signup";
            String path = "C:\\Users\\jmili\\IdeaProjects\\DevMaster\\QR2.jpg";
            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
            MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
            System.out.println("QR Code généré avec succès.");

            // Afficher une alerte de succès
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("QR Code généré");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Le QR Code a été généré avec succès !");
            successAlert.showAndWait();
        } catch (IOException | WriterException e) {
            System.out.println("Une erreur est survenue lors de la génération du code QR : " + e.getMessage());

            // Afficher une alerte d'erreur
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Une erreur est survenue lors de la génération du QR Code : " + e.getMessage());
            errorAlert.showAndWait();
        }
    }


    @FXML
    void showpassword(ActionEvent event) {
        if (showpassword.isSelected()) {
            userpasswordtextfiled.setText(userpasswordtextfiled.getText());
        } else {
            userpasswordtextfiled.setText("");
        }
    }
    @FXML
    void userpasswordtextfiled(ActionEvent event) {

    }

    public boolean verifierMotDePasse(String email, String motDePasseEntree) {
        // Récupérer le mot de passe haché correspondant à l'email depuis la base de données
        String motDePasseHache = usercrud.getHashedPasswordByEmail(email);

        // Vérifier si le mot de passe fourni correspond au mot de passe haché récupéré
        return motDePasseHache != null && BCrypt.checkpw(motDePasseEntree, motDePasseHache);
    }

}
