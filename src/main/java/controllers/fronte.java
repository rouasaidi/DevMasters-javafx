package tn.esprit.azizapplicationgui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tn.esprit.azizapplicationgui.models.Event;
import tn.esprit.azizapplicationgui.services.EventService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class fronte implements Initializable {

    @FXML
    private VBox chosenFruitCard;

    @FXML
    private ImageView fruitImg;

    @FXML
    private Label fruitNameLable;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    private List<Event> events = new ArrayList<>();
EventService eventService=new EventService();
List<Event> getData() throws SQLException {

    List<Event> events = new ArrayList<>();
events=eventService.recuperer();

return events;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            events.addAll(getData());
            int column=0;
            int row=0;

            for (int i=0;i< events.size();i++) {
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/tn/esprit/azizapplicationgui/item.fxml"));
                ForumbuyTicket controller = fxmlLoader.getController();

                    AnchorPane anchorPane=fxmlLoader.load();




                    ItemController itemController= fxmlLoader.getController();
    itemController.setData(events.get(i));

    if(column == 3){
        column=0;
        row ++;
    }
    grid.add(anchorPane,column++,row);
    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
    grid.setMaxWidth(Region.USE_COMPUTED_SIZE);


    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);


    GridPane.setMargin(anchorPane,new Insets(10));




                }






        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
