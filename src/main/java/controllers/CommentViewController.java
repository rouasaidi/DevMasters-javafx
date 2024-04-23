package controllers;

import Entities.Article;
import Entities.Commentaire;
import Services.ServiceCommentaire;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class CommentViewController {
    @FXML
    private Label articleName;
    @FXML
    private Label articleDetails;
    @FXML
    private ListView<Commentaire> commentList;

    private ServiceCommentaire serviceCommentaire = new ServiceCommentaire();

    // Initialisation des données de l'article sélectionné et chargement des commentaires
    public void initData(Article article) {
        if (article != null) {
            articleName.setText(article.getName_article()); // Assumer que getName_article() est la méthode pour obtenir le nom
            articleDetails.setText("Type: " + article.getType_article() + ", Description: " + article.getDesc_article()); // Idem pour getType_article() et getDesc_article()
            loadComments(article.getId()); // Charger les commentaires basés sur l'ID de l'article
        }
    }

    // Chargement des commentaires de l'article
    private void loadComments(int articleId) {
        List<Commentaire> comments = serviceCommentaire.getComments(articleId);
        commentList.getItems().addAll(comments); // Ajouter les commentaires à la ListView
        // Assurez-vous que la classe Commentaire a une méthode toString() bien définie pour l'affichage ou définissez une CellFactory pour ListView
    }
}
