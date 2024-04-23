package controllers;

import Entities.Article;
import Services.ServiceArticle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AfficherArticleControllerback {

    @FXML
    private TableView<Article> tableview;
    @FXML
    private TableColumn<Article, String> nameAr;
    @FXML
    private TableColumn<Article, String> descAr;
    @FXML
    private TableColumn<Article, String> typeAr;
    @FXML
    private TableColumn<Article, String> picAr;
    ObservableList<Article> observableList;
    @FXML
    private Label welcomeLBL;
    ServiceArticle sa = new ServiceArticle();


    private ServiceArticle serviceArticle = new ServiceArticle();
    @FXML
    void setData(String param) {
        welcomeLBL.setText("Welcome " + param);
    }


    @FXML
    void initialize() {
        try {
            loadArticlesFromDatabase();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void loadArticlesFromDatabase() throws SQLException {
        List<Article> articleList = sa.getAll();
        observableList = FXCollections.observableList(articleList);

        tableview.setItems(observableList);

        nameAr.setCellValueFactory(new PropertyValueFactory<>("name_article"));
        typeAr.setCellValueFactory(new PropertyValueFactory<>("type_article"));
        descAr.setCellValueFactory(new PropertyValueFactory<>("desc_article"));
        picAr.setCellValueFactory(new PropertyValueFactory<>("pic_article"));
    }
    @FXML
    void navigatetoajouter(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterArticle.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    void deleteArticle(ActionEvent event) {
        Article selectedArticle = tableview.getSelectionModel().getSelectedItem();
        if (selectedArticle != null) {
            int deleteResult = sa.Delete(selectedArticle.getId());
            if (deleteResult > 0) { // Suppression réussie
                observableList.remove(selectedArticle);
                tableview.refresh();  // Rafraîchir la TableView pour afficher les changements
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("L'article a été supprimé avec succès.");
                alert.showAndWait();
            } else { // Échec de la suppression
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Échec");
                alert.setHeaderText(null);
                alert.setContentText("La suppression de l'article a échoué.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un article à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    void navigatetoupdate(ActionEvent event) {
        Article selectedArticle = tableview.getSelectionModel().getSelectedItem();
        if (selectedArticle != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateArticle.fxml"));
                Parent root = loader.load();

                UpdateArticleController updateController = loader.getController();
                updateController.initData(selectedArticle);

                tableview.getScene().setRoot(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un article à mettre à jour.");
            alert.showAndWait();
        }
    }

    @FXML
    public void viewComments()
    {
        Article selectedArticle = tableview.getSelectionModel().getSelectedItem();
        if (selectedArticle != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/commentview.fxml"));
                Parent root = loader.load();

                CommentViewController controller = loader.getController();
                controller.initData(selectedArticle);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("View Comments for " + selectedArticle.getName_article());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
