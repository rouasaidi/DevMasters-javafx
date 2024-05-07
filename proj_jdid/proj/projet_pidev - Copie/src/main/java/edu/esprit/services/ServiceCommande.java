package edu.esprit.services;



import edu.esprit.entities.Commande;
import edu.esprit.utils.MyDatabase;

import java.sql.SQLException;
import java.util.List;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommande implements IService<Commande> {
    Connection connection = MyDatabase.getInstance().getConnection();//cnx avec bd



    @Override
    public void ajouter(Commande commande) throws SQLException {
        String query = "INSERT INTO commande (user_id, total_price, total_quant, date_validation) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, commande.getUser_id());
            statement.setInt(2, commande.getTotal_price());
            statement.setInt(3, commande.getTotal_quant());
            statement.setString(4, commande.getDate_validation()); // Utilise directement la chaîne de caractères

            statement.executeUpdate();
        }
    }


    @Override
    public void modifier(Commande commande) throws SQLException {
        String query = "UPDATE commande SET user_id=?, total_price=?, total_quant=?, date_validation=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, commande.getUser_id());
            statement.setInt(2, commande.getTotal_price());
            statement.setInt(3, commande.getTotal_quant());
            statement.setString(4, commande.getDate_validation());
            statement.setInt(5, commande.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM commande WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public Commande getOneByID(int id) throws SQLException {
        String query = "SELECT * FROM commande WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Commande commande = new Commande();
                    commande.setId(resultSet.getInt("id"));
                    commande.setUser_id(resultSet.getInt("user_id"));
                    commande.setTotal_price(resultSet.getInt("total_price"));
                    commande.setTotal_quant(resultSet.getInt("total_quant"));
                    commande.setDate_validation(resultSet.getString("date_validation"));
                    return commande;
                } else {
                    // Handle the case where no Commande with the given ID is found
                    return null;
                }
            }
        }
    }


    @Override
    public List<Commande> recuperer() throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String query = "SELECT * FROM commande";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Commande commande = new Commande();
                commande.setId(resultSet.getInt("id"));
                commande.setUser_id(resultSet.getInt("user_id"));
                commande.setTotal_price(resultSet.getInt("total_price"));
                commande.setTotal_quant(resultSet.getInt("total_quant"));
                commande.setDate_validation(resultSet.getString("date_validation"));
                commandes.add(commande);
            }
        }
        return commandes;
    }

    public List<Integer> recupererUser_id() throws SQLException {
        List<Integer> ids = new ArrayList<>();
        String query = "SELECT id FROM User";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                ids.add(id);
            }
        }
        return ids;
    }
    public List<Commande> recuperer_par_id(int id) throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String query = "SELECT * FROM commande WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id); // Set the user_id parameter
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Commande commande = new Commande();
                    commande.setId(resultSet.getInt("id"));
                    commande.setUser_id(resultSet.getInt("user_id"));
                    commande.setTotal_price(resultSet.getInt("total_price"));
                    commande.setTotal_quant(resultSet.getInt("total_quant"));
                    commande.setDate_validation(resultSet.getString("date_validation"));
                    commandes.add(commande);
                }
            }
        }
        return commandes;
    }
    public List<Commande> getTop3Commandes() throws SQLException {
        List<Commande> top3Commandes = new ArrayList<>();

        // Query to select top 3 commandes based on total_price
        String query = "SELECT * FROM Commande ORDER BY total_price DESC LIMIT 3";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Commande commande = new Commande();
                commande.setId(resultSet.getInt("id"));
                commande.setTotal_price(resultSet.getInt("total_price"));
                commande.setTotal_quant(resultSet.getInt("total_quant"));
                commande.setDate_validation(resultSet.getString("date_validation"));

                top3Commandes.add(commande);
            }
        }

        return top3Commandes;
    }





}
