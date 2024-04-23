package controllers;

import Entities.Article;
import Entities.Commentaire;
import Services.ServiceCommentaire;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;


public class AfficherCommentaireController {
    @FXML
    private Label name_article;
    @FXML
    private Label desc_article;

    @FXML
    private Label pic_article;

    @FXML
    private Label type_article;

    @FXML private TextField desc_comments;
    @FXML private GridPane commentsContainer;


    public void setArticle(Article article) {
        currentArticle = article;
        name_article.setText(article.getName_article());
        type_article.setText(article.getType_article());
        desc_article.setText(article.getDesc_article());
        pic_article.setText(article.getPic_article());
        loadComments();

    }
    private void loadComments() {
        commentsContainer.getChildren().clear(); // Clear existing comments
        if (currentArticle != null) {
            for (Commentaire comment : serviceCommentaire.getComments(currentArticle.getId())) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardcommentaire.fxml"));
                    Node commentNode = loader.load();
                    cardcommentairecontroller controller = loader.getController();
                    controller.setCommentaire(comment);
                    commentsContainer.addRow(commentsContainer.getRowCount(), commentNode);
                } catch (IOException e) {
                    System.err.println("Error loading comment cards: " + e.getMessage());
                }
            }
        }
    }

    private Article currentArticle;
    private ServiceCommentaire serviceCommentaire = new ServiceCommentaire();

    @FXML
    void ajoutcomment() {
        if (currentArticle == null) {
            showAlert("No article selected!", Alert.AlertType.ERROR);
            return;
        }

        String commentDesc = desc_comments.getText().trim();
        if (commentDesc.isEmpty()) {
            showAlert("Comment cannot be empty!", Alert.AlertType.ERROR);
            return;
        }

        Commentaire commentaire = new Commentaire();
        commentaire.setDesc_comments(commentDesc);
        commentaire.setArticle_id(currentArticle); // Ensure the article is not null before this call

        // Attempt to add the comment via the service
        int result = serviceCommentaire.Add(commentaire);
        if (result > 0) {
            showAlert("Comment added successfully!", Alert.AlertType.CONFIRMATION);
            desc_comments.setText(""); // Clear the text field after adding
            loadComments(); // Reload comments to immediately display the new one
        } else {
            showAlert("Failed to add comment.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }

}
