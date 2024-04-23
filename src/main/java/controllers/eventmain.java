package tn.esprit.azizapplicationgui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import tn.esprit.azizapplicationgui.models.Event;

import java.sql.Date;
import java.util.Calendar;

public class eventmain {

    @FXML
    private ListView<Event> eventListView;

    public void initialize() {
        // Set the cell factory to use your EventCellController
        eventListView.setCellFactory(param -> new EventCellController());

        // Create some sample Event objects
        ObservableList<Event> eventList = FXCollections.observableArrayList(
                /*new Event(1, 10, "Event 1", "Location 1", "Description 1", "image1.jpg", 100.0, new Date(Calendar.getInstance().getTimeInMillis())),
                new Event(2, 20, "Event 2", "Location 2", "Description 2", "image2.jpg", 200.0, new Date(Calendar.getInstance().getTimeInMillis())),
                new Event(3, 30, "Event 3", "Location 3", "Description 3", "image3.jpg", 300.0, new Date(Calendar.getInstance().getTimeInMillis()))
                // Add more Event objects as needed*/
        );

        // Populate the ListView with the sample Event objects
        eventListView.setItems(eventList);

    }


}
