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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
public class AfficherCommandeFrontController implements Initializable{
    @FXML
    private ListView<Commande> listView;

    int user_id =3 ;

    @FXML
    void DetailsCommande(ActionEvent event) {
        Commande selectedCommande = listView.getSelectionModel().getSelectedItem();
        if (selectedCommande != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowCommande.fxml"));
                Parent root = loader.load();
               ShowCommandeController controller = loader.getController();
                controller.initData(selectedCommande); // Pass selectedCommande to ModifierCommandeController
                Scene scene = listView.getScene();
                scene.setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void NavigateToAcc(ActionEvent event) {




    }

    @FXML
    void supprimerCommande(ActionEvent event) {
        // Retrieve the selected item from the ListView
        Commande commandeSelectionnee = listView.getSelectionModel().getSelectedItem();

        if (commandeSelectionnee != null) {
            try {
                // Delete the selected command
                ServiceCommande sc = new ServiceCommande();
                sc.supprimer(commandeSelectionnee.getId());

                // Update the list after deletion
                listView.getItems().remove(commandeSelectionnee);
            } catch (SQLException e) {
                // Display an error in case of deletion failure
                throw new RuntimeException("Error while deleting the command", e);
            }
        } else {
            // Display an error message if no command is selected
            System.out.println("Please select a command to delete.");
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceCommande sc = new ServiceCommande();
        List<Commande> list = new ArrayList<>();
        try {
            list = sc.recuperer_par_id(user_id);
            ObservableList<Commande> observableCommandes = FXCollections.observableArrayList(list);
            listView.setItems(observableCommandes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void retour_au_panier(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/AfficherPanier.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listView.getScene().setRoot(root);
    }
    }


