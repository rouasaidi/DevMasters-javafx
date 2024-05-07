package com.esprit.services;

import com.esprit.models.Article;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ArticleService implements IService<Article> {

    Connection cnx = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Article article) {
        String req = "INSERT INTO `article` (`user_id`, `title`, `content`, `likes`, `dislikes`, `date`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, article.getUserId());
            ps.setString(2, article.getTitle());
            ps.setString(3, article.getContent());
            ps.setInt(4, article.getLikes());
            ps.setInt(5, article.getDislikes());
            ps.setDate(6, Date.valueOf(article.getDate()));
            ps.setString(7, article.getImage());
            ps.executeUpdate();
            System.out.println("Article added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding article: " + e.getMessage());
        }
    }

    @Override
    public void modifier(Article article) {
        String req = "UPDATE `article` SET `user_id`=?, `title`=?, `content`=?, `likes`=?, `dislikes`=?, `date`=?, `image`=? WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, article.getUserId());
            ps.setString(2, article.getTitle());
            ps.setString(3, article.getContent());
            ps.setInt(4, article.getLikes());
            ps.setInt(5, article.getDislikes());
            ps.setDate(6, Date.valueOf(article.getDate()));
            ps.setString(7, article.getImage());
            ps.setInt(8, article.getId());
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Article updated successfully.");
            } else {
                System.out.println("No Article found with ID: " + article.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error updating Article: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `article` WHERE `id` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Article deleted successfully.");
            } else {
                System.out.println("No Article found with ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting Article: " + e.getMessage());
        }
    }

    @Override
    public Article getOneById(int id) {
        String req = "SELECT * FROM `article` WHERE `id` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Article(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("likes"),
                        rs.getInt("dislikes"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("image")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching Article by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Article> getAll() {
        List<Article> articles = new ArrayList<>();
        String req = "SELECT * FROM `article`";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                articles.add(new Article(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("likes"),
                        rs.getInt("dislikes"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("image"),
                        rs.getInt("rating")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching all articles: " + e.getMessage());
        }
        return articles;
    }
    public void incrementLikes(int articleId) {
        String sql = "UPDATE article SET likes = likes + 1 WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, articleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error incrementing likes: " + e.getMessage());
        }
    }

    public void decrementLikes(int articleId) {
        String sql = "UPDATE article SET likes = likes - 1 WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, articleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error decrementing likes: " + e.getMessage());
        }
    }

    public void incrementDislikes(int articleId) {
        String sql = "UPDATE article SET dislikes = dislikes + 1 WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, articleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error incrementing dislikes: " + e.getMessage());
        }
    }
    public void updateArticleRating(int articleId, double rating) {
        String sql = "UPDATE article SET rating = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setDouble(1, rating);
            ps.setInt(2, articleId);
            ps.executeUpdate();
            System.out.println("Rating updated successfully for Article ID: " + articleId);
        } catch (SQLException e) {
            System.err.println("Error updating article rating: " + e.getMessage());
        }
    }

    public void decrementDislikes(int articleId) {
        String sql = "UPDATE article SET dislikes = dislikes - 1 WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, articleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error decrementing dislikes: " + e.getMessage());
        }
    }
    public boolean isMostLiked(int articleId) {
        String sql = "SELECT id FROM article WHERE likes > (SELECT AVG(likes) FROM article) AND id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, articleId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error checking if article is most liked: " + e.getMessage());
            return false;
        }
    }
    public int getCommentCount(int articleId) {
        String query = "SELECT COUNT(*) AS comment_count FROM comment WHERE article_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, articleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("comment_count");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving comment count: " + e.getMessage());
        }
        return 0;
    }

}
