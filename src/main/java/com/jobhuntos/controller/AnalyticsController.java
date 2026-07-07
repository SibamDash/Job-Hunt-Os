package com.jobhuntos.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;

public class AnalyticsController {
    @FXML private PieChart pieChart;
    @FXML private LineChart<String, Number> lineChart;
    @FXML private BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        // Pie Chart
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
            new PieChart.Data("Offers", 5),
            new PieChart.Data("Rejections", 45),
            new PieChart.Data("No Response", 50)
        );
        pieChart.setData(pieData);

        // Line Chart
        XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
        lineSeries.setName("2026");
        lineSeries.getData().add(new XYChart.Data<>("Jan", 10));
        lineSeries.getData().add(new XYChart.Data<>("Feb", 25));
        lineSeries.getData().add(new XYChart.Data<>("Mar", 15));
        lineSeries.getData().add(new XYChart.Data<>("Apr", 30));
        lineChart.getData().add(lineSeries);

        // Bar Chart
        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        barSeries.setName("Count");
        barSeries.getData().add(new XYChart.Data<>("HR Screen", 12));
        barSeries.getData().add(new XYChart.Data<>("Technical", 8));
        barSeries.getData().add(new XYChart.Data<>("Behavioral", 5));
        barSeries.getData().add(new XYChart.Data<>("Take Home", 3));
        barChart.getData().add(barSeries);
    }
}
