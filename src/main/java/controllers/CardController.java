package controllers;

import Entities.Article;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CardController {
    @FXML
    private HBox box;

    @FXML
    private Label desc_article;

    @FXML
    private Label name_article;

    @FXML
    private Label pic_article;

    @FXML
    private Label type_article;
    private Article article;

    private String[] colors = {"#40E0D0", "#20B2AA", "#00CED1", "#4682B4", "#1E90FF", "#87CEFA", "#00BFFF", "#ADD8E6"};


    public void setArticle(Article article) {
        this.article = article;
        if (article != null) {
            name_article.setText(article.getName_article());
            type_article.setText(article.getType_article());
            desc_article.setText(article.getDesc_article());
            pic_article.setText(article.getPic_article());
        }
        String randomColor =colors[(int) (Math.random() * colors.length)];
        box.setStyle("-fx-background-color: " + randomColor +
                "; -fx-background-radius: 15;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
    }

    @FXML
    private void handleSeeMore() {
        if (article != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCommentaire.fxml"));
                Parent root = loader.load();

                AfficherCommentaireController controller = loader.getController();
                controller.setArticle(article);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Article data is not available.");
        }
    }


}