package services;

import entites.user;
import utlis.MyBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class usercrud  implements userservice<user> {
    private Connection connection ;
    public usercrud() {
        connection=MyBD.getInstance().getConn();

    }



   /* public usercrud(int i, int i1, String text, String text1, String text2, String text3, String text4) {
    }*/


    @Override
    public void ajouteruser(user user) throws SQLException {
        PreparedStatement ste = null;
        boolean var8 = false;

        try {
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
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Si une ligne correspond, les informations de connexion sont valides
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void updatefront(user user) {
        try (PreparedStatement statement = MyBD.getInstance().getConn().prepareStatement("UPDATE user SET name=?, phone=?, cin=?, image=?, roles=? ,password=? WHERE email=?")) {
            statement.setString(1, user.getName());
            statement.setInt(2, user.getPhone());
            statement.setInt(3, user.getCin());
            statement.setString(4, user.getImage());
            statement.setString(5, user.getRoles());

            statement.setString(6, user.getPassword());
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
}
