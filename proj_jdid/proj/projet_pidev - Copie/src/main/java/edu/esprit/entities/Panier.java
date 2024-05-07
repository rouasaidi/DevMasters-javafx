package edu.esprit.entities;

import edu.esprit.services.ServicePanier;

import java.sql.SQLException;
import java.util.Objects;

public class Panier {
    private int id;
    private int user_id;
    private int total_price;
    private int total_quant;
    private int status;
    private int confirmed ;
    private int commande_id;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    private String product_name="";

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getCommande_id() {
        return commande_id;
    }

    public void setCommande_id(int commande_id) {
        this.commande_id = commande_id;
    }

    public Panier(int id, int user_id, int total_price, int total_quant, int status, int confirmed, int commande_id) {
        this.id = id;
        this.user_id = user_id;
        this.total_price = total_price;
        this.total_quant = total_quant;
        this.status = status;
        this.confirmed = confirmed;
        this.commande_id = commande_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Panier panier = (Panier) o;
        return id == panier.id && user_id == panier.user_id && total_price == panier.total_price && total_quant == panier.total_quant && status == panier.status && confirmed == panier.confirmed && commande_id == panier.commande_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, total_price, total_quant, status, confirmed, commande_id);
    }

    @Override
    public String toString() {
        ServicePanier sp = new ServicePanier();
        try {
            this.product_name=sp.getProductNameFromProductId(sp.getProductidfromPanier(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            this.total_price=sp.getProductPriceFromProductId(sp.getProductidfromPanier(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return

                 total_quant +
               "                                              "+ total_price*total_quant +"                       "+
                         status +"                             "+
                         product_name ;
    }
    public Panier() {}
}
