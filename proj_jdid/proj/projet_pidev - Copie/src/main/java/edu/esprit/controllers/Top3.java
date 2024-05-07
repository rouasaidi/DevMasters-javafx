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
import javafx.scene.control.ListView;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class Top3 implements Initializable {
    @FXML
    private ListView<String> listView;

    @FXML
    void NavigateToAcc(ActionEvent event) {

    }

    @FXML
    void retour_a_listCommande(ActionEvent event) {
        Parent  root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/AfficherCommande.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listView.getScene().setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Get the top 3 commandes based on total_price
            ServiceCommande serviceCommande = new ServiceCommande();
            List<Commande> top3Commandes = serviceCommande.getTop3Commandes();

            // Create an ObservableList of String to hold the afficher() results
            ObservableList<String> commandesAffichees = FXCollections.observableArrayList();

            // Convert each Commande to its afficher() representation and add to the list
            for (Commande commande : top3Commandes) {
                commandesAffichees.add(commande.afficher());
            }

            // Set the items to the ListView
            listView.setItems(commandesAffichees);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while fetching top 3 commandes: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
