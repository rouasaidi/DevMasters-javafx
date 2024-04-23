package tn.esprit.devmasters.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.devmasters.Main;
import tn.esprit.devmasters.models.Category;
import tn.esprit.devmasters.services.CategoryService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CategoryController {
    CategoryService categoryService = new CategoryService();
    Category selectedCategory = null;

    // TABLE
    @FXML
    private TableView<Category> categoriesTable;

    // INPUTS
    @FXML
    private TextField input_type;
    @FXML
    private TextField input_image;

    // BUTTONS
    @FXML
    private Button submit_btn;

    // COLUMNS
    @FXML
    private TableColumn<Category, String> image_col;
    @FXML
    private TableColumn<Category, String> type_col;
    @FXML
    private TableColumn<Category, Integer> user_col;
    @FXML
    private TableColumn<Category, Integer> id_col;

    @FXML
    private Label error_type, error_image;


    public void initialize() throws SQLException {
        loadCategories();
    }

    @FXML
    void goToProduct() throws IOException {
        Stage stage = (Stage) submit_btn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/Product.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root, 1200, 700));
    }

    @FXML
    void onDelete(ActionEvent event) {
        selectedCategory = categoriesTable.getSelectionModel().getSelectedItem();
        try {
            categoryService.delete(selectedCategory);
            loadCategories();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void onEdit() {
        selectedCategory = categoriesTable.getSelectionModel().getSelectedItem();
        input_image.setText(selectedCategory.getImage());
        input_type.setText(selectedCategory.getType());
        submit_btn.setText("Modifier");
    }

    @FXML
    void onSubmit() {
        String status = submit_btn.getText();

        if (checkCategory())
            if (status.equals("Modifier")) {
                updateCategory();
            } else {
                addCategory();
            }

        submit_btn.setText("Ajouter");
        input_type.clear();
        input_image.clear();
    }

    public void loadCategories() throws SQLException {
        List<Category> categories = categoryService.findAll();
        categoriesTable.getItems().setAll(categories);

        image_col.setCellValueFactory(new PropertyValueFactory<>("image"));
        type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        user_col.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    public void addCategory() {
        String type = input_type.getText();
        String image = input_image.getText();

        Category category = new Category(type, image, 1);
        try {
            categoryService.add(category);
            loadCategories();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCategory() {
        Category category = new Category(selectedCategory.getId(), input_type.getText(), input_image.getText(), 1);
        try {
            categoryService.update(category);
            loadCategories();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean checkCategory() {
        boolean status = true;
        if (input_type.getText().isEmpty()) {
            error_type.setText("Le champ type est obligatoire");
            status = false;
        } else {
            error_type.setText("");
        }

        if (input_image.getText().isEmpty()) {
            error_image.setText("Le champ image est obligatoire");
            status = false;
        } else {
            error_image.setText("");
        }

        return status;
    }
}
