package services;

import entites.user;
import utlis.MyBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

import static java.sql.DriverManager.getConnection;

public  class usercrud  implements userservice<user> {
    private Connection connection ;
    public usercrud() {
        connection=MyBD.getInstance().getConn();

    }
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    private String hashPassword2(String confirmpassword) {
        return BCrypt.hashpw(confirmpassword, BCrypt.gensalt());
    }


   /* public usercrud(int i, int i1, String text, String text1, String text2, String text3, String text4) {
    }*/


    @Override
    public void ajouteruser(user user) throws SQLException {
        PreparedStatement ste = null;
        boolean var8 = false;

        try {
            String hashedPassword = hashPassword(user.getPassword());
            user.setPassword(hashedPassword);


            var8 = true;
            String insertData = "INSERT INTO `user`(`email`, `name`, `phone`, `cin`, `image`, `is_verified`, `is_banned`, `roles`, `password`)VALUES(?,?,?,?,?,?,?,?,?)";
            ste = this.connection.prepareStatement(insertData);
            ste.setString(1, user.getEmail());
            ste.setString(2, user.getName());

            ste.setInt(3, user.getPhone());
            ste.setInt(4, user.getCin());
            ste.setString(5, user.getImage());
            ste.setBoolean(6, user.isIs_verified());
            ste.setBoolean(7, user.isIs_banned());

            ste.setString(8, user.getRoles());
            ste.setString(9, user.getPassword());


            ste.executeUpdate();

            var8 = false;
        } catch (SQLException var9) {
            throw var9;
        } /*finally {
            if (var8) {
                MyBD var5 = new MyBD();
                var5.closeRessources(this.connection, ste);
            }
        }

        MyBD e1 = new MyBD();
        e1.closeRessources(this.connection, ste);*/


    }

    @Override
    public void modifieruser(user user) throws SQLException {
        PreparedStatement ste = null;
        boolean var8 = false;

        try {
            var8 = true;
            String updateData = "UPDATE `user` SET `email`=?, `name`=?, `phone`=?, `cin`=?, `image`=?, `is_verified`=?, `reset_token`=?, `is_banned`=?, `roles`=?, `password`=? WHERE id=?";
            ste = this.connection.prepareStatement(updateData);
            ste.setString(1, user.getEmail());
            ste.setString(2, user.getName());
            ste.setInt(3, user.getPhone());
            ste.setInt(4, user.getCin());
            ste.setString(5, user.getImage());
            ste.setBoolean(6, user.isIs_verified());
            ste.setString(7,user.getReset_token());

            ste.setBoolean(8, user.isIs_banned());
            ste.setString(9, user.getRoles());
            ste.setString(10, user.getPassword());
            ste.setInt(11, user.getId());


            ste.executeUpdate();

            var8 = false;
        } catch (SQLException var9) {
            throw var9;
        }/* finally {
            if (var8) {
                MyBD var5 = new MyBD();
                var5.closeRessources(this.connection, ste);
            }
        }

        MyBD e1 = new MyBD();
        e1.closeRessources(this.connection, ste);
*/

    }


    @Override

    public void supprimer(user users) throws SQLException {

        String query = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, users.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Utilisateur supprimé avec succès.");
            } else {
                System.out.println("Aucun utilisateur n'a été supprimé. ID d'utilisateur non valide.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<user> afficheruser() throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<user> users = new ArrayList<>();

        try {
            String sql = "SELECT * FROM user";
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                //user user1 = new user(name, email,phone, cin, image, roles, password);
                user user1=new user(resultSet.getInt("id"), resultSet.getInt("phone"), resultSet.getInt("cin"),  resultSet.getString("name"),resultSet.getString("password"),
                        resultSet.getString("reset_token"),resultSet.getString("image"),resultSet.getString("email"),resultSet.getString("roles"),resultSet.getBoolean("is_banned"),resultSet.getBoolean("is_verified"));




                users.add(user1);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            // Fermeture des ressources dans un bloc finally pour garantir l'exécution
            // même en cas d'exception
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // Gestion de l'exception lors de la fermeture du ResultSet
                    System.out.println("Erreur lors de la fermeture du ResultSet : " + e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // Gestion de l'exception lors de la fermeture du Statement
                    System.out.println("Erreur lors de la fermeture du Statement : " + e.getMessage());
                }
            }
        }

        return users;
    }


    public boolean isValidLogin(String email, String password) {
        ArrayList<String> userList = new ArrayList<>();
        userList.add(email);
        userList.add(password);
             user user = new user();
      //  String query = "SELECT * FROM user WHERE email = ? AND password = ?";
      //  String query = "SELECT * FROM user WHERE email = ? ";
        String query = "SELECT * FROM user WHERE email = '" + email + "'";
      //        PreparedStatement statement = connection.prepareStatement(query);
           // Statement ste = this.connection.createStatement();
        try ( Statement ste = this.connection.createStatement()) {
            ResultSet resultSet = ste.executeQuery(query);
//            statement.setString(1, email);
//            statement.setString(2, password);

           // try (ResultSet resultSet = statement.executeQuery()) {
               // return resultSet.next(); // Si une ligne correspond, les informations de connexion sont valides
                while(resultSet.next()){
                 return BCrypt.checkpw(password,  resultSet.getString("password"))  ;
                }
          //  }
        } catch (SQLException e) {

            return false;
        }
        return false;
    }

    public boolean existeEmail(String email) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean existe = false;

        try {
            String query = "SELECT COUNT(*) AS count FROM user WHERE email = ?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                existe = count > 0;
            }
        } finally {
            // Fermeture des ressources
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return existe;
    }

    public void updatefront(user user) {
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        try (
                PreparedStatement statement = MyBD.getInstance().getConn().prepareStatement("UPDATE user SET name=?, phone=?, cin=?, image=?, roles=? ,password=? WHERE email=?")) {
            statement.setString(1, user.getName());
            statement.setInt(2, user.getPhone());
            statement.setInt(3, user.getCin());
            statement.setString(4, user.getImage());
            statement.setString(5, user.getRoles());

            statement.setString(6, hashedPassword);
            statement.setString(7, user.getEmail());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Les informations de l'utilisateur ont été mises à jour avec succès.");
            } else {
                System.out.println("Échec de la mise à jour des informations de l'utilisateur.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deletefront(user users) throws SQLException {
        String query = "DELETE FROM user WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, users.getEmail());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Utilisateur supprimé avec succès.");
            } else {
                System.out.println("Aucun utilisateur n'a été supprimé. ID d'utilisateur non valide.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
            throw e;
        }

    }
    public void updateBannedStatus(int userId, boolean isBanned) throws SQLException {
        String req = "UPDATE user SET is_banned=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setBoolean(1, isBanned);
        preparedStatement.setInt(2, userId);
        preparedStatement.executeUpdate();
        System.out.println("User banned status updated");
    }
    public user getUserByEmail(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = MyBD.getInstance().getConn();
            String query = "SELECT * FROM user WHERE email = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return new user(rs.getInt("id"), rs.getInt("phone"), rs.getInt("cin"), rs.getString("name"),
                        rs.getString("password"), rs.getString("reset_token"), rs.getString("image"),
                        rs.getString("email"), rs.getString("roles"), rs.getBoolean("is_banned"),
                        rs.getBoolean("is_verified"));
            }
        }/*finally {
            // Fermeture des ressources
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
    }
*/ finally {

        }
        return null; // Retourne null si aucun utilisateur n'est trouvé avec cet e-mail
    }
    public String getHashedPasswordByEmail(String email) {
        String hashedPassword = null;
        String query = "SELECT password FROM user WHERE email = ?";

        try (PreparedStatement statement = MyBD.getInstance().getConn().prepareStatement(query)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    hashedPassword = resultSet.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hashedPassword;
    }


}
