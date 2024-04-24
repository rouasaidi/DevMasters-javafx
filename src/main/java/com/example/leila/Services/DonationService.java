package com.example.leila.Services;



import com.example.leila.Models.Donation;
import com.example.leila.Util.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonationService implements Iservice<Donation> {

    private Connection connection;

    public DonationService() {
        connection = DataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Donation donation) throws SQLException {
        String req = "INSERT INTO donation (user_id, name, category, description, quantity, date_don, status, image) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, donation.getUser_id());
        os.setString(2, donation.getName());
        os.setString(3, donation.getCategory());
        os.setString(4, donation.getDescription());
        os.setInt(5, donation.getQuantity());
        os.setDate(6, new java.sql.Date(donation.getDate_don().getTime()));
        os.setInt(7, donation.getStatus());
        os.setString(8, donation.getImage());
        os.executeUpdate();
        System.out.println("Donation ajoutée avec succès");
    }


    @Override
    public void modifier(Donation donation) throws SQLException {
        String req = "UPDATE donation SET name = ?, category = ?, description = ?, quantity = ?, date_don = ?, status = ?, image = ? " +
                "WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setString(1, donation.getName());
        os.setString(2, donation.getCategory());
        os.setString(3, donation.getDescription());
        os.setInt(4, donation.getQuantity());
        os.setDate(5, new java.sql.Date(donation.getDate_don().getTime()));
        os.setInt(6, donation.getStatus());
        os.setString(7, donation.getImage());
        os.setInt(8, donation.getId());
        os.executeUpdate();
        System.out.println("Donation modifiée avec succès");
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM donation WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        os.executeUpdate();
        System.out.println("Donation supprimée avec succès");
    }

    @Override
    public Donation getOneById(int id) throws SQLException {
        String req = "SELECT * FROM donation WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        ResultSet rs = os.executeQuery();
        if (rs.next()) {
            Donation donation = new Donation();
            donation.setId(rs.getInt("id"));
            donation.setUser_id(rs.getInt("user_id"));
            donation.setName(rs.getString("name"));
            donation.setCategory(rs.getString("category"));
            donation.setDescription(rs.getString("description"));
            donation.setQuantity(rs.getInt("quantity"));
            donation.setDate_don(rs.getDate("date_don"));
            donation.setStatus(rs.getInt("status"));
            donation.setImage(rs.getString("image"));
            return donation;
        }
        return null;
    }

    @Override
    public List<Donation> getAll() throws SQLException {
        List<Donation> donations = new ArrayList<>();
        String req = "SELECT * FROM donation";
        PreparedStatement os = connection.prepareStatement(req);
        ResultSet rs = os.executeQuery();
        while (rs.next()) {
            Donation donation = new Donation();
            donation.setId(rs.getInt("id"));
            donation.setUser_id(rs.getInt("user_id"));
            donation.setName(rs.getString("name"));
            donation.setCategory(rs.getString("category"));
            donation.setDescription(rs.getString("description"));
            donation.setQuantity(rs.getInt("quantity"));
            donation.setDate_don(rs.getDate("date_don"));
            donation.setStatus(rs.getInt("status"));
            donation.setImage(rs.getString("image"));
            donations.add(donation);
        }
        return donations;
    }

    @Override
    public List<Donation> getByIdUser(int id) throws SQLException {
        List<Donation> donations = new ArrayList<>();
        String req = "SELECT * FROM donation WHERE user_id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        ResultSet rs = os.executeQuery();
        while (rs.next()) {
            Donation donation = new Donation();
            donation.setId(rs.getInt("id"));
            donation.setUser_id(rs.getInt("user_id"));
            donation.setName(rs.getString("name"));
            donation.setCategory(rs.getString("category"));
            donation.setDescription(rs.getString("description"));
            donation.setQuantity(rs.getInt("quantity"));
            donation.setDate_don(rs.getDate("date_don"));
            donation.setStatus(rs.getInt("status"));
            donation.setImage(rs.getString("image"));
            donations.add(donation);
        }
        return donations;
    }

}