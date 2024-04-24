package com.example.leila.Services;

import com.example.leila.Models.Feedback_don;
import com.example.leila.Util.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDonService implements Iservice<Feedback_don> {

    private Connection connection;

    public FeedbackDonService() {
        connection = DataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Feedback_don feedback_don) throws SQLException {
        String req = "INSERT INTO feedback_don (donation_id, user_id, description, date_feedback) " +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, feedback_don.getDonation_id());
        os.setInt(2, feedback_don.getUser_id());
        os.setString(3, feedback_don.getDescription());
        os.setDate(4, new java.sql.Date(feedback_don.getDate_feedback().getTime()));
        os.executeUpdate();
        System.out.println("Feedback ajouté avec succès");
    }

    @Override
    public void modifier(Feedback_don feedback_don) throws SQLException {
        String req = "UPDATE feedback_don SET donation_id = ?, user_id = ?, description = ?, date_feedback = ? " +
                "WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, feedback_don.getDonation_id());
        os.setInt(2, feedback_don.getUser_id());
        os.setString(3, feedback_don.getDescription());
        os.setDate(4, new java.sql.Date(feedback_don.getDate_feedback().getTime()));
        os.setInt(5, feedback_don.getId());
        os.executeUpdate();
        System.out.println("Feedback modifié avec succès");
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM feedback_don WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        os.executeUpdate();
        System.out.println("Feedback supprimé avec succès");
    }

    @Override
    public Feedback_don getOneById(int id) throws SQLException {
        String req = "SELECT * FROM feedback_don WHERE donation_id = ?  ORDER BY id DESC LIMIT 1";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        ResultSet rs = os.executeQuery();
        if (rs.next()) {
            Feedback_don feedback_don = new Feedback_don();
            feedback_don.setId(rs.getInt("id"));
            feedback_don.setDonation_id(rs.getInt("donation_id"));
            feedback_don.setUser_id(rs.getInt("user_id"));
            feedback_don.setDescription(rs.getString("description"));
            feedback_don.setDate_feedback(rs.getDate("date_feedback"));
            return feedback_don;
        }
        return null;
    }

    @Override
    public List<Feedback_don> getAll() throws SQLException {
        List<Feedback_don> feedback_dons = new ArrayList<>();
        String req = "SELECT * FROM feedback_don";
        PreparedStatement os = connection.prepareStatement(req);
        ResultSet rs = os.executeQuery();
        while (rs.next()) {
            Feedback_don feedback_don = new Feedback_don();
            feedback_don.setId(rs.getInt("id"));
            feedback_don.setDonation_id(rs.getInt("donation_id"));
            feedback_don.setUser_id(rs.getInt("user_id"));
            feedback_don.setDescription(rs.getString("description"));
            feedback_don.setDate_feedback(rs.getDate("date_feedback"));
            feedback_dons.add(feedback_don);
        }
        return feedback_dons;
    }

    @Override
    public List<Feedback_don> getByIdUser(int id) throws SQLException {
        List<Feedback_don> feedback_dons = new ArrayList<>();
        String req = "SELECT * FROM feedback_don WHERE donation_id = ? ORDER BY id DESC LIMIT 1";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        ResultSet rs = os.executeQuery();
        while (rs.next()) {
            Feedback_don feedback_don = new Feedback_don();
            feedback_don.setId(rs.getInt("id"));
            feedback_don.setDonation_id(rs.getInt("donation_id"));
            feedback_don.setUser_id(rs.getInt("user_id"));
            feedback_don.setDescription(rs.getString("description"));
            feedback_don.setDate_feedback(rs.getDate("date_feedback"));
            feedback_dons.add(feedback_don);
        }
        return feedback_dons;
    }
    public List<Feedback_don> getAllbydonnation(int id) throws SQLException {
        List<Feedback_don> feedback_dons = new ArrayList<>();
        String req = "SELECT * FROM feedback_don WHERE donation_id = ? ORDER BY id DESC";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        ResultSet rs = os.executeQuery();
        while (rs.next()) {
            Feedback_don feedback_don = new Feedback_don();
            feedback_don.setId(rs.getInt("id"));
            feedback_don.setDonation_id(rs.getInt("donation_id"));
            feedback_don.setUser_id(rs.getInt("user_id"));
            feedback_don.setDescription(rs.getString("description"));
            feedback_don.setDate_feedback(rs.getDate("date_feedback"));
            feedback_dons.add(feedback_don);
        }
        return feedback_dons;
    }
}
