package tn.esprit.devmasters.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.devmasters.interfaces.IService;
import tn.esprit.devmasters.models.Category;
import tn.esprit.devmasters.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryService implements IService<Category> {
    Connection db = DatabaseConnection.getConnection();

    @Override
    public void add(Category category) throws SQLException {
        String query = "INSERT INTO categorie (user_id, type, image) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = db.prepareStatement(query);
        preparedStatement.setInt(1, category.getUserID());
        preparedStatement.setString(2, category.getType());
        preparedStatement.setString(3, category.getImage());

        preparedStatement.executeUpdate();
    }

    @Override
    public void update(Category category) throws SQLException {
        String query = "UPDATE categorie SET user_id = ?, type = ?, image = ? WHERE id = ?";
        PreparedStatement preparedStatement = db.prepareStatement(query);
        preparedStatement.setInt(1, category.getUserID());
        preparedStatement.setString(2, category.getType());
        preparedStatement.setString(3, category.getImage());
        preparedStatement.setInt(4, category.getId());

        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Category category) throws SQLException {
        String query = "DELETE FROM categorie WHERE id = ?";
        PreparedStatement preparedStatement = db.prepareStatement(query);
        preparedStatement.setInt(1, category.getId());

        preparedStatement.executeUpdate();
    }

    @Override
    public Category findById(int id) throws SQLException {
        String query = "SELECT * FROM categorie WHERE id = ?";
        PreparedStatement preparedStatement = db.prepareStatement(query);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Category(
                    resultSet.getInt("id"),
                    resultSet.getString("type"),
                    resultSet.getString("image"),
                    resultSet.getInt("user_id")
            );
        }

        return null;
    }

    @Override
    public List<Category> findAll() throws SQLException {
        String query = "SELECT * FROM categorie";
        PreparedStatement preparedStatement = db.prepareStatement(query);
        ObservableList<Category> categories = FXCollections.observableArrayList();

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            categories.add(new Category(
                    resultSet.getInt("id"),
                    resultSet.getString("type"),
                    resultSet.getString("image"),
                    resultSet.getInt("user_id")
            ));
        }

        return categories;
    }
}
