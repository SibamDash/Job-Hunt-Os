package com.jobhuntos.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class DashboardController {
    @FXML private BarChart<String, Number> barChart;
    @FXML private PieChart pieChart;
    
    @FXML
    public void initialize() {
        loadBarChart();
        loadPieChart();
    }
    
    private void loadBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Applications");
        series.getData().add(new XYChart.Data<>("Week 1", 5));
        series.getData().add(new XYChart.Data<>("Week 2", 12));
        series.getData().add(new XYChart.Data<>("Week 3", 8));
        series.getData().add(new XYChart.Data<>("Week 4", 15));
        barChart.getData().add(series);
    }
    
    private void loadPieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Applied", 40),
            new PieChart.Data("Interviewing", 20),
            new PieChart.Data("Rejected", 30),
            new PieChart.Data("Offer", 10)
        );
        pieChart.setData(pieChartData);
    }
}
