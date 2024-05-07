package edu.esprit.services;

import edu.esprit.entities.Panier;
import edu.esprit.utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicePanier implements IService<Panier> {
    Connection connection = MyDatabase.getInstance().getConnection();

    @Override
    public void ajouter(Panier panier) throws SQLException {
        String query = "INSERT INTO panier (user_id, total_price, total_quant, status, confirmed, commande_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, panier.getUser_id());
            statement.setInt(2, panier.getTotal_price());
            statement.setInt(3, panier.getTotal_quant());
            statement.setInt(4, panier.getStatus());
            statement.setInt(5, panier.getConfirmed());
            statement.setInt(6, panier.getCommande_id());
            statement.executeUpdate();
        }
    }

    @Override
    public void modifier(Panier panier) throws SQLException {
        String query = "UPDATE panier SET user_id=?, total_price=?, total_quant=?, status=?, confirmed=?, commande_id=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, panier.getUser_id());
            statement.setInt(2, panier.getTotal_price());
            statement.setInt(3, panier.getTotal_quant());
            statement.setInt(4, panier.getStatus());
            statement.setInt(5, panier.getConfirmed());
            statement.setInt(6, panier.getCommande_id());
            statement.setInt(7, panier.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM panier WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public List<Panier> recuperer() throws SQLException {
        List<Panier> paniers = new ArrayList<>();
        String query = "SELECT * FROM panier";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Panier panier = new Panier();
                    panier.setId(resultSet.getInt("id"));
                    panier.setUser_id(resultSet.getInt("user_id"));
                    panier.setTotal_price(resultSet.getInt("total_price"));
                    panier.setTotal_quant(resultSet.getInt("total_quant"));
                    panier.setStatus(resultSet.getInt("status"));
                    panier.setConfirmed(resultSet.getInt("confirmed"));
                    panier.setCommande_id(resultSet.getInt("commande_id"));
                    paniers.add(panier);
                }
            }
        }
        return paniers;
    }
    public List<Panier> recuperer_par_id(int id) throws SQLException {
        List<Panier> paniers = new ArrayList<>();
        String query = "SELECT * FROM panier WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Panier panier = new Panier();
                    panier.setId(resultSet.getInt("id"));
                    panier.setUser_id(resultSet.getInt("user_id"));
                    panier.setTotal_price(resultSet.getInt("total_price"));
                    panier.setTotal_quant(resultSet.getInt("total_quant"));
                    panier.setStatus(resultSet.getInt("status"));
                    panier.setConfirmed(resultSet.getInt("confirmed"));
                    panier.setCommande_id(resultSet.getInt("commande_id"));
                    paniers.add(panier);
                }
            }
        }
        return paniers;
    }
    public int getProductidfromPanier(int id) throws SQLException {
        int productId = -1; // Valeur par défaut si aucun produit n'est trouvé
        String query = "SELECT product_id FROM panier_product WHERE panier_id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    productId = resultSet.getInt("product_id");
                }
            }
        }
        return productId;
    }
    public String getProductNameFromProductId(int id) throws SQLException {
        String productName = null; // Valeur par défaut si aucun produit n'est trouvé
        String query = "SELECT name FROM product WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    productName = resultSet.getString("name");
                }
            }
        }
        return productName;
    }
    public int getProductPriceFromProductId(int id) throws SQLException {
        int productPrice = -1; // Valeur par défaut si aucun produit n'est trouvé
        String query = "SELECT price FROM product WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    productPrice = resultSet.getInt("price"); // Correction : récupérer le prix comme un entier
                }
            }
        }
        return productPrice;
    }
    public void modifier_quant_parid(int id, int newQuant) throws SQLException {
        String query = "UPDATE panier SET total_quant = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the new quantity and product id in the prepared statement
            statement.setInt(1, newQuant);
            statement.setInt(2, id);

            // Execute the update
            int rowsAffected = statement.executeUpdate();

            // Optionally, provide feedback to the user based on the result of the update
            if (rowsAffected > 0) {
                System.out.println("Quantity for product with ID " + id + " updated successfully.");
            } else {
                System.out.println("No rows updated. Product with ID " + id + " not found.");
            }
        }
    }
    public void updateTotalPrice(int panier_id, int product_id) throws SQLException {
        // Get the product price using getProductPriceFromProductId
        int productPrice = getProductPriceFromProductId(product_id);

        // Update the total_price column in the panier table
        String query = "UPDATE panier SET total_price = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productPrice);
            statement.setInt(2, panier_id);
            statement.executeUpdate();
        }
    }

    public int calculateTotalPriceForUser(int user_id) throws SQLException {
        int totalPriceSum = 0;
        String query = "SELECT total_price, total_quant FROM panier WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int total_price = resultSet.getInt("total_price");
                    int total_quant = resultSet.getInt("total_quant");
                    totalPriceSum += total_price * total_quant;
                }
            }
        }

        return totalPriceSum;
    }


    public int calculateTotalQuantForUser(int user_id) throws SQLException {
        int totalQuantSum = 0;
        String query = "SELECT total_quant FROM panier WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int total_quant = resultSet.getInt("total_quant");
                    totalQuantSum += total_quant;
                }
            }
        }

        return totalQuantSum;
    }
    public void updateIdCommandeForUser(int userId, int lastCommandeId) throws SQLException {
        String query = "UPDATE panier SET commande_id = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, lastCommandeId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }

    public int getLastCommandeId() throws SQLException {
        int lastCommandeId = -1; // Default value if no commande exists
        String query = "SELECT MAX(id) AS last_id FROM commande";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                lastCommandeId = resultSet.getInt("last_id");
            }
        }
        return lastCommandeId;
    }
    public List<Panier> recuperer_panier_par_commandeid(int commandeId) throws SQLException {
        List<Panier> paniers = new ArrayList<>();
        String query = "SELECT * FROM panier WHERE commande_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, commandeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Panier panier = new Panier();
                    panier.setId(resultSet.getInt("id"));
                    panier.setUser_id(resultSet.getInt("user_id"));
                    panier.setTotal_price(resultSet.getInt("total_price"));
                    panier.setTotal_quant(resultSet.getInt("total_quant"));
                    panier.setStatus(resultSet.getInt("status"));
                    panier.setConfirmed(resultSet.getInt("confirmed"));
                    panier.setCommande_id(resultSet.getInt("commande_id"));
                    paniers.add(panier);
                }
            }
        }
        return paniers;
    }

    public String getEmailfromId(int id )throws SQLException
    {
        String email = null;

        String sql = "SELECT email FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    email = resultSet.getString("email");
                }
            }
        }

        return email;
    }
    public String getNomFromId (int id ) throws SQLException
    {
        String name = null;

        String sql = "SELECT name FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    name = resultSet.getString("email");
                }
            }
        }

        return name;
    }
    public int getCountofelements() throws SQLException
    {
        int count = 0;
        String query = "SELECT COUNT(*) AS count FROM panier";

        try (
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        }

        return count;
    }
    }







