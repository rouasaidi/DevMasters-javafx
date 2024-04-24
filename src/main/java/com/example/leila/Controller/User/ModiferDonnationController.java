package com.example.leila.Controller.User;

import com.example.leila.HelloApplication;
import com.example.leila.Models.Donation;
import com.example.leila.Services.DonationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
public class ModiferDonnationController {
    @FXML
    private ComboBox<String> categoryCamcox;

    @FXML
    private Label checkCategorie;

    @FXML
    private Label checkimage;

    @FXML
    private Label checkname;

    @FXML
    private Label checkquantity;

    @FXML
    private Label checkdescreption;

    @FXML
    private ImageView imageview;

    @FXML
    private TextField nameF;

    @FXML
    private Spinner<Integer> quantity;

    @FXML
    private TextArea descreptionF;
    private final DonationService donationService = new DonationService();
    private String imagePath="";
    private Donation donation;
    @FXML
    void initialize(Donation donation) {
        this.donation=donation;

        ObservableList<String> options = FXCollections.observableArrayList(
                "Toys",
                "Schools suplies",
                "Clothes"
        );
        categoryCamcox.setItems(options);
        nameF.setText(donation.getName());
        categoryCamcox.setValue(donation.getCategory());
        descreptionF.setText(donation.getDescription());
        quantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1));
        quantity.getValueFactory().setValue(donation.getQuantity());
        imagePath = donation.getImage();
        if (imagePath != null) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                String imageUrl = imageFile.toURI().toString();
                imageview.setImage(new Image(imageUrl));
                imageview.setVisible(true);
            }
        }
    }
    @FXML
    void modifier() {
        if (validateInput()) {
            try {
                createDonation();
                donationService.modifier(this.donation);
                showSuccessAlert("Donation modifiaction avec succès.");
                Scene scenefer = descreptionF.getScene();
                Stage stagefer = (Stage) scenefer.getWindow();
                stagefer.close();
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/User/donnotaions.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("");
                stage.setScene(scene);
                stage.show();
            } catch (SQLException | IOException e) {
                showErrorAlert("Erreur lors de l'modification de la donation : " + e.getMessage());
            }
        }
    }
    private boolean validateInput() {
        String name = nameF.getText().trim();
        String category = categoryCamcox.getValue();
        String quote = descreptionF.getText();
        int quantityValue = quantity.getValue();

        if (name.isEmpty()) {
            checkname.setVisible(true);
            return false;
        }
        if(category == null){
            checkCategorie.setVisible(true);
            return false;
        }
        if (quote.isEmpty()) {
            checkdescreption.setVisible(true);
            return false;
        }
        if (quantity.getValue() == null || quantity.getValue().intValue() <= 0) {
            checkquantity.setVisible(true);
            return false;
        }
        if (imagePath=="") {
            checkimage.setVisible(true);
            return false;
        }
        return true;
    }

    private void createDonation() {
        this.donation.setName(nameF.getText());
        this.donation.setCategory(categoryCamcox.getValue());
        this.donation.setDescription(descreptionF.getText());
        this.donation.setImage(imagePath);
        this.donation.setQuantity(quantity.getValue());
    }

    @FXML
    void addImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                imageview.setImage(image);
                imagePath = selectedFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorAlert("Erreur lors du chargement de l'image.");
            }
        }
    }



    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Succès");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void goDonation() throws IOException {
        Scene scenefer = descreptionF.getScene();
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