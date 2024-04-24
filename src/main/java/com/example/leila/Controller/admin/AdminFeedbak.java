package com.example.leila.Controller.admin;

import com.example.leila.HelloApplication;
import com.example.leila.Models.Donation;
import com.example.leila.Models.Feedback_don;
import com.example.leila.Services.FeedbackDonService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class AdminFeedbak {
    @FXML
    private TableView<Feedback_don> tableView;
    private Donation donation=new Donation();

    @FXML
    private Label categorieF;


    @FXML
    private Label donateDateF;

    @FXML
    private ImageView imageview;



    @FXML
    private Label nameF;
    @FXML
    private Text dsecreptionf;

    @FXML
    private Label quantityF;
    private FeedbackDonService feedbackDonService=new FeedbackDonService();
    @FXML
    void initialize(Donation donation){
        this.donation=donation;
        categorieF.setText(categorieF.getText()+" "+donation.getCategory());
        nameF.setText(nameF.getText()+" "+donation.getName());
        donateDateF.setText(donateDateF.getText()+" "+donation.getDate_don());
        quantityF.setText(quantityF.getText()+" "+donation.getQuantity());
        dsecreptionf.setText(dsecreptionf.getText()+" "+donation.getDescription());
        if (donation.getImage() != null) {
            File imageFile = new File(donation.getImage());
            if (imageFile.exists()) {
                String imageUrl = imageFile.toURI().toString();
                imageview.setImage(new Image(imageUrl));
                imageview.setVisible(true);
            }}
        refreshPosts(this.donation.getId());
    }
    private void refreshPosts(int id) {
        tableView.getItems().clear();
        tableView.getColumns().clear();

        TableColumn<Feedback_don, String> titleCol = new TableColumn<>("Date");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("date_feedback"));

        TableColumn<Feedback_don, String> contentCol = new TableColumn<>("Description");
        contentCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Feedback_don, Void> deletCol = new TableColumn<>("Action");
        deletCol.setCellFactory(param -> new TableCell<>() {
            private final Button delet = new Button("Delete");

            {
                delet.setOnAction(event -> {
                    Feedback_don feedback_don = getTableView().getItems().get(getIndex());
                    try {
                        feedbackDonService.supprimer(feedback_don.getId());
                        tableView.refresh();
                        refreshPosts(feedback_don.getDonation_id());

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
        tableView.getColumns().addAll(titleCol, contentCol, deletCol);

        try {
            tableView.getItems().addAll(feedbackDonService.getAllbydonnation(id));
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
