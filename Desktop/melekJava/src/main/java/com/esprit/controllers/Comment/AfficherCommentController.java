package com.esprit.controllers.Comment;

import com.esprit.models.Comment;
import com.esprit.services.CommentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

public class AfficherCommentController {

    @FXML
    private ListView<Comment> commentsListView;
    private ObservableList<Comment> commentsObservableList;

    @FXML
    private Label labelArticleId, labelUserId, labelContent, labelDateCmt;

    private CommentService commentService;
    @FXML
    private StackPane contentPane;

    public void setMainContent(StackPane contentPane) {
        this.contentPane = contentPane;
    }
    @FXML
    public void initialize() {
        commentService = new CommentService();
        commentsObservableList = FXCollections.observableArrayList();
        commentsListView.setItems(commentsObservableList);
        refreshCommentsList();

        commentsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showCommentDetails(newValue);
            }
        });
    }

    private void showCommentDetails(Comment comment) {
        labelArticleId.setText(String.valueOf(comment.getArticleId()));
        labelUserId.setText(String.valueOf(comment.getUserId()));
        labelContent.setText(comment.getContent());
        labelDateCmt.setText(comment.getDateCmt().toString());
    }

    private void refreshCommentsList() {
        commentsObservableList.setAll(commentService.getAll());
    }

    @FXML
    private void deleteComment(ActionEvent event) {
        Comment selected = commentsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this comment?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        commentService.supprimer(selected.getId());
                        refreshCommentsList();  // Refresh the list to show updated data
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Comment deleted successfully!");
                        successAlert.showAndWait();
                    } catch (RuntimeException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Failed to delete comment: " + e.getMessage());
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a comment to delete.");
            alert.showAndWait();
        }
    }

}
