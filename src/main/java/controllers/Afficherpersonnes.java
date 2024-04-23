package tn.esprit.azizapplicationgui.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.azizapplicationgui.services.EventService;
import tn.esprit.azizapplicationgui.models.Event;
import tn.esprit.azizapplicationgui.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Afficherpersonnes {


    @FXML
    private TableColumn<Event,Date> datecol;

    @FXML
    private TableColumn<Event,String> descriptioncol;

    @FXML
    private TableColumn<Event,String> imagecol;

    @FXML
    private TableColumn<Event,String> namecol;

    @FXML
    private TableColumn<Event,String> placecol;

    @FXML
    private TableColumn<Event,Integer> quantitecol;

    @FXML
    private TableView<Event> tt;
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
    void navigatesearch(ActionEvent event) {
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/azizapplicationgui/search.fxml"));
        try {
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }




    @FXML
    void initialize() {

        EventService eventService=new EventService();
        try{
            List<Event>events=eventService.recuperer();
            ObservableList<Event>observableList= FXCollections.observableList(events);
            tt.setItems(observableList);


         /*   imagecol.setCellFactory(param -> new TableCell<Event, String>() {
                private final ImageView imageView = new ImageView();
                {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(imageView);
                }

                @Override
                protected void updateItem(String imageUrl, boolean empty) {
                    super.updateItem(imageUrl, empty);
                    if (empty || imageUrl == null) {
                        imageView.setImage(null);
                    } else {
                        Image image = new Image(imageUrl);
                        imageView.setImage(image);
                    }
                }
            });*/











            imagecol.setCellValueFactory(new PropertyValueFactory<>("image"));
            descriptioncol.setCellValueFactory(new PropertyValueFactory<>("description"));
            placecol.setCellValueFactory(new PropertyValueFactory<>("lieu"));
            namecol.setCellValueFactory(new PropertyValueFactory<>("nom"));
datecol.setCellValueFactory(new PropertyValueFactory<>("date_event"));
quantitecol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }

}
