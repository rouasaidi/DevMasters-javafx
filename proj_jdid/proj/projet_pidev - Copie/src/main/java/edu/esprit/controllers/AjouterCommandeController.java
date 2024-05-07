package edu.esprit.controllers;

import edu.esprit.entities.Commande;
import edu.esprit.services.ServiceCommande;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterCommandeController implements Initializable {

    @FXML
    private TextField TF_date_validation;

    @FXML
    private TextField TFtotal_price;
    @FXML
    private ComboBox<Integer> CBuser_id;

    @FXML
    private TextField TFtotal_quant;

    @FXML
    private TextField TFuser_id;

    @FXML
    void ajouterCommandeAction(ActionEvent event) {
        try {
            int user_id = CBuser_id.getValue(); // Retrieve the selected user ID from the ComboBox
            int total_quant = Integer.parseInt(TFtotal_quant.getText());
            int total_price = Integer.parseInt(TFtotal_price.getText());
            String date_validation = TF_date_validation.getText();
            //controle saisie de la date
            if (!date_validation.matches("\\d{2}-\\d{2}-\\d{4}")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le format de la date doit être JJ-MM-AAAA !");
                alert.showAndWait();
                return;
            }
            //controle saisie de la total_quant/total_price

            if (total_quant < 0 || total_price < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                if (total_quant < 0) {
                    alert.setContentText("Le total de la quantité doit être positif !");
                } else {
                    alert.setContentText("Le total du prix doit être positif !");
                }
                alert.showAndWait();
                return;
            }

            ServiceCommande sc = new ServiceCommande();
            Commande c = new Commande(user_id, total_quant, total_price, date_validation);
            sc.ajouter(c);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Commande ajoutée avec succès !");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir des nombres valides !");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void navigatetoAfficherCommandeAction(ActionEvent event) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/AfficherCommande.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TFtotal_price.getScene().setRoot(root);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ServiceCommande serviceCommande = new ServiceCommande();
            List<Integer> userIds = serviceCommande.recupererUser_id();
            ObservableList<Integer> userIdsObservable = FXCollections.observableArrayList(userIds);
            CBuser_id.setItems(userIdsObservable);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database errors appropriately
            // Optionally display an error message to the user
        }
    }
}


