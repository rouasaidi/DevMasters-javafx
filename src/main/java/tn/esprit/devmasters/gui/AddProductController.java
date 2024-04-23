package tn.esprit.devmasters.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.devmasters.Main;
import tn.esprit.devmasters.models.Category;
import tn.esprit.devmasters.models.Product;
import tn.esprit.devmasters.services.CategoryService;
import tn.esprit.devmasters.services.ProductService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddProductController {
    ProductService productService = new ProductService();
    CategoryService categoryService = new CategoryService();
    @FXML
    private Label error_category;

    @FXML
    private Label error_description;

    @FXML
    private Label error_image;

    @FXML
    private Label error_name;

    @FXML
    private Label error_price;

    @FXML
    private Label error_qte;

    @FXML
    private Label error_total;

    @FXML
    private TextField input_description;

    @FXML
    private TextField input_image;

    @FXML
    private TextField input_name;

    @FXML
    private TextField input_price;

    @FXML
    private TextField input_qte;

    @FXML
    private TextField input_total;

    @FXML
    private ComboBox<Integer> select_category;

    @FXML
    private Button submit_btn;

    public void initialize() throws SQLException {
        List<Category> categories = categoryService.findAll();
        for (Category category : categories) {
            select_category.getItems().add(category.getId());
        }
    }

    @FXML
    void goToProduct() throws IOException {
        Stage stage = (Stage) submit_btn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/ProductFront.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root, 1200, 700));
    }

    @FXML
    void onSubmit(ActionEvent event) throws SQLException {
        try {

            if (checkProduct()) {
                String name = input_name.getText();
                String description = input_description.getText();
                String image = input_image.getText();
                String price = input_price.getText();
                String qte = input_qte.getText();
                String total = input_total.getText();
                int category = Integer.parseInt(String.valueOf(select_category.getValue()));

                Product product = new Product(name, description, (Integer.parseInt(qte)), Float.parseFloat(price), Integer.parseInt(total), image, 1, category);
                productService.add(product);
                try {
                    goToProduct();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Boolean checkProduct() {
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

}
