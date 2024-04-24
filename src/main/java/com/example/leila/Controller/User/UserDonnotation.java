package com.example.leila.Controller.User;

import com.example.leila.HelloApplication;
import com.example.leila.Models.Donation;
import com.example.leila.Models.Feedback_don;
import com.example.leila.Services.DonationService;
import com.example.leila.Services.FeedbackDonService;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserDonnotation {

    int user_id=1;
    @FXML
    private ScrollPane ScrollePan;
    int choix = 1;

    private final DonationService donationService = new DonationService();
    private final FeedbackDonService feedbackDonService = new FeedbackDonService();

    @FXML
    public void initialize() {
        loadDonations(choix);
    }

    @FXML
    void loaAllDonation() {
        choix = 1;
        loadDonations(choix);
    }

    @FXML
    void loaUserDonation() {
        choix = 2;
        loadDonations(choix);
    }

    private void loadDonations(int choix) {
        FlowPane donationFlowPane = new FlowPane();
        donationFlowPane.setStyle("-fx-pref-width: 950px; " +
                "-fx-pref-height: 547px;");

        List<Donation> donationList;
        try {
            if (choix == 1) {
                donationList = donationService.getAll();
            } else {
                donationList = donationService.getByIdUser(user_id);
            }

            for (Donation donation : donationList) {
                VBox cardContainer = createDonationVBox(donation, choix);
                donationFlowPane.getChildren().add(cardContainer);
                FlowPane.setMargin(cardContainer, new Insets(10));
            }
            ScrollePan.setContent(donationFlowPane);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private VBox createDonationVBox(Donation donation, int choix) {
        String imagepth = donation.getImage();
        VBox cardContainer = new VBox();
        cardContainer.setStyle("-fx-padding: 10px 10px 10px 10px;");
        cardContainer.setStyle(
                "-fx-background-color: #EFFCFF; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-border-color: #9CCBD6; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-border-width: 1px; ");


        Pane pane = new Pane();
        pane.setLayoutX(403.0);
        pane.setLayoutY(130.0);
        pane.setPrefHeight(513.0);
        pane.setPrefWidth(631.0);



        ImageView imageView = new ImageView();
        imageView.setFitWidth(400.0);
        imageView.setFitHeight(200.0);
        imageView.setLayoutX(109.0);
        imageView.setLayoutY(132.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);



        Label userLabel = new Label();
        userLabel.setLayoutX(43.0);
        userLabel.setLayoutY(14.0);
        userLabel.setPrefHeight(17.0);
        userLabel.setPrefWidth(200.0);
        userLabel.setText("Category :"+donation.getCategory()+"     "+"Name :"+donation.getName());

        Label dateLabel = new Label();
        dateLabel.setLayoutX(54.0);
        dateLabel.setLayoutY(31.0);
        dateLabel.setPrefHeight(17.0);
        dateLabel.setPrefWidth(99.0);
        dateLabel.setText(String.valueOf(donation.getDate_don()));

        Label contentLabel = new Label();
        contentLabel.setLayoutX(66.0);
        contentLabel.setLayoutY(57.0);
        contentLabel.setPrefHeight(75.0);
        contentLabel.setPrefWidth(538.0);
        contentLabel.setText("Descreption : "+donation.getDescription());
        Feedback_don c;
        try {
            c=feedbackDonService.getOneById(donation.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Label commentLabel = new Label();
        commentLabel.setLayoutX(103.0);
        commentLabel.setLayoutY(354.0);
        commentLabel.setPrefHeight(17.0);
        commentLabel.setPrefWidth(490.0);


        Label commentDataLabel = new Label();
        commentDataLabel.setLayoutX(114.0);
        commentDataLabel.setLayoutY(371.0);
        commentDataLabel.setPrefHeight(42.0);
        commentDataLabel.setPrefWidth(490.0);
        if(c!=null){commentLabel.setText("Lasted Feedback at "+String.valueOf(c.getDate_feedback()));commentDataLabel.setText("Feedback data : "+c.getDescription());}


        TextArea commentTextArea = new TextArea();
        commentTextArea.setLayoutX(114.0);
        commentTextArea.setLayoutY(425.0);
        commentTextArea.setPrefHeight(59.0);
        commentTextArea.setPrefWidth(410.0);
        commentTextArea.setPromptText("New Feedback");

        Label CheckComment = new Label("*Insert a content");
        CheckComment.setLayoutX(114.0);
        CheckComment.setLayoutY(484.0);
        CheckComment.setTextFill(Color.RED);
        CheckComment.setVisible(false);

        Button addBtn = new Button();
        addBtn.setLayoutX(538.0);
        addBtn.setLayoutY(442.0);
        addBtn.setMnemonicParsing(false);
        addBtn.setPrefHeight(25.0);
        addBtn.setPrefWidth(81.0);
        addBtn.setText("ADD");

        addBtn.setOnAction(actionEvent -> {
            if (commentTextArea.getText()!=""){
                Feedback_don feedback_don=new Feedback_don();
                feedback_don.setUser_id(user_id);
                feedback_don.setDate_feedback(new Date());
                feedback_don.setDonation_id(donation.getId());
                feedback_don.setDescription(commentTextArea.getText());
                loadDonations(choix);

                try {
                    feedbackDonService.ajouter(feedback_don);
                    showSuccessAlert("Feedback added successfully");
                } catch (SQLException e) {
                    showErrorAlert("Error adding the Feedback : " + e.getMessage());
                }

            }
            else{CheckComment.setVisible(true);}
        });

        ImageView editImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/edit.png")));
        editImageView.setFitHeight(42.0);
        editImageView.setFitWidth(35.0);
        editImageView.setLayoutX(460.0);
        editImageView.setLayoutY(10.0);
        editImageView.setPickOnBounds(true);
        editImageView.setPreserveRatio(true);

        editImageView.setOnMouseClicked(event -> {

            modifierDonation(donation);

        });
        /////
        ImageView daitailleImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/projects.png")));
        daitailleImageView.setFitHeight(42.0);
        daitailleImageView.setFitWidth(35.0);
        daitailleImageView.setLayoutX(578.0);
        daitailleImageView.setLayoutY(10.0);
        daitailleImageView.setPickOnBounds(true);
        daitailleImageView.setPreserveRatio(true);

        daitailleImageView.setOnMouseClicked(event -> {
            daitailleDonate(donation);
        });
        /////

        ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/delete.png")));
        deleteImageView.setFitHeight(35.0);
        deleteImageView.setFitWidth(42.0);
        deleteImageView.setLayoutX(519.0);
        deleteImageView.setLayoutY(10.0);
        deleteImageView.setPickOnBounds(true);
        deleteImageView.setPreserveRatio(true);

        deleteImageView.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Voulez-vous vraiment supprimer cette Feedback ?");
            alert.setContentText("Cette action est irréversible.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    donationService.supprimer(donation.getId());
                    loadDonations(choix);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        if (imagepth != null) {
            File imageFile = new File(imagepth);
            if (imageFile.exists()) {
                String imageUrl = imageFile.toURI().toString();
                imageView.setImage(new Image(imageUrl));
                imageView.setVisible(true);
            } else {
                System.out.println("Fichier d'image introuvable : " + imagepth);
            }
        }




        if (choix == 1) {
            pane.getChildren().addAll( imageView, userLabel,
                    dateLabel, contentLabel, commentLabel, commentDataLabel, commentTextArea, addBtn,CheckComment,daitailleImageView);
        } else {
            pane.getChildren().addAll( imageView, userLabel,
                    dateLabel, contentLabel, commentLabel, commentDataLabel, commentTextArea, addBtn, editImageView, deleteImageView,CheckComment,daitailleImageView);
        }

        cardContainer.getChildren().add(pane);
        return cardContainer;
    }
    void daitailleDonate(Donation donation){
        Scene scene1 = ScrollePan.getScene();
        Stage stage1 = (Stage) scene1.getWindow();
        stage1.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/DonatFeedback.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            DonateFeedback controller = loader.getController();
            controller.initialize(donation);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Donation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    void modifierDonation(Donation donation){
       Scene scene1 = ScrollePan.getScene();
        Stage stage1 = (Stage) scene1.getWindow();
        stage1.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ModifierDonation.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            ModiferDonnationController controller = loader.getController();
            controller.initialize(donation);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit donation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    void goAddDonation() throws IOException {
        Scene scenefer = ScrollePan.getScene();
        Stage stagefer = (Stage) scenefer.getWindow();
        stagefer.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/User/AddDonnation.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void godonate() throws IOException {
        Scene scenefer = ScrollePan.getScene();
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
