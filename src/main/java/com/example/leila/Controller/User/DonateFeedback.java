package com.example.leila.Controller.User;

import com.example.leila.HelloApplication;
import com.example.leila.Models.Donation;
import com.example.leila.Models.Feedback_don;
import com.example.leila.Services.FeedbackDonService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class DonateFeedback {
    @FXML
    private Label DateFeedbackF;
    @FXML
    private Label categorieF;

    @FXML
    private Label checkcontent;

    @FXML
    private TextArea contentF;

    @FXML
    private Label donateDateF;

    @FXML
    private ImageView imageview;

    @FXML
    private ListView<Feedback_don> listeview;

    @FXML
    private Pane modifpanne;

    @FXML
    private Label nameF;
    @FXML
    private Text dsecreptionf;

    @FXML
    private Label quantityF;

    int user_id=1;
    private  final FeedbackDonService feedbackDonService=new FeedbackDonService();
    private Feedback_don feedback_don=new Feedback_don();
    private Donation donation=new Donation();
    @FXML
    public void initialize(Donation donation) {
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
        try {
            displayFeedback_donList(feedbackDonService.getAllbydonnation(donation.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    void afficher() {
        listeview.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Feedback_don> call(ListView<Feedback_don> param) {
                return new ListCell<>() {
                    private final HBox hbox = new HBox();
                    private final HBox h1 = new HBox();
                    private final Label dateLabel = new Label();
                    private final Label descriptionLabel = new Label();
                    private final ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/image/edit.png")));

                    {
                        editIcon.setFitHeight(42.0);
                        editIcon.setFitWidth(35.0);
                        h1.setSpacing(10);
                        hbox.setSpacing(10);
                        hbox.getChildren().addAll(h1, editIcon);
                        editIcon.setVisible(false);

                        setOnMouseClicked(event -> {
                            if (getItem().getUser_id() == user_id) {
                            modifpanne.setVisible(true);
                            DateFeedbackF.setText(DateFeedbackF.getText()+" "+getItem().getDate_feedback());
                            contentF.setText(getItem().getDescription());
                            setmodif(getItem());}
                        });

                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }

                    @Override
                    protected void updateItem(Feedback_don item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            dateLabel.setText("Date: " + item.getDate_feedback().toString());
                            descriptionLabel.setText("Description: " + item.getDescription());

                            h1.getChildren().setAll(dateLabel, descriptionLabel);

                            if (item.getUser_id() == user_id) {
                                editIcon.setVisible(true);
                            } else {
                                editIcon.setVisible(false);
                            }

                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
    }
    public void displayFeedback_donList(List<Feedback_don> feedback_donList) {
        afficher();
        listeview.getItems().setAll(feedback_donList);
    }
    void setmodif(Feedback_don don){
        this.feedback_don=don;
    }
    @FXML
    void deletev(){
        try {
            feedbackDonService.supprimer(this.feedback_don.getId());
            showSuccessAlert("Feedback added successfully");
            displayFeedback_donList(feedbackDonService.getAllbydonnation(this.donation.getId()));
            modifpanne.setVisible(false);

        } catch (SQLException e) {
            showErrorAlert("Error adding the Feedback : " + e.getMessage());
        }
    }
    @FXML
    void modifierFeedback(){
        if (contentF.getText()!=""){
            this.feedback_don.setDescription(contentF.getText());
            try {
                feedbackDonService.modifier(this.feedback_don);
                showSuccessAlert("Feedback added successfully");
                displayFeedback_donList(feedbackDonService.getAllbydonnation(this.donation.getId()));
                modifpanne.setVisible(false);

            } catch (SQLException e) {
                showErrorAlert("Error adding the Feedback : " + e.getMessage());
            }

        }
        else{checkcontent.setVisible(true);}
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void godonate() throws IOException {
        Scene scenefer = listeview.getScene();
        Stage stagefer = (Stage) scenefer.getWindow();
        stagefer.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/User/donnotaions.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
}
