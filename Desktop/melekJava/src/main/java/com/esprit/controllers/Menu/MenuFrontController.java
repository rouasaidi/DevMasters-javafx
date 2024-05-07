package com.esprit.controllers.Menu;

import com.esprit.controllers.Article.AfficherArticleFrontController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;


public class MenuFrontController {

    @FXML
    private StackPane contentPane;


    private void loadContent(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Node view = loader.load();

            if (fxml.equals("/Article/AfficherArticleFront.fxml")) {
                AfficherArticleFrontController controller = loader.getController();
                controller.setMainContent(contentPane);
            }

            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onArticleClick(ActionEvent event) {
        loadContent("/Article/AfficherArticleFront.fxml");
    }

}