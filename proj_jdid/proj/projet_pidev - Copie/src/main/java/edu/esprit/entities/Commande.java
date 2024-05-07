package edu.esprit.entities;

import edu.esprit.services.ServiceCommande;
import edu.esprit.services.ServicePanier;

import java.sql.SQLException;
import java.util.Objects;

public class Commande {
    private int id ;
    private int user_id;
    private int total_price;

    private int total_quant;
    public Commande(){};

    public Commande(int id, int user_id, int total_price, int total_quant, String date_validation) {
        this.id = id;
        this.user_id = user_id;
        this.total_price = total_price;
        this.total_quant = total_quant;
        this.date_validation = date_validation;
    }

    public Commande(int user_id, int total_price, int total_quant, String date_validation) {
        this.user_id = user_id;
        this.total_price = total_price;
        this.total_quant = total_quant;
        this.date_validation = date_validation;
    }

    String date_validation ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getTotal_quant() {
        return total_quant;
    }

    public void setTotal_quant(int total_quant) {
        this.total_quant = total_quant;
    }

    public String getDate_validation() {
        return date_validation;
    }

    public void setDate_validation(String date_validation) {
        this.date_validation = date_validation;
    }
    ServicePanier sp ;
   /* String nom_user;

    {
        try {
            nom_user = sp.getNomFromId(user_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    @Override
    public String toString() {

        return

                user_id +
                        "                              " + total_price +
                        "                            " + total_quant +
                        "                                         " + date_validation ;

    }
    public String afficher()
    {
        return

                total_price +
                        "                            " + total_quant +
                        "                                         " + date_validation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commande commande = (Commande) o;
        return id == commande.id && user_id == commande.user_id && total_price == commande.total_price && total_quant == commande.total_quant && Objects.equals(date_validation, commande.date_validation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, total_price, total_quant, date_validation);
    }
}
