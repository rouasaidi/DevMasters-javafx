package tn.esprit.devmasters;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.devmasters.utils.DatabaseConnection;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseConnection.connectDB();

      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/Product.fxml"));  // BACK OFFICE
       // FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/ProductFront.fxml"));   // FRONT OFFICE
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}