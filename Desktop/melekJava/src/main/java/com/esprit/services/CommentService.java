package com.esprit.services;

import com.esprit.models.Comment;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentService implements IService<Comment> {

    private final Connection cnx = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Comment comment) {
        String req = "INSERT INTO `comment` (`article_id`, `user_id`, `content`, `date_cmt`) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, comment.getArticleId());
            ps.setInt(2, comment.getUserId());
            ps.setString(3, comment.getContent());
            ps.setDate(4, Date.valueOf(comment.getDateCmt()));
            ps.executeUpdate();
            System.out.println("Comment added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding comment: " + e.getMessage());
        }
    }

    public List<Comment> getCommentsByArticleId(int articleId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comment WHERE article_id = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setInt(1, articleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Comment comment = new Comment(
                        resultSet.getInt("id"),
                        resultSet.getInt("article_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("content"),
                        resultSet.getDate("date_cmt").toLocalDate() // Assuming date_cmt is stored as a Date in the DB
                );
                comments.add(comment);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching comments by article ID: " + e.getMessage());
        }

        return comments;
    }
    @Override
    public void modifier(Comment comment) {
        String req = "UPDATE `comment` SET `article_id`=?, `user_id`=?, `content`=?, `date_cmt`=? WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, comment.getArticleId());
            ps.setInt(2, comment.getUserId());
            ps.setString(3, comment.getContent());
            ps.setDate(4, Date.valueOf(comment.getDateCmt()));
            ps.setInt(5, comment.getId());
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Comment updated successfully.");
            } else {
                System.out.println("No Comment found with ID: " + comment.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error updating comment: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `comment` WHERE `id` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Comment deleted successfully.");
            } else {
                System.out.println("No Comment found with ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting comment: " + e.getMessage());
        }
    }

    @Override
    public Comment getOneById(int id) {
        String req = "SELECT * FROM `comment` WHERE `id` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Comment(
                        rs.getInt("id"),
                        rs.getInt("article_id"),
                        rs.getInt("user_id"),
                        rs.getString("content"),
                        rs.getDate("date_cmt").toLocalDate()
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching comment by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments = new ArrayList<>();
        String req = "SELECT * FROM `comment`";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                comments.add(new Comment(
                        rs.getInt("id"),
                        rs.getInt("article_id"),
                        rs.getInt("user_id"),
                        rs.getString("content"),
                        rs.getDate("date_cmt").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching all comments: " + e.getMessage());
        }
        return comments;
    }
}
