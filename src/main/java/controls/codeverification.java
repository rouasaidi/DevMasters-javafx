package controls;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.usercrud;
import utlis.MyBD;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

public class codeverification {


    @FXML
    private TextField code;

    @FXML
    private Button send;

    @FXML
    private TextField useremail;

    @FXML
    private Button verify;
    private String verificationCode;
    private String recipientEmail;
    @FXML
    void send(ActionEvent event) {
        // Récupérer l'e-mail de l'utilisateur à partir du champ de texte
         recipientEmail = useremail.getText();
            usercrud crud = new usercrud();
        // Vérifier si l'adresse e-mail est valide
        if (isValidEmail(recipientEmail)) {
            // Envoyer le code de vérification, peu importe si l'e-mail existe déjà ou non
            verificationCode = sendVerificationCode(recipientEmail, "NomUtilisateur", "NuméroTéléphone");
          //  String verificationCode = sendVerificationCode(recipientEmail, "NomUtilisateur", "NuméroTéléphone");
            System.out.println("Code de vérification envoyé avec succès à " + recipientEmail + ". Le code est : " + verificationCode);
        } else {
            // Afficher un message d'erreur si l'adresse e-mail n'est pas valide
            System.out.println("Adresse e-mail invalide.");
        }
    }



    @FXML
    void verify(ActionEvent event) {
        // Récupérer le code saisi par l'utilisateur à partir du champ de texte
        String enteredCode = code.getText();

        // Vérifier si le code saisi correspond au code envoyé
        if (enteredCode.equals(verificationCode)) {
            // Le code saisi est correct, afficher une alerte de succès
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Le code de vérification est correct.");

            // Afficher l'alerte de succès
            successAlert.showAndWait();

            try {
                // Charger le fichier FXML de l'interface cible
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/newpassword.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène
                Scene scene = new Scene(root);

                // Obtenir la scène actuelle à partir de l'événement
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Afficher la nouvelle scène
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Gérer l'erreur de chargement de l'interface
                System.out.println("Erreur lors du chargement de l'interface cible.");
            }
        } else {
            // Le code saisi est incorrect, afficher une alerte d'échec
            Alert failAlert = new Alert(Alert.AlertType.ERROR);
            failAlert.setTitle("Échec");
            failAlert.setHeaderText(null);
            failAlert.setContentText("Le code de vérification est incorrect. Veuillez réessayer.");

            // Afficher l'alerte d'échec
            failAlert.showAndWait();
        }
    }



    public String sendVerificationCode(String recipientEmail, String userName, String phoneNumber) {
        // Générer un code de vérification aléatoire
        String verificationCode = generateVerificationCode();

        // Configuration des propriétés de l'e-mail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "false"); // Pas besoin d'authentification pour MailHog
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", "localhost"); // Serveur SMTP de MailHog
        props.put("mail.smtp.port", "1025"); // Port par défaut de MailHog

        Session session = Session.getInstance(props);

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("jmilimouhamedamine@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Code de vérification");

            // Personnalisation du contenu du message avec le nom, le numéro de téléphone et le code de vérification
            String messageContent = "Votre code de vérification est : " + verificationCode;


            message.setText(messageContent);

            // Envoi du message
            Transport.send(message);
            System.out.println("Message envoyé à " + recipientEmail + " avec le code de vérification : " + verificationCode);

            // Retourner le code de vérification
            return verificationCode;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode pour générer un code de vérification aléatoire
    private String generateVerificationCode() {
        // Vous pouvez utiliser une logique pour générer un code aléatoire, par exemple un code numérique à 6 chiffres
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Générer un nombre aléatoire entre 100000 et 999999
        return String.valueOf(code);
    }
    private boolean isValidEmail(String email) {
        // Expression régulière pour vérifier si l'adresse email se termine par "@gmail.com" ou "@esprit.tn"
        return email.matches("[a-zA-Z0-9._%+-]+@(?:gmail\\.com|esprit\\.tn)");
    }

    @FXML
    void useremail(ActionEvent event) {

    }
    @FXML
    void code(ActionEvent event) {

    }

}
