package edu.esprit.tests;


import edu.esprit.controllers.EmailClass;
import edu.esprit.entities.Commande;
import edu.esprit.entities.Panier;
import edu.esprit.services.ServiceCommande;
import edu.esprit.services.ServicePanier;
import edu.esprit.utils.MyDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
       /* PersonneService ps =new PersonneService();
      //  try {
           // ps.ajouter(new Personne(27,"khalil","guesmi"));
            //ps.ajouter(new Personne(30,"cristiano","ronaldo"));

        //} catch (SQLException e) {
        //    System.err.println(e.getMessage());
        //}

        try {
            System.out.println(ps.recuperer());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

     //   try {
        //     ps.modifier(new Personne(1,18,"mohsen","kouki"));
        //} catch (SQLException e) {
        //    System.err.println(e.getMessage());
        //  }

        // try {
        //     ps.supprimer(2);
        // } catch (SQLException e) {
        //    System.err.println(e.getMessage());
       // }
*/
        MyDatabase.getInstance();
        /*Commande c = new Commande(3,4556,4444,"2024-02-28");
        ServiceCommande serviceCommande = new ServiceCommande();
        try {
            serviceCommande.ajouter(c);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println(serviceCommande.recuperer());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            serviceCommande.supprimer(51);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServicePanier sp = new ServicePanier();
        try {
            List<Panier> list = sp.recuperer();
            System.out.println("panier"+ list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
        EmailClass test = new EmailClass();
        test.envoyer("kthiri1919@gmail.com");
    }
}
