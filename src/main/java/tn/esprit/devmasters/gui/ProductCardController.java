package tn.esprit.devmasters.gui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import tn.esprit.devmasters.Main;
import tn.esprit.devmasters.models.Product;
import tn.esprit.devmasters.services.CategoryService;

import java.sql.SQLException;

public class ProductCardController {
    private final CategoryService categoryService = new CategoryService();

    @FXML
    private Text title, price, category;

    @FXML
    private ImageView image;


    public void setProduct(Product product) {
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
