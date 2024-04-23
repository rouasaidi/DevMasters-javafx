package controllers;

import Entities.Article;
import Services.ServiceArticle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class AjouterArticleController {

    @FXML
    private TextField nameAr;
    @FXML
    private TextField descAr;
    @FXML
    private TextField typeAr;
    @FXML
    private TextField picAr;

    @FXML
    void ajouterArticle(ActionEvent event)
    {
        String name_article = nameAr.getText();
        String type_article = typeAr.getText();
        String desc_article = descAr.getText();
        String pic_article = picAr.getText();


        ServiceArticle sa = new ServiceArticle();

        Article a1 = new Article(
                name_article,  type_article,  desc_article,  pic_article
        );
        sa.Add(a1);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText("article insérée avec succéss");
        alert.show();
    }

    @FXML
    void afficherArticle(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherArticle.fxml")));
            typeAr.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
