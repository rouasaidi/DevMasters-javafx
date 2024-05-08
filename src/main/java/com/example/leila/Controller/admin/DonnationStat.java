package com.example.leila.Controller.admin;

import com.example.leila.HelloApplication;
import com.example.leila.Services.DonationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class DonnationStat {
    @FXML PieChart piechart;
    private final DonationService donationService = new DonationService();

    @FXML
    public void initialize() {
        setPichart();
    }

    public void setPichart() {
        try {
            Map<String, Integer> statistics = donationService.getDonationStatisticsByCategory();
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
                pieChartData.add(new PieChart.Data(entry.getKey() + " : " + entry.getValue(), entry.getValue()));
            }
            piechart.setData(pieChartData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void godonate() throws IOException {
        Scene scenefer = piechart.getScene();
        Stage stagefer = (Stage) scenefer.getWindow();
        stagefer.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Admin/Dones.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }        }