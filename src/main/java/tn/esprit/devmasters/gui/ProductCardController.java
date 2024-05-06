package tn.esprit.devmasters.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tn.esprit.devmasters.Main;
import tn.esprit.devmasters.models.Product;
import tn.esprit.devmasters.services.CategoryService;

import java.io.IOException;
import java.sql.SQLException;

public class ProductCardController {
    Product selectedProduct;
    private final CategoryService categoryService = new CategoryService();

    @FXML
    private Text title, price, category;

    @FXML
    private ImageView image;

    @FXML
    void click(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/AddProductFront.fxml"));
        Parent addProductFront = fxmlLoader.load();
        AddProductController addProductController = fxmlLoader.getController();
        addProductController.setSelectedProduct(selectedProduct);
        Scene currentScene = ((Node) event.getSource()).getScene();
        currentScene.setRoot(addProductFront);

    }

    public void setProduct(Product product) {
        selectedProduct = product;
        System.out.println(selectedProduct);
        title.setText(product.getName());
        price.setText(product.getPrice() + " DT");
        try {
            Image img = new Image(Main.class.getResource("assets/"+product.getImage()).toExternalForm());
            image.setImage(img);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Image img = new Image(Main.class.getResource("assets/product.jpg").toExternalForm());
            image.setImage(img);
        }

        try {
            category.setText(categoryService.findById(product.getCategoryID()).getType());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



    }

    public void initialize() throws SQLException {

    }
}
