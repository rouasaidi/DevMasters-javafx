package controls;

import entites.user;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utlis.MyBD;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Userinfo {
    @FXML
    private Button back;

    @FXML
    private Button update;

    @FXML
    private TextField usercintextfiled;

    @FXML
    private TextField usereamiltextfiled;

    @FXML
    private TextField userimagetextfiled;

    @FXML
    private TextField usernametextfiled;

    @FXML
    private TextField userpasswordtextfiled;

    @FXML
    private TextField userphonetextfiled;

    @FXML
    private TextField userrolesextfiled;
    private user user1 =new user();

    public void initData(String name, String email,String password,String cin,String phone,String roles,String image) {
        usernametextfiled.setText( name);
        usereamiltextfiled.setText( email);
        userpasswordtextfiled.setText( password);
       usercintextfiled.setText( cin );
       userphonetextfiled.setText( phone);
       userrolesextfiled.setText( roles);
       userimagetextfiled.setText( image);


        // Initialisez d'autres champs avec les informations de l'utilisateur selon vos besoins
    }


    public void update(javafx.event.ActionEvent event) {


        try {
            Parent root = FXMLLoader.load(getClass().getResource("/updatefront.fxml"));
            Stage stage = (Stage) update.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void back(javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menufront.fxml"));
            Stage stage = (Stage) back.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
