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
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class AfficherCommandeController implements Initializable {

    @FXML
    private TextField TFrecherche;

    @FXML
    private ListView<Commande> listView;

    @FXML
    void NavigateToAcc(ActionEvent event) {

    }

    @FXML
    void ajouterCommande(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/AjouterCommande.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TFrecherche.getScene().setRoot(root);

    }

    @FXML
    void modifierCommande(ActionEvent event) {
        Commande selectedCommande = listView.getSelectionModel().getSelectedItem();
        if (selectedCommande != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCommande.fxml"));
                Parent root = loader.load();
                ModifierCommandeController controller = loader.getController();
                controller.initData(selectedCommande); // Pass selectedCommande to ModifierCommandeController
                Scene scene = listView.getScene();
                scene.setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @FXML
    void supprimerCommande(ActionEvent event) {
        Commande selectedcommande = listView.getSelectionModel().getSelectedItem();
        if (selectedcommande != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette commande?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    int id = selectedcommande.getId();
                    ServiceCommande serviceCommande = new ServiceCommande();
                    serviceCommande.supprimer(id);
                    listView.getItems().remove(selectedcommande);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceCommande serviceCommande = new ServiceCommande();
        try {
            // Récupérer les commandes sous forme d'une ArrayList
            List<Commande> commandes = serviceCommande.recuperer();
            // Convertir l'ArrayList en ObservableList
            ObservableList<Commande> observableCommandes = FXCollections.observableArrayList(commandes);
            // Définir les éléments de la ListView avec l'ObservableList
            listView.setItems(observableCommandes);

            // Ajouter un ChangeListener à la TFrecherche pour détecter les changements de texte
            TFrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
                // Appeler la méthode filtrerCommandes avec le nouveau texte de recherche
                filtrerCommandes(newValue);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void filtrerCommandes(String recherche) {
        // Si la recherche est vide, afficher toutes les commandes
        if (recherche.isEmpty()) {
            try {
                // Récupérer toutes les commandes
                ServiceCommande serviceCommande = new ServiceCommande();
                List<Commande> commandes = serviceCommande.recuperer();
                // Convertir en ObservableList et définir dans la ListView
                ObservableList<Commande> observableCommandes = FXCollections.observableArrayList(commandes);
                listView.setItems(observableCommandes);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        // Filtrer les commandes en fonction du user_id de recherche
        ObservableList<Commande> commandesFiltrees = FXCollections.observableArrayList();
        for (Commande commande : listView.getItems()) {
            if (String.valueOf(commande.getUser_id()).equals(recherche)) {
                commandesFiltrees.add(commande);
            }
        }

        // Afficher les commandes filtrées dans la ListView
        listView.setItems(commandesFiltrees);
    }
    @FXML
    void tri_par_prx_asc(ActionEvent event) {
        try {
            // Récupérer les commandes sous forme d'une ArrayList
            ServiceCommande serviceCommande = new ServiceCommande();
            List<Commande> commandes = serviceCommande.recuperer();

            // Trier les commandes par prix de manière ascendante
            trierParPrixAsc(commandes);

            // Convertir l'ArrayList triée en ObservableList
            ObservableList<Commande> observableCommandes = FXCollections.observableArrayList(commandes);

            // Mettre à jour la ListView avec les commandes triées
            listView.setItems(observableCommandes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void tri_par_prx_desc(ActionEvent event) {
        try {
            // Récupérer les commandes sous forme d'une ArrayList
            ServiceCommande serviceCommande = new ServiceCommande();
            List<Commande> commandes = serviceCommande.recuperer();

            // Trier les commandes par prix de manière descendante
            trierParPrixDesc(commandes);

            // Convertir l'ArrayList triée en ObservableList
            ObservableList<Commande> observableCommandes = FXCollections.observableArrayList(commandes);

            // Mettre à jour la ListView avec les commandes triées
            listView.setItems(observableCommandes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void trierParPrixAsc(List<Commande> commandes) {
        Collections.sort(commandes, Comparator.comparingInt(Commande::getTotal_price));
    }

    private void trierParPrixDesc(List<Commande> commandes) {
        Collections.sort(commandes, (c1, c2) -> Integer.compare(c2.getTotal_price(), c1.getTotal_price()));
    }




}



