package tn.esprit.azizapplicationgui.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {
    int id,user_id,quantity;
    String nom,lieu,description,image;
    double total_prix;
    Date date_event;
    public List<Ticket>tickets=new ArrayList<>();

    public Event() {

    }

    public java.sql.Date getDate_event() {
        return (java.sql.Date) date_event;
    }

    public void setDate_event(Date date_event) {
        this.date_event = date_event;
    }



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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getTotal_prix() {
        return total_prix;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", quantity=" + quantity +
                ", nom='" + nom + '\'' +
                ", lieu='" + lieu + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", total_prix=" + total_prix +
                ", date_event=" + date_event +
                '}';
    }

    public void setTotal_prix(double total_prix) {
        this.total_prix = total_prix;
    }


    public Event(int id, int quantity, String nom, String lieu, String description, String image, double total_prix, Date date_event) {
        this.id = id;

        this.quantity = quantity;
        this.nom = nom;
        this.lieu = lieu;
        this.description = description;
        this.image = image;
        this.total_prix = total_prix;
        this.date_event = date_event;
    }

    //without id construct

    public Event( int quantity, String nom, String lieu, String description, String image, double total_prix,Date date_event) {
        this.quantity = quantity;
        this.nom = nom;
        this.lieu = lieu;
        this.description = description;
        this.image = image;
        this.total_prix = total_prix;
        this.date_event = date_event;
    }


}
