package tn.esprit.devmasters.gui;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.chart.PieChart;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tn.esprit.devmasters.Main;
import tn.esprit.devmasters.models.Category;
import tn.esprit.devmasters.models.Product;
import tn.esprit.devmasters.services.CategoryService;
import tn.esprit.devmasters.services.ProductService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;



import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;


import com.itextpdf.layout.Document;




import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.Paths;
//-----------------------------backend
public class ProductController {
    ProductService productService = new ProductService();
    CategoryService categoryService = new CategoryService();

    Product selectedProduct = null;
    private final int userID = 1;

    @FXML
    private TableView<Product> productsTable;
    @FXML
    private Button Qrcode;
    @FXML
    private TableColumn<Product, String> category_col, image_col, name_col;
    @FXML
    private TableColumn<Product, Integer> id_col, qte_col, total_col, user_col;
    @FXML
    private TableColumn<Product, Float> price_col;

    @FXML
    private TextField input_image, input_name, input_price, input_qte, input_total, input_description;

    @FXML
    private ComboBox<Integer> select_category;

    @FXML
    private Button submit_btn;

    @FXML
    private Label error_name, error_price, error_qte, error_total, error_description, error_category, error_image;

    @FXML
    private GridPane products_grid;

    public void initialize() {
        try {
            List<Category> categories = categoryService.findAll();
            for (Category category : categories) {
                select_category.getItems().add(category.getId());
            }
            loadProducts();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void goToCategory() throws IOException {
        // Load Category
        Stage stage = (Stage) submit_btn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/Category.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root, 1200, 700));
    }

    @FXML
    void onDelete() {
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        try {
            productService.delete(selectedProduct);
            loadProducts();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void onEdit() {
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        select_category.setValue(selectedProduct.getCategoryID());
        input_image.setText(selectedProduct.getImage());
        input_name.setText(selectedProduct.getName());
        input_price.setText(String.valueOf(selectedProduct.getPrice()));
        input_qte.setText(String.valueOf(selectedProduct.getQuantity()));
        input_total.setText(String.valueOf(selectedProduct.getTotalSales()));
        input_description.setText(selectedProduct.getDescription());

        submit_btn.setText("Modifier");
    }

    @FXML
    void onSubmit() {
        String state = submit_btn.getText();
        Boolean status = checkProduct();

        if (status)
            if (state.equals("Modifier")) {
                updateProduct();
            } else {
                addProduct();
            }
    }

    void loadProducts() throws SQLException {
        List<Product> products = productService.findAll();
        productsTable.getItems().setAll(products);

        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        category_col.setCellValueFactory(new PropertyValueFactory<>("categoryID"));
        image_col.setCellValueFactory(new PropertyValueFactory<>("image"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        qte_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        total_col.setCellValueFactory(new PropertyValueFactory<>("totalSales"));
        user_col.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    void addProduct() {
        Product product = new Product(
                input_name.getText(),
                input_description.getText(),
                Integer.parseInt(input_qte.getText()),
                Float.parseFloat(input_price.getText()),
                Integer.parseInt(input_total.getText()),
                input_image.getText(),
                userID,
                Integer.parseInt(select_category.getValue().toString())
        );

        try {
            productService.add(product);
            loadProducts();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        Notifications notification = Notifications.create()
                .title("Product")
                .text("Product Added successfully ")
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .graphic(null) // No graphic
                .darkStyle() // Use dark style for better visibility
                .hideCloseButton(); // Hide close button

// Apply the CSS styling directly
        //notification.showInformation(); // Show the notification as information style

// Apply the CSS styling directly
        notification.show();
    }

    void updateProduct() {
        Product product = new Product(
                selectedProduct.getId(),
                input_name.getText(),
                input_description.getText(),
                Integer.parseInt(input_qte.getText()),
                Float.parseFloat(input_price.getText()),
                Integer.parseInt(input_total.getText()),
                input_image.getText(),
                userID,
                Integer.parseInt(select_category.getValue().toString())
        );

        try {
            productService.update(product);
            loadProducts();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
//---------notif start
        Notifications notification = Notifications.create()
                .title("Product")
                .text("Product Updated successfully ")
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .graphic(null) // No graphic
                .darkStyle() // Use dark style for better visibility
                .hideCloseButton(); // Hide close button

// Apply the CSS styling directly
        //notification.showInformation(); // Show the notification as information style

// Apply the CSS styling directly
        notification.show();
//---------notif end


    }

    Boolean checkProduct() {
        if (input_name.getText().isEmpty())
            error_name.setText("Le nom est obligatoire");
        else
            error_name.setText("");

        if (input_price.getText().isEmpty())
            error_price.setText("Le prix est obligatoire");
        else if (!input_price.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
            error_price.setText("Le prix doit être un nombre");
        else if (Float.parseFloat(input_price.getText()) <= 0)
            error_price.setText("Le prix doit être supérieur à 0");
        else
            error_price.setText("");

        if (input_qte.getText().isEmpty())
            error_qte.setText("La quantité est obligatoire");
        else if (!input_qte.getText().matches("^[0-9]+$"))
            error_qte.setText("La quantité doit être un nombre positive");
        else
            error_qte.setText("");

        if (input_total.getText().isEmpty())
            error_total.setText("Le total des ventes est obligatoire");
        else if (!input_total.getText().matches("^[0-9]+$"))
            error_total.setText("Le total des ventes doit être un nombre");
        else
            error_total.setText("");

        if (input_description.getText().isEmpty())
            error_description.setText("La description est obligatoire");
        else
            error_description.setText("");

        if (select_category.getValue() == null)
            error_category.setText("La catégorie est obligatoire");
        else
            error_category.setText("");

        if (input_image.getText().isEmpty())
            error_image.setText("L'image est obligatoire");
        else
            error_image.setText("");

        return error_name.getText().isEmpty() && error_price.getText().isEmpty() && error_qte.getText().isEmpty() && error_total.getText().isEmpty() && error_description.getText().isEmpty() && error_category.getText().isEmpty() && error_image.getText().isEmpty();
    }


    @FXML
    void handleGeneratePdfButton(ActionEvent event) {
        try (PdfWriter writer = new PdfWriter("products.pdf");
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {
            document.add(new Paragraph("Liste des products"));
            Table table = new Table(6);
            table.addCell("ID");
            table.addCell("getName");
            table.addCell("Description");
            table.addCell("Quantity");
            table.addCell("Price");
            table.addCell("TotalSales");
            // Récupération des données des matériels depuis la TableView
            ObservableList<Product> products = productsTable.getItems();
            for (Product product : products) {
                table.addCell(String.valueOf(product.getId()));
                table.addCell(product.getName());
                table.addCell(product.getDescription());
                table.addCell(String.valueOf(product.getQuantity()));
                table.addCell(String.valueOf(product.getPrice()));
                table.addCell(String.valueOf(product.getTotalSales()));
            }

            document.add(table);

            System.out.println("PDF généré avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void QRCODE(ActionEvent event) {

    }





    @FXML
    void qccode(ActionEvent event) {
        try {
            String data = "http://127.0.0.1:8000/signup";
            String path = "C:\\Users\\ROUAA\\Desktop\\DevMasters-javafx\\QR2.jpg";
            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
            MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
            System.out.println("QR Code généré avec succès.");

            // Afficher une alerte de succès
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("QR Code généré");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Le QR Code a été généré avec succès !");
            successAlert.showAndWait();
        } catch (IOException | WriterException e) {
            System.out.println("Une erreur est survenue lors de la génération du code QR : " + e.getMessage());

            // Afficher une alerte d'erreur
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Une erreur est survenue lors de la génération du QR Code : " + e.getMessage());
            errorAlert.showAndWait();
        }
    }


    @FXML
    void showStatsPage(ActionEvent event) {
        try {
            // Load the FXML file for the statistics page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/devmasters/views/product_stat.fxml"));
            Parent root = loader.load();

            // Get the controller associated with the loaded FXML
            productStatController statController = loader.getController();

            // Refresh the pie chart in the statistics page
            statController.refreshPieChart();

            // Create a new stage for the statistics page
            Stage statsStage = new Stage();
            statsStage.setTitle("Product Statistics");
            statsStage.initModality(Modality.APPLICATION_MODAL);
            statsStage.setScene(new Scene(root));

            // Show the statistics page
            statsStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
