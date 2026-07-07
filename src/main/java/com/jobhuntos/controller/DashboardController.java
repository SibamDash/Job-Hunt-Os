package com.jobhuntos.controller;
import com.jobhuntos.dto.StatisticsDTO;
import com.jobhuntos.event.EventBus;
import com.jobhuntos.service.AnalyticsService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class DashboardController {
    @FXML private BarChart<String, Number> barChart;
    @FXML private PieChart pieChart;
    @FXML private Label lblActiveApps;
    @FXML private Label lblInterviews;
    @FXML private Label lblOffers;
    @FXML private Label lblRejections;
    
    @FXML
    public void initialize() {
        refreshData();
        EventBus.getInstance().subscribe(event -> {
            if ("DATA_CHANGED".equals(event)) {
                Platform.runLater(this::refreshData);
            }
        });
    }
    
    private void refreshData() {
        StatisticsDTO stats = AnalyticsService.getInstance().getStatistics(null);
        if(lblActiveApps != null) lblActiveApps.setText(String.valueOf(stats.pendingApplications));
        if(lblInterviews != null) lblInterviews.setText(String.valueOf(stats.interviews));
        if(lblOffers != null) lblOffers.setText(String.valueOf(stats.offers));
        if(lblRejections != null) lblRejections.setText(String.valueOf(stats.rejections));
        
        loadCharts();
    }
    
    private void loadCharts() {
        if(barChart != null) {
            barChart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Applications per Month");
            AnalyticsService.getInstance().getApplicationsPerMonthChart(null).dataPoints.forEach(
                (k, v) -> series.getData().add(new XYChart.Data<>(k, v))
            );
            barChart.getData().add(series);
        }
        
        if(pieChart != null) {
            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
            AnalyticsService.getInstance().getApplicationStatusChart(null).dataPoints.forEach(
                (k, v) -> pieData.add(new PieChart.Data(k, v.doubleValue()))
            );
            pieChart.setData(pieData);
        }
    }
}
