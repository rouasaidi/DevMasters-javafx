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
<<<<<<< HEAD
=======
import javafx.scene.layout.StackPane;
>>>>>>> 5f185cf (Heeeeeeello)
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
<<<<<<< HEAD
=======
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
>>>>>>> 5f185cf (Heeeeeeello)
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
<<<<<<< HEAD
=======
    @FXML
    void loaUserFavarieListe() {
        choix = 3;
        loadDonations(choix);
    }
>>>>>>> 5f185cf (Heeeeeeello)

    private void loadDonations(int choix) {
        FlowPane donationFlowPane = new FlowPane();
        donationFlowPane.setStyle("-fx-pref-width: 950px; " +
                "-fx-pref-height: 547px;");

        List<Donation> donationList;
        try {
            if (choix == 1) {
                donationList = donationService.getAll();
<<<<<<< HEAD
            } else {
                donationList = donationService.getByIdUser(user_id);
            }
=======
            } else if (choix == 2){
                donationList = donationService.getByIdUser(user_id);
            }
            else{
                donationList = donationService.getUserFavorieliste(user_id);
            }
>>>>>>> 5f185cf (Heeeeeeello)

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
<<<<<<< HEAD
                loadDonations(choix);
=======
>>>>>>> 5f185cf (Heeeeeeello)

                try {
                    feedbackDonService.ajouter(feedback_don);
                    showSuccessAlert("Feedback added successfully");
                } catch (SQLException e) {
                    showErrorAlert("Error adding the Feedback : " + e.getMessage());
                }

            }
            else{CheckComment.setVisible(true);}
        });

<<<<<<< HEAD
=======
        ImageView favimag = new ImageView(new Image(getClass().getResourceAsStream("/image/Favorie.jpeg")));
        favimag.setFitHeight(42.0);
        favimag.setFitWidth(35.0);
        if (choix==2){
            favimag.setLayoutX(401.0);
            favimag.setLayoutY(10.0);
        }
        else {
            favimag.setLayoutX(519.0);
            favimag.setLayoutY(10.0);

        }
        favimag.setPickOnBounds(true);
        favimag.setPreserveRatio(true);


        ImageView notfavimag = new ImageView(new Image(getClass().getResourceAsStream("/image/NotFavorie.png")));
        notfavimag.setFitHeight(42.0);
        notfavimag.setFitWidth(35.0);
        if (choix==2){
            notfavimag.setLayoutX(401.0);
            notfavimag.setLayoutY(10.0);
            }
        else {
            notfavimag.setLayoutX(519.0);
            notfavimag.setLayoutY(10.0);
        }
        notfavimag.setPickOnBounds(true);
        notfavimag.setPreserveRatio(true);

        favimag.setOnMouseClicked(event -> {
            try {
                donationService.deletfavorie(donation.getId(),user_id);
                favimag.setVisible(false);
                notfavimag.setVisible(true);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        notfavimag.setOnMouseClicked(event -> {
            try {
                donationService.Addfavorie(donation.getId(),user_id);
                favimag.setVisible(true);
                notfavimag.setVisible(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        ImageView qr = new ImageView(new Image(getClass().getResourceAsStream("/image/Qr.png")));
        qr.setFitHeight(42.0);
        qr.setFitWidth(35.0);
        if (choix==2){
            qr.setLayoutX(342.0);
            qr.setLayoutY(10.0);
           }
        else {
            qr.setLayoutX(460.0);
            qr.setLayoutY(10.0);
        }
        qr.setPickOnBounds(true);
        qr.setPreserveRatio(true);
        qr.setOnMouseClicked(event->{
            generateQRCode(donation);
        });
>>>>>>> 5f185cf (Heeeeeeello)
        ImageView editImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/edit.png")));
        editImageView.setFitHeight(42.0);
        editImageView.setFitWidth(35.0);
        editImageView.setLayoutX(460.0);
        editImageView.setLayoutY(10.0);
        editImageView.setPickOnBounds(true);
        editImageView.setPreserveRatio(true);

        editImageView.setOnMouseClicked(event -> {
<<<<<<< HEAD

            modifierDonation(donation);

=======
            modifierDonation(donation);
>>>>>>> 5f185cf (Heeeeeeello)
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
<<<<<<< HEAD
                    loadDonations(choix);
=======
>>>>>>> 5f185cf (Heeeeeeello)
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

<<<<<<< HEAD



        if (choix == 1) {
            pane.getChildren().addAll( imageView, userLabel,
                    dateLabel, contentLabel, commentLabel, commentDataLabel, commentTextArea, addBtn,CheckComment,daitailleImageView);
        } else {
            pane.getChildren().addAll( imageView, userLabel,
                    dateLabel, contentLabel, commentLabel, commentDataLabel, commentTextArea, addBtn, editImageView, deleteImageView,CheckComment,daitailleImageView);
        }

=======
        try {
            if(donationService.isFavorie(donation.getId(),user_id)){
                notfavimag.setVisible(false);
            }
            else favimag.setVisible(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (choix == 2) {
            pane.getChildren().addAll( imageView, userLabel,favimag,qr,notfavimag,
                    dateLabel, contentLabel, commentLabel, commentDataLabel, commentTextArea, addBtn, editImageView, deleteImageView,CheckComment,daitailleImageView);
        } else {
            pane.getChildren().addAll( imageView, userLabel,favimag,qr,notfavimag,
                    dateLabel, contentLabel, commentLabel, commentDataLabel, commentTextArea, addBtn,CheckComment,daitailleImageView);
        }


>>>>>>> 5f185cf (Heeeeeeello)
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
<<<<<<< HEAD
=======
    void generateQRCode(Donation donation){
         String userId = "IsmailChouikhi";
         String apiKey = "cyEIkNQxPmSmlONcs32Qm2io2pl2aPLi291SlS2YEYU6H6RN";
         String apiUrl = "https://neutrinoapi.net/qr-code";
         int width = 256;
         int height = 256;
         String fgColor = "#000000";
         String bgColor = "#ffffff";
         String content ="Nam :" + donation.getName() + ",\n\n"+
                        "Category :" + donation.getCategory() + ",\n\n"+
                        "Description :" + donation.getDescription() + ",\n\n"+
                        "Quantity :" + donation.getQuantity() + ",\n\n"+
                        "Date :" + donation.getDate_don() + ",\n\n"+
                        "Status :" + donation.getStatus() + ",\n\n";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-ID", userId);
            connection.setRequestProperty("API-Key", apiKey);
            connection.setDoOutput(true);

            String postData = "width=" + width + "&height=" + height + "&fg-color=" + fgColor +
                    "&bg-color=" + bgColor + "&content=" + content;
            connection.getOutputStream().write(postData.getBytes("UTF-8"));

            InputStream inputStream = connection.getInputStream();

            if (inputStream != null) {
                Image image = new Image(inputStream);
                showQRCodeDialog(image);
            } else {
                System.out.println("Failed to download QR Code image. Input stream is null.");
            }

            inputStream.close();
            System.out.println("QR Code downloaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showQRCodeDialog(Image image) {
        Stage stage = new Stage();
        ImageView imageView = new ImageView(image);
        stage.setScene(new Scene(new StackPane(imageView)));
        stage.show();
    }
>>>>>>> 5f185cf (Heeeeeeello)
}
