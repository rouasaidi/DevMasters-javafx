package edu.esprit.controllers;
import edu.esprit.entities.Commande;
import edu.esprit.entities.Panier;
import edu.esprit.services.ServiceCommande;
import edu.esprit.services.ServicePanier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.sql.SQLException;
public class ModifierPanierController {
    @FXML
    private TextField TF_total_quant;
    @FXML
    private TextField TF_id;

    @FXML
    void afficher(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/AfficherPanier.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TF_total_quant.getScene().setRoot(root);
    }
    public void initData(Panier panier) {
        if (panier != null) {
            TF_total_quant.setText(String.valueOf(panier.getTotal_quant()));
            TF_id.setText(String.valueOf(panier.getId()));

        }
    }
    @FXML
    void ok(ActionEvent event) {
        int id = Integer.parseInt(TF_id.getText());
        ServicePanier sp = new ServicePanier();

        int total_quant = Integer.parseInt(TF_total_quant.getText());
        if (total_quant<0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("total_quant ne peut pas etre negatif !");
            alert.showAndWait();
            return;
        }
        try {
            sp.modifier_quant_parid(id,total_quant);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("GG");
            alert.setHeaderText(null);
            alert.setContentText("MODIFICATION EFFECTUE!");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
