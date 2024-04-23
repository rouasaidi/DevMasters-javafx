package edu.esprit.controllers;
import edu.esprit.entities.Commande;
import edu.esprit.entities.Panier;
import edu.esprit.services.ServiceCommande;
import edu.esprit.services.ServicePanier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherPanierController implements Initializable {
    @FXML
    private TextField TFrecherche;

    @FXML
    private ListView<Panier> listView;

    @FXML
    private TextField tf_user_id;
    @FXML
    private TextField TFresult;

    @FXML
    void NavigateToAcc(ActionEvent event) {

    }

    @FXML
    void SupprimerPanier(ActionEvent event) {
        // Récupérer l'élément sélectionné dans la ListView
        Panier panierSelectionne = listView.getSelectionModel().getSelectedItem();

        if (panierSelectionne != null) {
            try {
                // Supprimer le panier sélectionné en utilisant son ID
                ServicePanier sp = new ServicePanier();
                sp.supprimer(panierSelectionne.getId());

                // Mettre à jour la liste après suppression
                listView.getItems().remove(panierSelectionne);
            } catch (SQLException e) {
                throw new RuntimeException("Erreur lors de la suppression du panier", e);
            }
        } else {
            // Afficher un message d'erreur si aucun panier n'est sélectionné
            // Vous pouvez utiliser une boîte de dialogue ou une autre méthode pour afficher le message
            System.out.println("Veuillez sélectionner un panier à supprimer.");
        }
    }


    @FXML
    void modifierPanier(ActionEvent event) {
        Panier selectedPanier = listView.getSelectionModel().getSelectedItem();
        if (selectedPanier != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierPanier.fxml"));
                Parent root = loader.load();
                ModifierPanierController controller = loader.getController();
                controller.initData(selectedPanier); // Pass selectedCommande to ModifierCommandeController
                Scene scene = listView.getScene();
                scene.setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServicePanier sp = new ServicePanier();
        List<Panier> list = new ArrayList<>();

        // Assume user_id is already defined or retrieved
        int user_id = 3;
        tf_user_id.setText(String.valueOf(user_id));

        try {
            list = sp.recuperer_par_id(user_id);

            // Iterate through the list and update total_price for each item
            for (Panier panier : list) {
                // Update total_price for the current item
                sp.updateTotalPrice(panier.getId(), sp.getProductidfromPanier(panier.getId()));
            }

            ObservableList<Panier> observableList = FXCollections.observableArrayList(list);
            listView.setItems(observableList);
            System.out.println("sum = "+ sp.calculateTotalPriceForUser(user_id));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void Confirmer(ActionEvent event) {
        Parent root = null;
        ServiceCommande sc = new ServiceCommande();
        ServicePanier sp = new ServicePanier();
        int user_id = Integer.parseInt(tf_user_id.getText());
        LocalDate currentDate = LocalDate.now();

        // Define a formatter for the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");

        // Format the date as a string
        String dateString = currentDate.format(formatter);
        try {
            int prix=sp.calculateTotalPriceForUser(user_id);
            int quant =sp.calculateTotalQuantForUser(user_id);
            Commande commande = new Commande(user_id,prix,quant,dateString);

            sc.ajouter(commande);
         int lastCommandeID = sp.getLastCommandeId();
            sp.updateIdCommandeForUser(user_id,lastCommandeID);

            root = FXMLLoader.load(getClass().getResource("/AfficherCommandeFront.fxml"));



        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
        TFresult.getScene().setRoot(root);
    }
    @FXML
    void Commandes(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/AfficherCommandeFront.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TFresult.getScene().setRoot(root);
    }



}
