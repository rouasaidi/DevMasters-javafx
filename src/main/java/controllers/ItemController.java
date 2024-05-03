package tn.esprit.azizapplicationgui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.azizapplicationgui.models.Event;
import tn.esprit.azizapplicationgui.services.EventService;

import java.io.IOException;
import java.sql.SQLException;

public class ItemController {
    @FXML
    private Label datecc;

    @FXML
    private ImageView imgcc;
    @FXML
    private Button buyTicketButton;


    @FXML
    private Label nomcc;

    @FXML
    private Label placecc;




    @FXML
    protected Label quantitycc;


    private Event event;

    int id;
ForumbuyTicket controller = new ForumbuyTicket();

    public void updateQuantity(int newQuantity) {
        quantitycc.setText(String.valueOf(newQuantity));
    }
    public void setData(Event e) throws SQLException { FXMLLoader fxmlLoader = new FXMLLoader();
        EventService eventService=new EventService();
double total;

this.event=e;
nomcc.setText(e.getNom());
placecc.setText(e.getLieu());
datecc.setText(String.valueOf(e.getDate_event()));
e.setQyantity_left(e.getQuantity()-e.nb_vip-e.nb_standard);
//quantitycc.setText(String.valueOf(e.getQuantity()-e.nb_vip-e.nb_standard));
        quantitycc.setText(String.valueOf(e.qyantity_left));
id=e.getId();
        buyTicketButton.setUserData(e);


        total=e.nb_standard*20+e.nb_vip*35;
        e.setTotal_prix(total);
        eventService.modifier(e);


ForumbuyTicket forumbuyTicket =new ForumbuyTicket();


        try {
            Image image = new Image(e.getImage());
            imgcc.setImage(image);
        } catch (Exception ex) {
            System.err.println("Error loading image: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    @FXML
    void buyticket(ActionEvent event) {
        Event e = (Event) buyTicketButton.getUserData();
if(e.nb_vip+e.nb_standard<e.getQuantity()) {


    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/azizapplicationgui/forumbuyTicket.fxml"));
        Parent root = fxmlLoader.load();

        ForumbuyTicket controller = fxmlLoader.getController();
        controller.setEventId(e);

        if (controller.eventchosen != null) {
            controller.ev_namelabel.setText(controller.eventchosen.getNom());
            controller.ev_datelabel.setText(String.valueOf(controller.eventchosen.getDate_event()));

            System.out.println("event name : " + controller.eventchosen.getNom());
        } else {
            System.err.println("Event chosen is null");
        }

        Stage stage = new Stage();
        stage.setTitle("Buy Ticket");
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}
else
{

    buyTicketButton.setDisable(true);
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("We are sorry ");
    alert.setContentText("Unfortunetely no more Tickets left !!");
    alert.show();
}
    }
    @FXML
    void initialize() {


    }


    @FXML
    void click(MouseEvent event) {

    }




    }



