package com.example.leila.Services;



import com.example.leila.Models.Donation;
import com.example.leila.Util.DataBase;

<<<<<<< HEAD
=======
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
>>>>>>> 5f185cf (Heeeeeeello)
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
=======
import java.util.*;
>>>>>>> 5f185cf (Heeeeeeello)

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
<<<<<<< HEAD

=======
    public Map<String, Integer> getDonationStatisticsByCategory() throws SQLException {
        Map<String, Integer> quantities = new HashMap<>();
        String query = "SELECT category, SUM(quantity) AS total_quantity FROM donation GROUP BY category";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                int totalQuantity = resultSet.getInt("total_quantity");
                quantities.put(category, totalQuantity);
            }
        }
        return quantities;
    }
    public boolean isFavorie(int id_done,int id_user)throws SQLException{
        String checkReq = "SELECT COUNT(*) FROM donationfavorie WHERE id_done  = ? AND id_user  = ?";
        PreparedStatement checkStmt = connection.prepareStatement(checkReq);
        checkStmt.setInt(1, id_done);
        checkStmt.setInt(2, id_user);
        ResultSet checkResult = checkStmt.executeQuery();
        checkResult.next();
        int count = checkResult.getInt(1);

        if (count > 0) {
            return true;
        }

        return false;
    }
    public void Addfavorie(int id_done,int id_user)throws SQLException{
        String insertReq = "INSERT INTO donationfavorie (id_done , id_user) VALUES (?, ?)";
        PreparedStatement insertStmt = connection.prepareStatement(insertReq);
        insertStmt.setInt(1, id_done);
        insertStmt.setInt(2, id_user);
        insertStmt.executeUpdate();
    }
    public void deletfavorie(int id_done,int id_user)throws  SQLException{
        String req = "DELETE FROM donationfavorie WHERE id_done = ? AND id_user=? ";
        PreparedStatement insertStmt = connection.prepareStatement(req);
        insertStmt.setInt(1, id_done);
        insertStmt.setInt(2, id_user);
        insertStmt.executeUpdate();
    }

    public List<Donation> getUserFavorieliste(int userId)throws SQLException {
        List<Donation> donations = new ArrayList<>();
        String req = "SELECT d.* FROM donation d INNER JOIN donationfavorie df ON d.id = df.id_done WHERE df.id_user = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, userId);
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

    public void sendEmail(String recipient, String subject, String body) {
        // Paramètres SMTP
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "onesfendouli72@gmail.com";
        String password = "rzca mbsx giei gstk";

        // Propriétés de la session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Créer une session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Créer un message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);

            // Ajouter les détails de la réservation dans le corps de l'e-mail
            message.setText(body);

            // Envoyer le message
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public String getusermail(int id){

        String req = "SELECT email FROM user WHERE id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                return res.getString("email");
            } else {
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
>>>>>>> 5f185cf (Heeeeeeello)
}