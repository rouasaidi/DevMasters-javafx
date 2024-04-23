package controllers;

import Entities.Commentaire;
import Services.ServiceCommentaire;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane; // Import pour Pane
import javafx.scene.control.Label;

public class cardcommentairecontroller {
    @FXML
    private Label desc_comments;
    private Commentaire commentaire;
    @FXML
    private HBox box;
    private ServiceCommentaire serviceCommentaire = new ServiceCommentaire();

    public void setCommentaire(Commentaire commentaire) {
        this.commentaire = commentaire;
        if (commentaire != null) {
            desc_comments.setText(commentaire.getDesc_comments());
        }
    }

    private void showAlert(String message, Alert.AlertType type)
    {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }

    @FXML
    public void deleteComment(ActionEvent actionEvent) {
        if (commentaire != null && commentaire.getId() > 0) {
            int result = serviceCommentaire.Delete(commentaire.getId());
            if (result > 0) {
                showAlert("Comment deleted successfully!", Alert.AlertType.INFORMATION);
                Platform.runLater(() -> {
                    Node parentNode = box.getParent();
                    if (parentNode instanceof Pane) {
                        ((Pane) parentNode).getChildren().remove(box);
                    }
                });
            } else {
                showAlert("Failed to delete comment.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("No comment selected or comment ID is invalid.", Alert.AlertType.ERROR);
        }
    }


}
