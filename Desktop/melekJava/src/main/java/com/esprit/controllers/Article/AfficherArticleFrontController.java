package com.esprit.controllers.Article;

import com.esprit.controllers.Comment.AfficherArticleCommentFrontController;
import com.esprit.models.Article;
import com.esprit.services.ArticleService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import java.util.List;

public class AfficherArticleFrontController {

    @FXML
    private TilePane tilePane;
    private ArticleService articleService;
    @FXML
    private StackPane contentPane;
    private TextField tfTitle, tfContent, tfImage;
    private DatePicker datePicker;
    public void setMainContent(StackPane contentPane) {
        this.contentPane = contentPane;
    }

    public void initialize() {
        articleService = new ArticleService();
        loadArticles();
    }

    private void loadArticles() {
        List<Article> articles = articleService.getAll();
        for (Article article : articles) {
            tilePane.getChildren().add(createArticleCard(article));
        }
    }

    private VBox createArticleCard(Article article) {
        VBox card = new VBox(10);
        card.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 10;");

        ImageView imageView = loadArticleImage(article.getImage());
        imageView.setFitHeight(150);
        imageView.setFitWidth(250);
        imageView.setPreserveRatio(true);

        Label titleLabel = new Label(article.getTitle());
        titleLabel.setStyle("-fx-font-size: 16px;");

        Label contentLabel = new Label(article.getContent());
        contentLabel.setStyle("-fx-font-size: 12px;");

        Label dateLabel = new Label(article.getDate().toString());
        dateLabel.setStyle("-fx-font-size: 12px;");

        Rating rating = null;
        if (articleService.isMostLiked(article.getId())) {
            rating = new org.controlsfx.control.Rating(5);
            rating.setRating(article.getRating());
            rating.setPartialRating(true);
            Rating finalRating = rating;
            rating.setOnMouseClicked(e -> updateArticleRating(article, finalRating.getRating()));
        }
        // Like and Dislike Buttons
        ImageView likeIcon = new ImageView(new Image(getClass().getResource("/images/thumbs-up-40349.png").toExternalForm()));
        likeIcon.setFitHeight(20);
        likeIcon.setFitWidth(20);
        Button likeButton = new Button("", likeIcon);

        ImageView dislikeIcon = new ImageView(new Image(getClass().getResource("/images/thumbs-down-14922.png").toExternalForm()));
        dislikeIcon.setFitHeight(20);
        dislikeIcon.setFitWidth(20);
        Button dislikeButton = new Button("", dislikeIcon);

        Label likeCount = new Label(String.valueOf(article.getLikes()));
        Label dislikeCount = new Label(String.valueOf(article.getDislikes()));

        likeButton.setOnAction(e -> {
            articleService.incrementLikes(article.getId());
            likeCount.setText(String.valueOf(Integer.parseInt(likeCount.getText()) + 1));
        });

        dislikeButton.setOnAction(e -> {
            articleService.incrementDislikes(article.getId());
            dislikeCount.setText(String.valueOf(Integer.parseInt(dislikeCount.getText()) + 1));
        });

        Button showCommentsButton = new Button("Show Comments");
        showCommentsButton.setOnAction(e -> showArticleComments(article));
        int commentCount = articleService.getCommentCount(article.getId());
        Label commentCountLabel = new Label("Comments: " + commentCount);
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editArticle(article));
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteArticle(article));

        HBox buttonBox = new HBox(10, showCommentsButton);
        if (article.getUserId() == 1) {
            buttonBox.getChildren().addAll(editButton, deleteButton);
        }

        HBox likeDislikeBox = new HBox(5, likeButton, likeCount, dislikeButton, dislikeCount);
        card.getChildren().addAll(imageView, titleLabel, contentLabel, dateLabel, likeDislikeBox ,commentCountLabel, buttonBox);
        if (articleService.isMostLiked(article.getId())) {
            card.getChildren().add(rating);
        }
        return card;
    }
    private void updateArticleRating(Article article, double newRating) {
        articleService.updateArticleRating(article.getId(), newRating);
        article.setRating((int) newRating);  // Assuming Article model has a setRating method
        refreshArticles();
    }
    private ImageView loadArticleImage(String imagePathStr) {
        Path imagePath = Path.of("src/main/resources/images/" + imagePathStr);
        ImageView imageView = new ImageView();
        if (Files.exists(imagePath)) {
            imageView.setImage(new Image(imagePath.toUri().toString()));
        } else {
            imageView.setImage(new Image(getClass().getResource("/images/default.png").toExternalForm()));
        }
        return imageView;
    }


    private void editArticle(Article article) {
        Dialog<Article> dialog = createArticleDialog(article);
        dialog.setTitle("Edit Article");
        dialog.showAndWait().ifPresent(updatedArticle -> {
            articleService.modifier(updatedArticle);
            refreshArticles();
        });
    }

    private Dialog<Article> createArticleDialog(Article article) {
        Dialog<Article> dialog = new Dialog<>();
        dialog.setHeaderText("Fill in the details of the article.");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        tfTitle = new TextField(article.getTitle());
        tfContent = new TextField(article.getContent());
        datePicker = new DatePicker(article.getDate());
        tfImage = new TextField(article.getImage());
        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(e -> handleUploadImage());

        grid.add(new Label("Title:"), 0, 0);
        grid.add(tfTitle, 1, 0);
        grid.add(new Label("Content:"), 0, 1);
        grid.add(tfContent, 1, 1);
        grid.add(new Label("Date:"), 0, 2);
        grid.add(datePicker, 1, 2);
        grid.add(new Label("Image:"), 0, 3);
        grid.add(tfImage, 1, 3);
        grid.add(uploadButton, 2, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                article.setTitle(tfTitle.getText());
                article.setContent(tfContent.getText());
                article.setDate(datePicker.getValue());
                article.setImage(tfImage.getText());
                return article;
            }
            return null;
        });

        return dialog;
    }

    private void deleteArticle(Article article) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this article?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                articleService.supprimer(article.getId());
                refreshArticles();
            }
        });
    }

    private void showArticleComments(Article article) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Comment/AfficherCommentFront.fxml"));
            Node commentView = loader.load();
            AfficherArticleCommentFrontController controller = loader.getController();
            controller.initializeWithArticle(article);  // Assuming this method exists
            contentPane.getChildren().setAll(commentView);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load comment view: " + ex.getMessage());
            alert.show();
            ex.printStackTrace(); // Log or handle the error appropriately
        }
    }

    public void AjouterArticle(ActionEvent actionEvent) {
        Dialog<Article> dialog = new Dialog<>();
        dialog.setTitle("Add New Article");
        dialog.setHeaderText("Fill in the details of the new article.");

        // Set the button types.
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().setPrefSize(400,300);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create labels and fields for the article data entry
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField tfTitle = new TextField();
        tfTitle.setPromptText("Title");
        Label titleError = new Label();
        titleError.setTextFill(Color.RED);

        TextField tfContent = new TextField();
        tfContent.setPromptText("Content");
        Label contentError = new Label();
        contentError.setTextFill(Color.RED);

        DatePicker datePicker = new DatePicker(LocalDate.now());
        Label dateError = new Label();
        dateError.setTextFill(Color.RED);

         tfImage = new TextField();
        tfImage.setPromptText("Upload Image");
        Label imageError = new Label();
        imageError.setTextFill(Color.RED);
        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(e -> handleUploadImage());

        // Add to grid
        grid.add(new Label("Title:"), 0, 0);
        grid.add(tfTitle, 1, 0);
        grid.add(titleError, 2, 0);

        grid.add(new Label("Content:"), 0, 1);
        grid.add(tfContent, 1, 1);
        grid.add(contentError, 2, 1);

        grid.add(new Label("Date:"), 0, 2);
        grid.add(datePicker, 1, 2);
        grid.add(dateError, 2, 2);

        grid.add(new Label("Image:"), 0, 3);
        grid.add(tfImage, 1, 3);
        grid.add(uploadButton, 2, 3);
        grid.add(imageError, 3, 3);

        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true); // Initially disable the save button

        // Add listeners to fields for real-time validation
        tfTitle.textProperty().addListener((obs, old, newV) -> {
            titleError.setText(newV.trim().isEmpty() ? "Title cannot be empty" : "");
            validateForm(saveButton, titleError, contentError, dateError, imageError);
        });
        tfContent.textProperty().addListener((obs, old, newV) -> {
            contentError.setText(newV.trim().isEmpty() ? "Content cannot be empty" : "");
            validateForm(saveButton, titleError, contentError, dateError, imageError);
        });
        datePicker.valueProperty().addListener((obs, old, newV) -> {
            dateError.setText(newV == null ? "Date cannot be empty" : "");
            validateForm(saveButton, titleError, contentError, dateError, imageError);
        });
        tfImage.textProperty().addListener((obs, old, newV) -> {
            imageError.setText(newV.trim().isEmpty() ? "Image path cannot be empty" : "");
            validateForm(saveButton, titleError, contentError, dateError, imageError);
        });

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType && !saveButton.isDisabled()) {
                Article article = new Article();
                article.setTitle(tfTitle.getText());
                article.setContent(tfContent.getText());
                article.setDate(datePicker.getValue());
                article.setImage(tfImage.getText());
                article.setUserId(1);  // Hardcoded user ID
                article.setLikes(0);  // Initialized to 0
                article.setDislikes(0);  // Initialized to 0
                return article;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(article -> {
            articleService.ajouter(article);
            refreshArticles();
        });
    }

    private void validateForm(Node saveButton, Label... errorLabels) {
        for (Label label : errorLabels) {
            if (!label.getText().isEmpty()) {
                saveButton.setDisable(true);
                return;
            }
        }
        saveButton.setDisable(false);
    }

    private void refreshArticles() {
        tilePane.getChildren().clear(); // Clear the existing articles
        loadArticles(); // Reload the articles from the database including the new one
    }
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                Path resourcesDir = Path.of("src/main/resources/images");
                if (!Files.exists(resourcesDir)) {
                    Files.createDirectories(resourcesDir);
                }
                Path imagePath = resourcesDir.resolve(selectedFile.getName());
                Files.copy(selectedFile.toPath(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                tfImage.setText(selectedFile.getName());
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to upload image.");
                alert.showAndWait();
            }
        }
    }
}
