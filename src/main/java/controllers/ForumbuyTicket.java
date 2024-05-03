package tn.esprit.azizapplicationgui.controllers;

import com.google.protobuf.StringValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.azizapplicationgui.models.Event;
import tn.esprit.azizapplicationgui.models.Ticket;
import tn.esprit.azizapplicationgui.services.EventService;
import tn.esprit.azizapplicationgui.services.TicketService;
import tn.esprit.azizapplicationgui.controllers.ItemController;
import tn.esprit.azizapplicationgui.utils.Mydatabase;

import java.io.IOException;
import java.sql.SQLException;

public class ForumbuyTicket  {
    public Event eventchosen;
    public TextField ev_namelabel;
    @FXML
    private RadioButton Standard;

    @FXML
    private RadioButton VIP;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    public Label ev_datelabel;
    @FXML
    private Label price_label;


    @FXML
    private Button updatequ;
    Ticket ticket=new Ticket();
    private Mydatabase StageManager;


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void confirm_buy(ActionEvent event) throws SQLException, IOException {
        EventService eventService=new EventService();
        if (toggleGroup.getSelectedToggle() == null) {
            showAlert("Please select a ticket type");
            return; // Stop further execution if no radio button is selected
        }

        TicketService ticketService=new TicketService();
        ticket.setEvent_id(eventchosen.getId());
        ticketService.ajouter(ticket);
        eventchosen.tickets.add(ticket);
        if(ticket.getPrice()==35)
        {
            int n= eventchosen.getNb_vip()+1;
            eventchosen.setNb_vip(n);
        }
        else if(ticket.getPrice()==20)
        {
            int s=eventchosen.getNb_standard()+1;
            eventchosen.setNb_standard(s);
        }


        System.out.println(eventchosen.nb_vip+eventchosen.nb_standard);
        int nouveau_qu=eventchosen.getQyantity_left()-1 ;
        eventchosen.setQyantity_left(nouveau_qu);
        eventService.modifier(eventchosen);

        //eventService.calcule(eventchosen);
        //System.out.println(eventchosen.nb_standard+eventchosen.nb_vip);





















        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Devmaster are waiting for you");
        alert.setContentText("We hope you have fun Ticket Bought Successfully");
        alert.show();


    }
    @FXML
    void handleRadioButtonSelection(ActionEvent event) {

        if (VIP.isSelected()) {
            price_label.setText("35"); // Set price to 35 for VIP
            ticket.setType("VIP");
            ticket.setPrice(35);

        } else if (Standard.isSelected()) {
            price_label.setText("20"); // Set price to 20 for Standard
            ticket.setPrice(20);
            ticket.setType("Standard");

        }
        ticket.setEvent_id(eventchosen.getId());
    }

    public void setEventId(Event eventchosen) {
        this.eventchosen = eventchosen;
    }
    @FXML
    void initialize() {
        toggleGroup = new ToggleGroup(); // Initialize the ToggleGroup
        Standard.setToggleGroup(toggleGroup); // Assign ToggleGroup to the radio buttons
        VIP.setToggleGroup(toggleGroup);






        /*FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/tn/esprit/azizapplicationgui/forumbuyTicket.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        Stage stage = new Stage();
        stage.setTitle("Hi!");
        stage.setScene(scene);
        stage.show();*/

    }

}
