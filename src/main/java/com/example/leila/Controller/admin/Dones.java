package com.example.leila.Controller.admin;

import com.example.leila.HelloApplication;
import com.example.leila.Models.Donation;
import com.example.leila.Services.DonationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class Dones {
    @FXML
    private TableView<Donation> tableView;
    private final DonationService donationService = new DonationService();

    @FXML
    public void initialize() {
        refreshPosts();
    }

    private void refreshPosts() {
        tableView.getItems().clear();
        tableView.getColumns().clear();

        TableColumn<Donation, String> titleCol = new TableColumn<>("Name");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Donation, String> contentCol = new TableColumn<>("Category");
        contentCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Donation, String> quoteCol = new TableColumn<>("Description");
        quoteCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Donation, Date> createdatCol = new TableColumn<>("Created At");
        createdatCol.setCellValueFactory(new PropertyValueFactory<>("date_don"));

        TableColumn<Donation, Integer> ratingCol = new TableColumn<>("Status");
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Donation, Void> repondreCol = new TableColumn<>("Action");
        repondreCol.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("Feedback");

            {
                button.setOnAction(event -> {
                    Donation donation = getTableView().getItems().get(getIndex());
                    Scene scene1 = tableView.getScene();
                    Stage stage1 = (Stage) scene1.getWindow();
                    stage1.close();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/DonetFeedback.fxml"));
                    Parent root = null;
                    try {

                        root = loader.load();

                        AdminFeedbak controller = loader.getController();
                        controller.initialize(donation);
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Feedbaks");
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });


        TableColumn<Donation, Void> deletCol = new TableColumn<>("Action");
        deletCol.setCellFactory(param -> new TableCell<>() {
            private final Button delet = new Button("Delete");

            {
                delet.setOnAction(event -> {
                    Donation donation = getTableView().getItems().get(getIndex());
                    try {
                        donationService.supprimer(donation.getId());
                        tableView.refresh();
                        refreshPosts();
                    } catch (SQLException ex) {
                        showErrorAlert("Erreur lors de la suppression de l'hébergement : " + ex.getMessage());
                    }

                });

            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(delet);
                }
            }
        });
        tableView.getColumns().addAll(titleCol, contentCol, quoteCol, createdatCol, ratingCol, repondreCol, deletCol);

        try {
            tableView.getItems().addAll(donationService.getAll());
        } catch (SQLException e) {
            showErrorAlert("Error fetching posts: " + e.getMessage());
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void godonate() throws IOException {
        Scene scenefer = tableView.getScene();
        Stage stagefer = (Stage) scenefer.getWindow();
        stagefer.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Admin/Dones.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
}