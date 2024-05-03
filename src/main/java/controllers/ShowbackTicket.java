package tn.esprit.azizapplicationgui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.azizapplicationgui.models.Event;
import tn.esprit.azizapplicationgui.models.Ticket;
import tn.esprit.azizapplicationgui.services.EventService;
import tn.esprit.azizapplicationgui.services.TicketService;
import tn.esprit.azizapplicationgui.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ShowbackTicket {

    @FXML
    private TableColumn<Ticket,Integer> Tprice;

    @FXML
    private TableColumn<Ticket, String> datetic;

    @FXML
    private TableColumn<Ticket,String> nametic;

    @FXML
    private TableColumn<Ticket,String> placetic;

    @FXML
    private TableView<Ticket> tt;

    @FXML
    private TableColumn<Ticket,String> type;




    @FXML
    void initialize() {
        TicketService ticketService = new TicketService();
        searchEvent searchEvent = new searchEvent();
        try {
            List<Ticket> tickets = ticketService.recupere_chevent();
            ObservableList<Ticket> observableList = FXCollections.observableList(tickets);
            tt.setItems(observableList);

            Tprice.setCellValueFactory(new PropertyValueFactory<>("price"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            placetic.setCellValueFactory(new PropertyValueFactory<>("place"));
            nametic.setCellValueFactory(cellData -> {
                Ticket ticket = cellData.getValue();
                try {
                    String eventName = searchEvent.findEventById(ticket.getEvent_id()).getNom();
                    return new SimpleStringProperty(eventName);
                } catch (SQLException e) {
                    throw new RuntimeException("Error retrieving event name", e);
                }
            });
            placetic.setCellValueFactory(cellData -> {
                Ticket ticket = cellData.getValue();
                try {
                    String eventplace = searchEvent.findEventById(ticket.getEvent_id()).getLieu();
                    return new SimpleStringProperty(eventplace);
                } catch (SQLException e) {
                    throw new RuntimeException("Error retrieving event name", e);
                }
            });
            datetic.setCellValueFactory(cellData -> {
                Ticket ticket = cellData.getValue();
                try {
                    String eventdate = searchEvent.findEventById(ticket.getEvent_id()).getDate_event().toString();
                    return new SimpleStringProperty(eventdate);
                } catch (SQLException e) {
                    throw new RuntimeException("Error retrieving event name", e);
                }
            });


        } catch (SQLException e) {
            throw new RuntimeException("Error fetching tickets", e);
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
}

