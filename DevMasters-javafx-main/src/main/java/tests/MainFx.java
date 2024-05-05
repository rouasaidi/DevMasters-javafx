package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage)  {
       // FXMLLoader loader =new FXMLLoader(getClass().getResource("/ajouteruser.fxml"));
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/login.fxml"));

        Parent root;
        try {
            root = loader.load();
            Scene scene =new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("ajouter");
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }
}
