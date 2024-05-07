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
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;



import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AfficherCommandeController implements Initializable {

    @FXML
    private TextField TFrecherche;

    @FXML
    private ListView<Commande> listView;

    @FXML
    void NavigateToAcc(ActionEvent event) {

    }

    @FXML
    void ajouterCommande(ActionEvent event) {
        Parent  root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/AjouterCommande.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TFrecherche.getScene().setRoot(root);

    }

    @FXML
    void modifierCommande(ActionEvent event) {
        Commande selectedCommande = listView.getSelectionModel().getSelectedItem();
        if (selectedCommande != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCommande.fxml"));
                Parent root = loader.load();
                ModifierCommandeController controller = loader.getController();
                controller.initData(selectedCommande); // Pass selectedCommande to ModifierCommandeController
                Scene scene = listView.getScene();
                scene.setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @FXML
    void supprimerCommande(ActionEvent event) {
        Commande selectedcommande = listView.getSelectionModel().getSelectedItem();
        if (selectedcommande != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette commande?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    int id = selectedcommande.getId();
                    ServiceCommande serviceCommande = new ServiceCommande();
                    serviceCommande.supprimer(id);
                    listView.getItems().remove(selectedcommande);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceCommande serviceCommande = new ServiceCommande();
        try {
            // Récupérer les commandes sous forme d'une ArrayList
            List<Commande> commandes = serviceCommande.recuperer();
            // Convertir l'ArrayList en ObservableList
            ObservableList<Commande> observableCommandes = FXCollections.observableArrayList(commandes);
            // Définir les éléments de la ListView avec l'ObservableList
            listView.setItems(observableCommandes);

            // Ajouter un ChangeListener à la TFrecherche pour détecter les changements de texte
            TFrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
                // Appeler la méthode filtrerCommandes avec le nouveau texte de recherche
                filtrerCommandes(newValue);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void filtrerCommandes(String recherche) {
        // Si la recherche est vide, afficher toutes les commandes
        if (recherche.isEmpty()) {
            try {
                // Récupérer toutes les commandes
                ServiceCommande serviceCommande = new ServiceCommande();
                List<Commande> commandes = serviceCommande.recuperer();
                // Convertir en ObservableList et définir dans la ListView
                ObservableList<Commande> observableCommandes = FXCollections.observableArrayList(commandes);
                listView.setItems(observableCommandes);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        // Filtrer les commandes en fonction de la date de validation
        ObservableList<Commande> commandesFiltrees = FXCollections.observableArrayList(
                listView.getItems()
                        .stream()
                        .filter(commande -> {
                            // Compare the date string from the Commande object with the provided recherche
                            return commande.getDate_validation().contains(recherche);
                        })
                        .collect(Collectors.toList())
        );

        // Afficher les commandes filtrées dans la ListView
        listView.setItems(commandesFiltrees);
    }



    @FXML
    void tri_par_prx_asc(ActionEvent event) {
        try {
            // Récupérer les commandes sous forme d'une ArrayList
            ServiceCommande serviceCommande = new ServiceCommande();
            List<Commande> commandes = serviceCommande.recuperer();

            // Trier les commandes par prix de manière ascendante
            trierParPrixAsc(commandes);

            // Convertir l'ArrayList triée en ObservableList
            ObservableList<Commande> observableCommandes = FXCollections.observableArrayList(commandes);

            // Mettre à jour la ListView avec les commandes triées
            listView.setItems(observableCommandes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void tri_par_prx_desc(ActionEvent event) {
        try {
            // Récupérer les commandes sous forme d'une ArrayList
            ServiceCommande serviceCommande = new ServiceCommande();
            List<Commande> commandes = serviceCommande.recuperer();

            // Trier les commandes par prix de manière descendante
            trierParPrixDesc(commandes);

            // Convertir l'ArrayList triée en ObservableList
            ObservableList<Commande> observableCommandes = FXCollections.observableArrayList(commandes);

            // Mettre à jour la ListView avec les commandes triées
            listView.setItems(observableCommandes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void trierParPrixAsc(List<Commande> commandes) {
        Collections.sort(commandes, Comparator.comparingInt(Commande::getTotal_price));
    }

    private void trierParPrixDesc(List<Commande> commandes) {
        Collections.sort(commandes, (c1, c2) -> Integer.compare(c2.getTotal_price(), c1.getTotal_price()));
    }
    @FXML
    void top3(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Top3.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TFrecherche.getScene().setRoot(root);

    }

    private void drawRow(PDPageContentStream contentStream, float y, float tableWidth, float rowHeight, String[] rowData, float[] columnWidths) throws IOException {
        float x = 50;
        float rowY = y - rowHeight; // Y-coordinate for the bottom of the row
        contentStream.setStrokingColor(Color.BLACK);

        // Draw horizontal lines for the row
        contentStream.moveTo(50, rowY);
        contentStream.lineTo(tableWidth + 50, rowY);
        contentStream.stroke();

        for (int i = 0; i < 3; i++) { // Ensure only 3 columns are drawn
            String text = i < rowData.length ? rowData[i] : ""; // Fill empty columns with empty strings
            // Draw vertical lines for each cell
            contentStream.moveTo(x, y);
            contentStream.lineTo(x, rowY);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.setNonStrokingColor(Color.WHITE); // Set text color to white
            contentStream.newLineAtOffset(x + 2, y - rowHeight + 2); // Offset for text within cell
            contentStream.showText(text);
            contentStream.endText();

            x += columnWidths[i];
        }

        // Draw vertical line at the end of the row
        contentStream.moveTo(x, y);
        contentStream.lineTo(x, rowY);
        contentStream.stroke();

        // Draw horizontal line at the top of the row
        contentStream.moveTo(50, y);
        contentStream.lineTo(tableWidth + 50, y);
        contentStream.stroke();
    }



    @FXML
    public void PDF(ActionEvent event) {
        List<Commande> commandes = listView.getItems(); // Get the list of commands from the ListView

        // Prompt the user to choose a file location
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("commandes.pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(TFrecherche.getScene().getWindow());

        if (file != null) {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                // Chargement de l'image
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Draw the logo covering the entire page
                PDImageXObject pdImage = PDImageXObject.createFromFile("C:\\Users\\mohamed ali\\Desktop\\projet_java\\proj\\proj\\projet_pidev - Copie\\src\\img\\joysprout_pic.jpg", document);
                contentStream.drawImage(pdImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;
                float rowHeight = 20;

                // Write title "Liste des commandes" in light blue above the table
                contentStream.setNonStrokingColor(new Color(0, 191, 255)); // Light blue color
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                contentStream.newLineAtOffset(margin, yPosition); // Position for the title
                contentStream.showText("Liste des commandes");
                contentStream.endText();
                yPosition -= 30; // Adjusting y position for the title

                // Define table headers
                String[] headers = { "Date Validation", "Total Price", "Total Quantity"};
                float[] columnWidths = { 150, 150, 197}; //
                drawRow(contentStream, yPosition, tableWidth, rowHeight, headers, columnWidths);
                yPosition -= rowHeight;

                // Write each command to the table
                for (Commande commande : commandes) {
                    String[] rowData = {
                            commande.getDate_validation(),
                            String.valueOf(commande.getTotal_price()),
                            String.valueOf(commande.getTotal_quant())
                    };
                    // Truncate rowData to ensure only three columns are drawn
                    String[] rowDataTrimmed = Arrays.copyOf(rowData, 3);
                    drawRow(contentStream, yPosition, tableWidth, rowHeight, rowDataTrimmed, columnWidths);
                    yPosition -= rowHeight;
                }

                contentStream.close();

                // Save the document to the selected file
                document.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}









