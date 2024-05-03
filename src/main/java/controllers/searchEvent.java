
package tn.esprit.azizapplicationgui.controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tn.esprit.azizapplicationgui.controllers.AjouterEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.esprit.azizapplicationgui.models.Event;
import tn.esprit.azizapplicationgui.services.EventService;
import tn.esprit.azizapplicationgui.test.HelloApplication;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

public class searchEvent {

    @FXML
    private TextField eventIdTextField;

    @FXML
    private TextField namelabel;

    @FXML
    private TextField descriptionlabel;

    @FXML
    private TextField quantitylabel;

    @FXML
    private TextField placelabel;

    @FXML
    private TextField datelabel;

    @FXML
    private TextField picturelabel;

    @FXML
    private ChoiceBox<String> choicebb;
    @FXML
    private ImageView imageView;
    String url;

    @FXML
    void chooseImageU(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Load the selected image file into the ImageView

            Image image = new Image(selectedFile.toURI().toString());
            url = selectedFile.toURI().toString();
            imageView.setImage(image);
        }


    }

    @FXML
    public void navigaddevent(ActionEvent event) {
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
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/azizapplicationgui/ajouter_event.fxml"));
        try {
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    // This method is called when the user clicks the search button
    @FXML
    private void searchEvent(ActionEvent event) throws SQLException {
        // Get the ID entered by the user

        String eventname = eventIdTextField.getText().toString();


        String selectedChoice = choicebb.getValue();
        if (Objects.equals(selectedChoice, "Name")) {
            Event eventa = findEventByName(eventname);


            if (eventa != null) {
                // Update UI to display event details
                namelabel.setText(eventa.getNom());
                descriptionlabel.setText(eventa.getDescription());
                quantitylabel.setText(String.valueOf(eventa.getQuantity()));
                placelabel.setText(eventa.getLieu());
                datelabel.setText(String.valueOf(eventa.getDate_event()));
                picturelabel.setText(eventa.getImage());
                Image image = new Image(eventa.getImage());
                imageView.setImage(image);
            } else {
                // Update UI to indicate that event was not found
                namelabel.setText("Event not found");
                descriptionlabel.setText("Event not found");
                quantitylabel.setText("Event not found");
                placelabel.setText("Event not found");
                datelabel.setText("Event not found");
                picturelabel.setText("Event not found");
                namelabel.setText("Event not found");
                Image image = new Image("C:\\Users\\Aziz\\Desktop\\1.jpg");

                imageView.setImage(image);

                // Clear other labels or show appropriate message as needed

            }

        }


        else if (selectedChoice == "Quantity") {
            String inputText = eventIdTextField.getText();
            if (inputText.matches("\\d+")) {
                int eventId = Integer.parseInt(eventIdTextField.getText());

                Event eventa = findEventByQuantity(eventId);
                if (eventa != null) {
                    // Update UI to display event details
                    namelabel.setText(eventa.getNom());
                    descriptionlabel.setText(eventa.getDescription());
                    quantitylabel.setText(String.valueOf(eventa.getQuantity()));
                    placelabel.setText(eventa.getLieu());
                    datelabel.setText(String.valueOf(eventa.getDate_event()));
                    picturelabel.setText(eventa.getImage());
                    Image image = new Image(eventa.getImage());
                    imageView.setImage(image);
                } else {
                    // Update UI to indicate that event was not found
                    namelabel.setText("Event not found");
                    descriptionlabel.setText("Event not found");
                    quantitylabel.setText("Event not found");
                    placelabel.setText("Event not found");
                    datelabel.setText("Event not found");
                    picturelabel.setText("Event not found");
                    Image image = new Image("C:\\Users\\Aziz\\Desktop\\no.png");

                    imageView.setImage(image);


                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Failed Search ");
                alert.setContentText("Quantity should be an integer ");
                alert.show();


            }


                // Clear other labels or show appropriate message as needed
              /*  descriptionlabel.setText("");
                quantitylabel.setText("");
                placelabel.setText("");
                datelabel.setText("");
                picturelabel.setText("");*/
            }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("You should select Either a Quantity or Name");
            alert.show();
        }

        }
        // Call a method to search for the event by ID


        // Display the details of the found event in the UI
       /* if (eventa != null) {
            // Update UI to display event details
            namelabel.setText(eventa.getNom());
            descriptionlabel.setText(eventa.getDescription());
            quantitylabel.setText(String.valueOf(eventa.getQuantity()));
            placelabel.setText(eventa.getLieu());
            datelabel.setText(String.valueOf(eventa.getDate_event()));
            picturelabel.setText(eventa.getImage());
        } else {
            // Update UI to indicate that event was not found
            namelabel.setText("Event not found");
            descriptionlabel.setText("Event not found");
            quantitylabel.setText("Event not found");
            placelabel.setText("Event not found");
            datelabel.setText("Event not found");
            picturelabel.setText("Event not found");
            namelabel.setText("Event not found");*/

        // Clear other labels or show appropriate message as needed
            /*descriptionlabel.setText("");
            quantitylabel.setText("");
            placelabel.setText("");
            datelabel.setText("");
            picturelabel.setText("");*/



    // Method to search for an event by ID (Replace this with your actual implementation)
    public Event findEventByQuantity(int eventq) throws SQLException {
        EventService eventService = new EventService();
        List<Event> events = eventService.recuperer();
        // Perform your search operation here (e.g., query database, search in a list)
        // This is just a placeholder method; replace it with your actual search logic

        // Example: Searching in a list of events
        for (Event event : events) {
            if (event.getQuantity() == eventq) {
                return event; // Event found
            }
        }
        return null; // Event not found
    }
    public Event findEventById(int eventq) throws SQLException {
        EventService eventService = new EventService();
        List<Event> events = eventService.recuperer();
        // Perform your search operation here (e.g., query database, search in a list)
        // This is just a placeholder method; replace it with your actual search logic

        // Example: Searching in a list of events
        for (Event event : events) {
            if (event.getId() == eventq) {
                return event; // Event found
            }
        }
        return null; // Event not found
    }

    private Event findEventByName(String eventName) throws SQLException {
        EventService eventService = new EventService();
        List<Event> events = eventService.recuperer();
        // Perform your search operation here (e.g., query database, search in a list)
        // This is just a placeholder method; replace it with your actual search logic

        // Example: Searching in a list of events
        for (Event event : events) {
            if (Objects.equals(event.getNom(), eventName)) {
                return event; // Event found
            }
        }
        return null; // Event not found
    }

    @FXML
    void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList(
                "Quantity",
                "Name"

        );

        // Set the items to the ChoiceBox
        choicebb.setItems(items);
    }


    @FXML
    void updateevent(ActionEvent event) {
        EventService eventService = new EventService();
        String eventname = eventIdTextField.getText().toString();

        String selectedChoice = choicebb.getValue();
        if (Objects.equals(selectedChoice, "Name")) {
            Event eventa = null;
            try {
                eventa = findEventByName(eventname);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            if (eventa != null) {
                String nom = namelabel.getText().trim();
                if (nom.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Event name cannot be empty.");
                    alert.show();
                    return; // Stop further execution if event name is empty
                }
                eventa.setNom(nom);


                if (picturelabel == null ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please check the photo URL or select a photo.");
                    alert.show();
                    return; // Stop further execution if URL is invalid
                }
                eventa.setImage(picturelabel.getText());



                LocalDate selectedDate;
                selectedDate = LocalDate.parse(datelabel.getText());
                LocalDate selectedDate1 = null;
                try {
                    selectedDate1 = LocalDate.parse(datelabel.getText());
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format: " + e.getMessage());
                }


                if (selectedDate1 != null) {
                    // Convert LocalDate to java.sql.Date
                    java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);
                    eventa.setDate_event(sqlDate);
                    System.out.println("date picker is empty u need to selct one ");
                } else {

                    System.out.println("Invalid date format: ");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Event date cannot be empty.");
                    alert.show();
                }



                java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);
                eventa.setDate_event(sqlDate);




                try {
                    int quantity = Integer.parseInt(quantitylabel.getText().trim());
                    if (quantity <= 0) {Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Invalid quantity format. Please enter a valid integer.");
                        alert.show();
                        return;

                    }
                    eventa.setQuantity(quantity);
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Invalid quantity format. Please enter a valid integer.");
                    alert.show();
                    return; // Stop further execution if quantity is invalid
                }









                String lieu = placelabel.getText().trim();
                if (lieu.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Event location cannot be empty.");
                    alert.show();
                    return; // Stop further execution if event location is empty
                }
                eventa.setLieu(placelabel.getText());


                String description = descriptionlabel.getText().trim();
                if (description.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Event description cannot be empty.");
                    alert.show();
                    return; // Stop further execution if event description is empty
                }
                eventa.setDescription(description);

                try {
                    eventService.modifier(eventa);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Event Updated  succesfully");
                    alert.show();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed");
                alert.setContentText("Event not found update 404");
                alert.show();
            }


        } else if (Objects.equals(selectedChoice, "Quantity")) {

            int idf = Integer.parseInt(eventname);
            Event eventa = null;
            try {
                eventa = findEventByQuantity(idf);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            if (eventa != null) {
                String nom = namelabel.getText().trim();
                if (nom.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Event name cannot be empty.");
                    alert.show();
                    return; // Stop further execution if event name is empty
                }
                eventa.setNom(nom);


                if (picturelabel == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please check the photo URL or select a photo.");
                    alert.show();
                    return; // Stop further execution if URL is invalid
                }
                eventa.setImage(picturelabel.getText());


                LocalDate selectedDate;
                selectedDate = LocalDate.parse(datelabel.getText());
                LocalDate selectedDate1 = null;
                try {
                    selectedDate1 = LocalDate.parse(datelabel.getText());
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format: " + e.getMessage());
                }


                if (selectedDate1 != null) {
                    // Convert LocalDate to java.sql.Date
                    java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);
                    eventa.setDate_event(sqlDate);
                    System.out.println("date picker is empty u need to selct one ");
                } else {

                    System.out.println("Invalid date format: ");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Event date cannot be empty.");
                    alert.show();
                }


                java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);
                eventa.setDate_event(sqlDate);
                eventa.setQuantity(Integer.parseInt(quantitylabel.getText()));


                String lieu = placelabel.getText().trim();
                if (lieu.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Event location cannot be empty.");
                    alert.show();
                    return; // Stop further execution if event location is empty
                }
                eventa.setLieu(placelabel.getText());


                String description = descriptionlabel.getText().trim();
                if (description.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Event description cannot be empty.");
                    alert.show();
                    return; // Stop further execution if event description is empty
                }
                eventa.setDescription(description);

                try {
                    eventService.modifier(eventa);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Event Updated  succesfully");
                    alert.show();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed");
                alert.setContentText("Event not found update 404");
                alert.show();



            }
        }
    }

    @FXML
    void deleteevent(ActionEvent event) throws SQLException {
        EventService eventService = new EventService();
        String eventname = eventIdTextField.getText().toString();

        String selectedChoice = choicebb.getValue();
        if (Objects.equals(selectedChoice, "Name")) {
            Event eventa = null;
            try {
                eventa = findEventByName(eventname);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            if (eventa != null) {
                eventService.supprimer(eventa.getId());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("Event Deleted Succesfully");
                alert.show();


            }
            if(eventa== null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Event Does not exist");
                alert.show();
            }


        } else if (Objects.equals(selectedChoice, "Id")) {

            int idf = Integer.parseInt(eventname);
            Event eventa = null;
            try {
                eventa = findEventByQuantity(idf);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            if (eventa != null) {

                eventService.supprimer(eventa.getId());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("Event Deleted Succesfully");
                alert.show();

            }
            if(eventa== null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Event Does not exist");
                alert.show();
            }

        }
    }
    @FXML
    void navigatetotickets(ActionEvent event) {
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/azizapplicationgui/showbackTicket.fxml"));
        try {
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}





