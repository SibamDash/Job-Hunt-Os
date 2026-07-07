package com.jobhuntos.controller;
import com.jobhuntos.dto.AnalyticsFilterDTO;
import com.jobhuntos.dto.StatisticsDTO;
import com.jobhuntos.event.EventBus;
import com.jobhuntos.service.AnalyticsService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;

public class AnalyticsController {
    @FXML private DatePicker dpStart;
    @FXML private DatePicker dpEnd;
    @FXML private TextField txtCompany;
    @FXML private ComboBox<String> comboStatus;
    
    @FXML private Label lblTotal;
    @FXML private Label lblSuccessRate;
    @FXML private Label lblAvgSalary;
    
    @FXML private PieChart pieChart;
    @FXML private LineChart<String, Number> lineChart;
    @FXML private BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        comboStatus.setItems(FXCollections.observableArrayList("All", "APPLIED", "INTERVIEWING", "OFFER", "REJECTED"));
        refreshData();
        
        EventBus.getInstance().subscribe(event -> {
            if ("DATA_CHANGED".equals(event)) {
                Platform.runLater(this::refreshData);
            }
        });
    }
    
    @FXML
    private void handleFilter() {
        refreshData();
    }
    
    private void refreshData() {
        AnalyticsFilterDTO filter = new AnalyticsFilterDTO();
        filter.startDate = dpStart.getValue();
        filter.endDate = dpEnd.getValue();
        filter.company = txtCompany.getText();
        filter.status = comboStatus.getValue();
        
        StatisticsDTO stats = AnalyticsService.getInstance().getStatistics(filter);
        lblTotal.setText(String.valueOf(stats.totalApplications));
        lblSuccessRate.setText(String.format("%.1f%%", stats.applicationSuccessRate));
        lblAvgSalary.setText(String.format("$%.0f", stats.averageSalary));
        
        loadCharts(filter);
    }
    
    private void loadCharts(AnalyticsFilterDTO filter) {
        // Pie Chart
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        AnalyticsService.getInstance().getApplicationStatusChart(filter).dataPoints.forEach(
            (k, v) -> pieData.add(new PieChart.Data(k, v.doubleValue()))
        );
        pieChart.setData(pieData);

        // Bar Chart (Salary)
        barChart.getData().clear();
        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        barSeries.setName("Count");
        AnalyticsService.getInstance().getSalaryDistributionChart(filter).dataPoints.forEach(
            (k, v) -> barSeries.getData().add(new XYChart.Data<>(k, v))
        );
        barChart.getData().add(barSeries);
        
        // Line Chart (Applications over time)
        lineChart.getData().clear();
        XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
        lineSeries.setName("Applications");
        AnalyticsService.getInstance().getApplicationsPerMonthChart(filter).dataPoints.forEach(
            (k, v) -> lineSeries.getData().add(new XYChart.Data<>(k, v))
        );
        lineChart.getData().add(lineSeries);
    }
}
