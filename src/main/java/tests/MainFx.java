package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.UserSession;

import java.io.IOException;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        // FXMLLoader loader =new FXMLLoader(getClass().getResource("/ajouteruser.fxml"));
        //   UserSession session = SessionManager.getInstance().getSession();

      /*  // Ajouter un hook pour enregistrer la session lors de la fermeture de l'application
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Enregistrer la session lors de la fermeture de l'application
            SessionSerializer.serializeSession(session);
        }));*/
       // UserSession session = SessionSerializer.loadSession();
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/login.fxml"));



        Parent root;
        try {
            root = loader.load();
            Scene scene =new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("hello");
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }// Méthode pour ouvrir une page spécifique en fonction de son nom
   /* private void openPage(Stage primaryStage, String ajouteruser) {
        try {
            String resourcePath = "/" + ajouteruser + ".fxml"; // Chemin vers le fichier FXML de la page
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle(ajouteruser); // Définir le titre de la fenêtre sur le nom de la page
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
