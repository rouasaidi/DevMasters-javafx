package com.esprit.controllers.Article;

import com.esprit.models.Article;
import com.esprit.models.User;
import com.esprit.services.ArticleService;
import com.esprit.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

public class AfficherArticleController {

    @FXML
    private ListView<Article> articlesListView;
    private ObservableList<Article> articlesObservableList;

    @FXML
    private TextField tfTitle, tfContent, tfLikes, tfDislikes, tfDate, tfImage;

    @FXML
    private VBox detailsPane;

    private ArticleService articleService;

    @FXML
    private StackPane contentPane;
    private UserService userService;

    public void setMainContent(StackPane contentPane) {
        this.contentPane = contentPane;
    }

    @FXML
    public void initialize() {
        articleService = new ArticleService();
        userService = new UserService() ;
        articlesObservableList = FXCollections.observableArrayList();
        articlesListView.setItems(articlesObservableList);
        refreshArticlesList();

        detailsPane.setVisible(false);

        articlesListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Article item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox vBox = new VBox(
                            new Label("Title: " + item.getTitle()),
                            new Label("Content: " + item.getContent()),
                            new Label("Likes: " + item.getLikes()),
                            new Label("Dislikes: " + item.getDislikes()),
                            new Label("Date: " + item.getDate()),
                            new Label("Image URL: " + item.getImage())
                    );
                    vBox.setSpacing(4);
                    setGraphic(vBox);
                }
            }
        });

        articlesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tfTitle.setText(newValue.getTitle());
                tfContent.setText(newValue.getContent());
                tfLikes.setText(String.valueOf(newValue.getLikes()));
                tfDislikes.setText(String.valueOf(newValue.getDislikes()));
                tfDate.setText(newValue.getDate().toString());
                tfImage.setText(newValue.getImage());
                detailsPane.setVisible(true);
            }
        });
    }

    private void refreshArticlesList() {
        articlesObservableList.setAll(articleService.getAll());
    }

    @FXML
    private void deleteArticle(ActionEvent event) {
        Article selected = articlesListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this article?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    User user = userService.getUserById(selected.getUserId());
                    String email =  user.getEmail() ;
                    try {
                        sendEmailWithQRCode(email , "Delete Of Article", "Ur Article has been deleted");
                    } catch (MessagingException | IOException e) {
                        throw new RuntimeException(e);
                    }

                    articleService.supprimer(selected.getId());
                    refreshArticlesList();
                    detailsPane.setVisible(false);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Article deleted successfully!");
                    successAlert.showAndWait();
                }
            });
        }
    }

    private void sendEmailWithQRCode(String recipient, String subject, String text) throws MessagingException, IOException {
        String from = "malek.chkoundali@esprit.tn";
        final String username = "malek.chkoundali@esprit.tn";
        final String password = "farahBOUTAR222";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(text);


        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);


        message.setContent(multipart);
        Transport.send(message);
    }
}
