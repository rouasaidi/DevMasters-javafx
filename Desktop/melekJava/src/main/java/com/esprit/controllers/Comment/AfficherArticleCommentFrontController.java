package com.esprit.controllers.Comment;

import com.esprit.models.Article;
import com.esprit.models.Comment;
import com.esprit.models.User;
import com.esprit.services.BadWordsFilter;
import com.esprit.services.CommentService;
import com.esprit.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.time.LocalDate;

public class AfficherArticleCommentFrontController {

    @FXML
    private Label labelArticleTitle;
    @FXML
    private ListView<Comment> listViewComments;
    @FXML
    private Label labelNoCommentSelected;

    private CommentService commentService;
    private Article currentArticle;  // Holds the current article
    private UserService userService;

    public void initialize() {
        commentService = new CommentService();
        userService = new UserService() ;
        listViewComments.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Comment comment, boolean empty) {
                super.updateItem(comment, empty);
                if (empty || comment == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10);
                    VBox vbox = new VBox(5);
                    System.out.println(comment.getUserId());
                    User user = userService.getUserById(comment.getUserId());
                    String userName = user != null ? user.getName() : "Unknown User";
                    Label userNameLabel = new Label(userName);
                    userNameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                    Label commentLabel = new Label(comment.getContent());
                    commentLabel.setStyle("-fx-font-size: 12px;");

                    vbox.getChildren().addAll(userNameLabel, commentLabel);
                    hbox.getChildren().add(vbox);

                    if (comment.getUserId() == 1) {
                        Button editButton = new Button("Edit");
                        editButton.setOnAction(e -> editComment(comment));
                        Button deleteButton = new Button("Delete");
                        deleteButton.setOnAction(e -> deleteComment(comment));
                        hbox.getChildren().addAll(editButton, deleteButton);
                    }

                    setGraphic(hbox);
                }
            }
        });
    }
    private void editComment(Comment comment) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Edit Comment");
        dialog.setHeaderText("Edit your comment");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextArea textArea = new TextArea(comment.getContent());
        textArea.setWrapText(true);

        dialog.getDialogPane().setContent(textArea);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return textArea.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(content -> {
            if (!content.trim().isEmpty()) {
                comment.setContent(content);
                commentService.modifier(comment);  // Assuming there's an update method in CommentService
                refreshComments();
            }
        });
    }

    public void initializeWithArticle(Article article) {
        currentArticle = article;  // Store the article being viewed
        labelArticleTitle.setText(article.getTitle());
        refreshComments();
    }

    public void addComment(ActionEvent actionEvent) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Add Comment");
        dialog.setHeaderText("Add a new comment to \"" + currentArticle.getTitle() + "\"");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextArea textArea = new TextArea();
        textArea.setPromptText("Enter your comment here...");
        textArea.setWrapText(true);

        dialog.getDialogPane().setContent(textArea);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return textArea.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(content -> {
            if (!content.trim().isEmpty() && !BadWordsFilter.containsBadWords(content)) {
                Comment newComment = new Comment();
                newComment.setArticleId(currentArticle.getId());
                newComment.setUserId(1);
                newComment.setContent(content);
                newComment.setDateCmt(LocalDate.now());
                commentService.ajouter(newComment);
                refreshComments();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Inappropriate Content");
                alert.setHeaderText("Your comment contains inappropriate content.");
                alert.setContentText("Please remove any inappropriate words before submitting your comment.");
                alert.showAndWait();
            }
        });
    }

    private void deleteComment(Comment comment) {
        if (comment != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this comment?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    commentService.supprimer(comment.getId());
                    refreshComments();
                }
            });
        }
    }
    public void deleteComment(ActionEvent actionEvent) {
        Comment selectedComment = listViewComments.getSelectionModel().getSelectedItem();
        if (selectedComment != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this comment?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    commentService.supprimer(selectedComment.getId());
                    refreshComments();
                }
            });
        }
    }

    private void refreshComments() {
        if (currentArticle != null) {
            ObservableList<Comment> comments = FXCollections.observableArrayList(commentService.getCommentsByArticleId(currentArticle.getId()));
            listViewComments.setItems(comments);
            labelNoCommentSelected.setVisible(comments.isEmpty());
            labelNoCommentSelected.setText(comments.isEmpty() ? "No comments found for this article." : "");
        }
    }
}
