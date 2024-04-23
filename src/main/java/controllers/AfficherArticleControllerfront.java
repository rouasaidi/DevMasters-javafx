package controllers;

import Entities.Article;
import Services.ServiceArticle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherArticleControllerfront implements Initializable {

    @FXML
    private GridPane cardlayout;

    private ServiceArticle serviceArticle = new ServiceArticle();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadArticles();
    }

    private int column = 0 ;
    private int row = 1 ;

    private void loadArticles() {
        List<Article> articles = serviceArticle.getAll();

        for (Article article : articles)
        {
            try
            {
                // Charge chaque carte d'article et l'ajoute au GridPane
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/card.fxml"));
                HBox card = loader.load();
                CardController controller = loader.getController();
                controller.setArticle(article);
                if (column==1)
                {
                    column=0;
                    ++row ;
                }
                cardlayout.add(card, column++,row);
                GridPane.setMargin(card,new Insets(10));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
