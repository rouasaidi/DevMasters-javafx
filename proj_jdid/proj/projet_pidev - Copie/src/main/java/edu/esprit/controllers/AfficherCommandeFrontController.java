package edu.esprit.controllers;
import edu.esprit.entities.Commande;
import edu.esprit.services.ServiceCommande;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.List;


public class AfficherCommandeFrontController implements Initializable{
    @FXML
    private ListView<Commande> listView;

    int user_id =3 ;

    @FXML
    void DetailsCommande(ActionEvent event) {
        Commande selectedCommande = listView.getSelectionModel().getSelectedItem();
        if (selectedCommande != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowCommande.fxml"));
                Parent root = loader.load();
               ShowCommandeController controller = loader.getController();
                controller.initData(selectedCommande); // Pass selectedCommande to ModifierCommandeController
                Scene scene = listView.getScene();
                scene.setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void NavigateToAcc(ActionEvent event) {




    }

    @FXML
    void supprimerCommande(ActionEvent event) {
        // Retrieve the selected item from the ListView
        Commande commandeSelectionnee = listView.getSelectionModel().getSelectedItem();

        if (commandeSelectionnee != null) {
            try {
                // Delete the selected command
                ServiceCommande sc = new ServiceCommande();
                sc.supprimer(commandeSelectionnee.getId());

                // Update the list after deletion
                listView.getItems().remove(commandeSelectionnee);
            } catch (SQLException e) {
                // Display an error in case of deletion failure
                throw new RuntimeException("Error while deleting the command", e);
            }
        } else {
            // Display an error message if no command is selected
            System.out.println("Please select a command to delete.");
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceCommande sc = new ServiceCommande();
        List<Commande> list = new ArrayList<>();
        try {
            list = sc.recuperer_par_id(user_id);
            ObservableList<Commande> observableCommandes = FXCollections.observableArrayList(list);
            listView.setItems(observableCommandes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void retour_au_panier(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/AfficherPanier.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listView.getScene().setRoot(root);
    }

    @FXML
    void tf_PDF(ActionEvent event) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            System.out.println("salemmm");

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Draw the logo with padding from the top
           // PDImageXObject pdImage = PDImageXObject.createFromFile("src/main/resources/images/add-document.png", document);
            //contentStream.drawImage(pdImage, 20, 690, 100, 50); // Adjusted padding from the top

            // Title
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
            contentStream.newLineAtOffset(200, 720);
            contentStream.showText("Facture");
            contentStream.endText();

            // Name client
            float yStart = 690; // Initial y position
            String clientName = "John Doe"; // Exemple de nom de client
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(200, yStart);
            contentStream.showText("Client Name: " + clientName); // Remplacez par le nom du client réel
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -20); // Space between name and address
            contentStream.showText("Company Address: 1234 Street Name, City, Country");
            contentStream.endText();

            yStart -= 60; // Additional space before starting table

            // Table setup
            float margin = 50;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float rowHeight = 20;
            int numCols = 3;
            int numRows = 3; // Number of products + headers
            float tableHeight = numRows * rowHeight;

            // Draw Table Borders
            drawTableBorders(contentStream, margin, yStart, tableWidth, tableHeight, numRows, numCols);

            // Draw the table headers
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            float textx = margin;
            float texty = yStart - 15;
            String[] headers = {"  Product Name", "  Quantity", "  Price Total"};
            for (String header : headers) {
                contentStream.beginText();
                contentStream.newLineAtOffset(textx, texty);
                contentStream.showText(header);
                contentStream.endText();
                textx += tableWidth / numCols;
            }

            // Draw table rows
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            texty -= rowHeight;

            // Draw total price
            textx = margin;
            texty -= rowHeight;
            float totalPrice = 100.0f; // Exemple de prix total
            contentStream.beginText();
            contentStream.newLineAtOffset(textx, texty);
            contentStream.showText("Total Price: $ " + totalPrice); // Remplacez par le prix total réel
            contentStream.endText();

            contentStream.close();
            document.save("command.pdf");

            // Open the generated PDF file automatically
            Desktop.getDesktop().open(new File("command.pdf"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawTableBorders(PDPageContentStream contentStream, float startX, float startY, float width, float height, int numRows, int numCols) throws IOException {
        contentStream.setLineWidth(1f);
        float cellWidth = width / numCols;
        float cellHeight = height / numRows;

        // Draw horizontal lines
        for (int i = 0; i <= numRows; i++) {
            contentStream.moveTo(startX, startY - (i * cellHeight));
            contentStream.lineTo(startX + width, startY - (i * cellHeight));
            contentStream.stroke();
        }

        // Draw vertical lines
        for (int i = 0; i <= numCols; i++) {
            contentStream.moveTo(startX + (i * cellWidth), startY);
            contentStream.lineTo(startX + (i * cellWidth), startY - height);
            contentStream.stroke();
        }
    }
    }


