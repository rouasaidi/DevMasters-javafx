package tn.esprit.devmasters.gui;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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

}
