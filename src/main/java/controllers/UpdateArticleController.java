package controllers;

import Entities.Article;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import Services.ServiceArticle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class UpdateArticleController {

    @FXML
    private TextField nameAr;

    @FXML
    private TextField typeAr;

    @FXML
    private TextField descAr;

    @FXML
    private TextField picAr;

    private Article articleToUpdate;
    private ServiceArticle sa = new ServiceArticle();

    public void initData(Article article) {
        articleToUpdate = article;

        nameAr.setText(article.getName_article());
        typeAr.setText(article.getType_article());
        descAr.setText(article.getDesc_article());
        picAr.setText(article.getPic_article());
    }

    @FXML
    void updateArticle(ActionEvent event) {
        if (articleToUpdate != null) {
            articleToUpdate.setName_article(nameAr.getText());
            articleToUpdate.setType_article(typeAr.getText());
            articleToUpdate.setDesc_article(descAr.getText());
            articleToUpdate.setPic_article(picAr.getText());

            sa.Edit(articleToUpdate);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("L'article a été mis à jour avec succès.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Aucun article à mettre à jour.");
            alert.showAndWait();
        }
    }
    @FXML
    void navigatetoAfficher(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherArticle.fxml")));
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}