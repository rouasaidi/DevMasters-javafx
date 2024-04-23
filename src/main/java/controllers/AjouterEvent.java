package tn.esprit.azizapplicationgui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tn.esprit.azizapplicationgui.controllers.Afficherpersonnes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.azizapplicationgui.services.EventService;
import tn.esprit.azizapplicationgui.models.Event;
import tn.esprit.azizapplicationgui.test.HelloApplication;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AjouterEvent {

    String url=null;
    int t=0;
    @FXML
    private TextField date1cc;

    @FXML
    private DatePicker date2cc;

    @FXML
    private TextField descriptcc;

    @FXML
    private TextField lieucc;

    @FXML
    private TextField nomcc;



    @FXML
    private TextField quantitecc;

    @FXML
    private ImageView imageView; // Assuming you've given your ImageView this ID in Scene Builder

    @FXML
    void chooseImage(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Load the selected image file into the ImageView
t=1;
            Image image = new Image(selectedFile.toURI().toString());
            url = selectedFile.toURI().toString();
            imageView.setImage(image);
        }

    }
    @FXML
    void initialize() {
        Image image = new Image("C:\\Users\\Aziz\\Desktop\\1.jpg");

        imageView.setImage(image);


    }



    @FXML
    void ajouterevent(ActionEvent event) {

        EventService eventservice = new EventService();
        Event e = new Event();

// Validate nomcc
        String nom = nomcc.getText().trim();
        if (nom.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Event name cannot be empty.");
            alert.show();
            return; // Stop further execution if event name is empty
        }
        e.setNom(nom);

// Validate lieucc
        String lieu = lieucc.getText().trim();
        if (lieu.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Event location cannot be empty.");
            alert.show();
            return; // Stop further execution if event location is empty
        }
        e.setLieu(lieu);

// Validate descriptcc
        String description = descriptcc.getText().trim();
        if (description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Event description cannot be empty.");
            alert.show();
            return; // Stop further execution if event description is empty
        }
        e.setDescription(description);

// Validate quantity
        try {
            int quantity = Integer.parseInt(quantitecc.getText().trim());
            if (quantity <= 0) {Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid quantity format. Please enter a valid integer.");
                alert.show();
                return;

            }
            e.setQuantity(quantity);
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid quantity format. Please enter a valid integer.");
            alert.show();
            return; // Stop further execution if quantity is invalid
        }

// Validate URL
        if (url == null && t == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please check the photo URL or select a photo.");
            alert.show();
            return; // Stop further execution if URL is invalid
        }

// Validate date
        LocalDate selectedDate = date2cc.getValue();
        if (selectedDate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a valid event date.");
            alert.show();
            return; // Stop further execution if date is invalid
        }
// Convert LocalDate to java.sql.Date
        java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);
        e.setDate_event(sqlDate);

        try {
            eventservice.ajouter(e);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Event added successfully");
            alert.show();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("An error occurred while adding the event: " + ex.getMessage());
            alert.show();
        }
    }
    @FXML
    public void navigaddevent(ActionEvent event)  {
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/azizapplicationgui/fff.fxml"));
        try {
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void naviglistevent(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/azizapplicationgui/ajouter_event.fxml"));
        try {
            lieucc.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void navigatesearch(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/azizapplicationgui/search.fxml"));
        try {
            lieucc.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    }





