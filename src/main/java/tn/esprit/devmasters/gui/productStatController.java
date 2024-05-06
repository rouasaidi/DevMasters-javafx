package tn.esprit.devmasters.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import tn.esprit.devmasters.models.Product;
import tn.esprit.devmasters.services.ProductService;

import java.sql.SQLException;
import java.util.List;

public class productStatController {

    private final ProductService productService = new ProductService();

    @FXML
    private PieChart productPieChart;

    @FXML
    void refreshPieChart() throws SQLException {
        // Retrieve all products from the database
        List<Product> products = productService.findAll();

        // Create an observable list of pie chart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Add data to the pie chart
        for (Product product : products) {
            String productName = product.getName();
            int quantity = product.getQuantity();
            // Create a label with both product name and quantity
            String label = String.format("%s (%d)", productName, quantity);
            pieChartData.add(new PieChart.Data(label, quantity));
        }

        // Set the data to the pie chart
        productPieChart.setData(pieChartData);
    }
}
