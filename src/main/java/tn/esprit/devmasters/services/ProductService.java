package tn.esprit.devmasters.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.devmasters.interfaces.IService;
import tn.esprit.devmasters.models.Product;
import tn.esprit.devmasters.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductService implements IService<Product> {
    Connection db = DatabaseConnection.getConnection();
    @Override
    public void add(Product product) throws SQLException {
        String query = "INSERT INTO product (name, description, quantity, price, totalsales, image, user_id, categorie_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = db.prepareStatement(query);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, product.getDescription());
        preparedStatement.setInt(3, product.getQuantity());
        preparedStatement.setDouble(4, product.getPrice());
        preparedStatement.setInt(5, product.getTotalSales());
        preparedStatement.setString(6, product.getImage());
        preparedStatement.setInt(7, product.getUserID());
        preparedStatement.setInt(8, product.getCategoryID());

        preparedStatement.executeUpdate();
    }

    @Override
    public void update(Product product) throws SQLException {
        String query = "UPDATE product SET name = ?, description = ?, quantity = ?, price = ?, totalsales = ?, image = ?, user_id = ?, categorie_id = ? WHERE id = ?";
        PreparedStatement preparedStatement = db.prepareStatement(query);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, product.getDescription());
        preparedStatement.setInt(3, product.getQuantity());
        preparedStatement.setDouble(4, product.getPrice());
        preparedStatement.setInt(5, product.getTotalSales());
        preparedStatement.setString(6, product.getImage());
        preparedStatement.setInt(7, product.getUserID());
        preparedStatement.setInt(8, product.getCategoryID());
        preparedStatement.setInt(9, product.getId());

        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Product product) throws SQLException {
        String query = "DELETE FROM product WHERE id = ?";
        PreparedStatement preparedStatement = db.prepareStatement(query);
        preparedStatement.setInt(1, product.getId());

        preparedStatement.executeUpdate();
    }

    @Override
    public Product findById(int id) throws SQLException {
        String query = "SELECT * FROM product WHERE id = ?";
        PreparedStatement preparedStatement = db.prepareStatement(query);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("totalsales"),
                    resultSet.getString("image"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("categorie_id")
            );
        }

        return null;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        String query = "SELECT * FROM product";
        PreparedStatement preparedStatement = db.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Product> products = FXCollections.observableArrayList();

        while (resultSet.next()) {
            products.add(new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("totalsales"),
                    resultSet.getString("image"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("categorie_id")
            ));
        }

        return products;
    }
}
