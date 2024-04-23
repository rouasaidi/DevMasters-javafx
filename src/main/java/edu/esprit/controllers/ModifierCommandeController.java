package edu.esprit.controllers;

import edu.esprit.entities.Commande;
import edu.esprit.services.ServiceCommande;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.sql.SQLException;
public class ModifierCommandeController {


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


    public void initData(Commande commande) {
        if (commande != null) {
            TF_id.setText(String.valueOf(commande.getId()));
            TF_userid.setText(String.valueOf(commande.getUser_id()));
            TF_total_price.setText(String.valueOf(commande.getTotal_price()));
            TF_quant.setText(String.valueOf(commande.getTotal_quant()));
            TF_date_validation.setText(commande.getDate_validation());
        }
    }

    @FXML
    void afficher(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/AfficherCommande.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TF_quant.getScene().setRoot(root);

    }


    @FXML
    void ok(ActionEvent event) {
        int total_quant;
        int total_price;
        String date_validation = TF_date_validation.getText();

        // Contrôle de saisie de la date
        if (!date_validation.matches("\\d{2}-\\d{2}-\\d{4}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Le format de la date doit être JJ-MM-AAAA !");
            alert.showAndWait();
            return;
        }

        try {
            total_quant = Integer.parseInt(TF_quant.getText());
            total_price = Integer.parseInt(TF_total_price.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir des nombres valides pour la quantité et le prix !");
            alert.showAndWait();
            return;
        }

        // Contrôle de saisie pour total_quant et total_price
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

        Commande c = new Commande();
        c.setId(Integer.parseInt(TF_id.getText()));
        c.setUser_id(Integer.parseInt(TF_userid.getText()));
        c.setTotal_quant(total_quant);
        c.setTotal_price(total_price);
        c.setDate_validation(date_validation);

        ServiceCommande sc = new ServiceCommande();
        try {
            Commande commande = sc.getOneByID(c.getId());
            sc.modifier(c);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modification réussie");
            alert.setContentText("La commande a été modifiée avec succès.");
            alert.show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}







