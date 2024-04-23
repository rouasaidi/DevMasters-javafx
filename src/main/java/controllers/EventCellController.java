package tn.esprit.azizapplicationgui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.azizapplicationgui.models.Event;

import java.io.IOException;

public class EventCellController extends ListCell<Event> {

    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label placeLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private ImageView imageView;

    private FXMLLoader fxmlLoader;

    @Override
    protected void updateItem(Event event, boolean empty) {
        super.updateItem(event, empty);

        if (empty || event == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("event_cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            nameLabel.setText(event.getNom());
            descriptionLabel.setText(event.getDescription());
            placeLabel.setText(event.getLieu());
            dateLabel.setText(String.valueOf(event.getDate_event()));
            quantityLabel.setText(String.valueOf(event.getQuantity()));

            // Load and set the image
            if (event.getImage() != null) {
                Image image = new Image(event.getImage());
                imageView.setImage(image);
            } else {
                imageView.setImage(null);
            }

            setText(null);
            setGraphic(fxmlLoader.getRoot());
        }
    }
}
