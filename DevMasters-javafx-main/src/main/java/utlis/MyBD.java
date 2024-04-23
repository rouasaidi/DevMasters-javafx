package utlis;

import java.sql.*;

public class MyBD {
    Connection conn;

    String url = "jdbc:mysql://localhost:3306/devmaster";
    String user = "root";
    String password = "";
    static MyBD instance;


    public MyBD() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("conncted !!! ");

        } catch (SQLException e) {
            System.out.println(" probleme de connexion !!");
        }


    }
    public static MyBD getInstance() {
        if (instance == null) {
            instance = new MyBD();
        }

        return instance;
    }


    public Connection getConn() {
        return this.conn;
    }


}
