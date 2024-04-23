package Utils;

import java.sql.*;

public class DB {
    private Connection cnx;            // pour la Connexion
    private ResultSet rs;              // pour les résultats des requêtes de type select
    private PreparedStatement pstm;   // pour les résultats préparés
    private int ok;                    // pour les résultats des requêtes de type mise à jour

    private Connection getConnection() {
        String url ="jdbc:mysql://localhost:3306/pidevsymfony";
        String email ="root";
        String password="";
        try {
            System.out.println("Tentative de connexion à la base de données...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            cnx = DriverManager.getConnection(url,email,password);
            System.out.println("Connexion réussie à la base de données.");
            return cnx;
        } catch (Exception ex) {
            System.out.println("Erreur lors de la connexion à la base de données : " + ex.getMessage());
            return null;
        }
    }

    public PreparedStatement initPrepar(String sql) {
        try {
            pstm = getConnection().prepareStatement(sql);
            return pstm;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public ResultSet executeSelect() {
        try {
            rs = pstm.executeQuery();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return rs;
    }

    public int executeMaj() {
        try {
            ok = pstm.executeUpdate();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeConnection();
        }
        return ok;
    }

    public void closeConnection() {
        try {
            if (cnx != null) {
                cnx.close();
                System.out.println("Connexion à la base de données fermée.");
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public PreparedStatement getPstm() throws SQLException {
        return pstm;
    }
}
