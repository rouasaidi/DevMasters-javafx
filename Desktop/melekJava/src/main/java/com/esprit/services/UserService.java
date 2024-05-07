package com.esprit.services;

import com.esprit.models.User;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UserService {

    private final Connection cnx = DataSource.getInstance().getConnection();


    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT * FROM user WHERE id = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                // Extract other fields as necessary
                user = new User(id, name,email);
                System.out.println(user);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user by ID: " + e.getMessage());
        }

        return user;
    }
}

