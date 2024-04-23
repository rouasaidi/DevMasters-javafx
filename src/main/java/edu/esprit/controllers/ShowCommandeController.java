package edu.esprit.controllers;

import edu.esprit.entities.Commande;
import edu.esprit.entities.Panier;
import edu.esprit.services.ServicePanier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;


public class ShowCommandeController {

    @FXML
    private TextField TF_date_validation;

    @FXML
    private TextField TF_id;

    @FXML
    private TextField TF_quant;

    @FXML
    private TextField TF_total_price;

    @FXML
    private TextField TF_userid;

    @FXML
    private ListView<Panier> listView;


    @FXML
    void retour(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/AfficherCommandeFront.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TF_id.getScene().setRoot(root);
    }



    public void initData(Commande commande) {
        if (commande != null) {
            ServicePanier sp = new ServicePanier();
            try {
                // Retrieve the list of panier items associated with the given commande
                List<Panier> list = sp.recuperer_panier_par_commandeid(commande.getId());

                // Set the text fields with the commande information
                TF_id.setText(String.valueOf(commande.getId()));
                TF_userid.setText(String.valueOf(commande.getUser_id()));
                TF_total_price.setText(String.valueOf(commande.getTotal_price()));
                TF_quant.setText(String.valueOf(commande.getTotal_quant()));
                TF_date_validation.setText(commande.getDate_validation());

                // Populate the ListView with the retrieved panier items
                ObservableList<Panier> observableList = FXCollections.observableArrayList(list);
                listView.setItems(observableList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*private void chargerPaniers(int commandeId) {
        ServicePanier sp = new ServicePanier();
        List<Panier> list = new ArrayList<>();
        try {
            list = sp.recuperer_panier_par_commandeid(commandeId);
            ObservableList<Panier> observableList = FXCollections.observableArrayList(list);
            listView.setItems(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}

    @Override
 public void initialize(URL url, ResourceBundle resourceBundle) {
      ServicePanier sp = new ServicePanier();
        List<Panier> list = new ArrayList<>();

        try {
            sp.recuperer_panier_par_commandeid(99); // Problème ici
            ObservableList<Panier> observableList = FXCollections.observableArrayList(list);
            listView.setItems(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

}
