package tn.esprit.devmasters.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.devmasters.Main;
import tn.esprit.devmasters.models.Category;
import tn.esprit.devmasters.models.Product;
import tn.esprit.devmasters.services.CategoryService;
import tn.esprit.devmasters.services.ProductService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProductFrontController {
    ProductService productService = new ProductService();

    Product selectedProduct = null;
    private final int userID = 1;

    @FXML
    private GridPane products_grid;

    public void initialize() {
        try {
            loadGridProducts();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    void loadGridProducts() throws SQLException {
        products_grid.getChildren().clear();
        List<Product> products = productService.findAll();
        int row = 1;
        int col = 0;
        for (Product product : products) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/ProductCard.fxml"));
            try {
                VBox card = fxmlLoader.load();
                ProductCardController productCardController = fxmlLoader.getController();
                productCardController.setProduct(product);
                products_grid.add(card, col++, row);

                if (col > 3) {
                    col = 0;
                    row++;
                }

                GridPane.setMargin(card, new Insets(10));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void goToAddProduct(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/AddProductFront.fxml"));
            Parent parent = fxmlLoader.load();
            stage.setScene(new Scene(parent));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
