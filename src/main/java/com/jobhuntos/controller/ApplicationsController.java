package com.jobhuntos.controller;
import com.jobhuntos.ui.DialogHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ApplicationsController {
    @FXML private TableView<String[]> tableView;
    @FXML private TableColumn<String[], String> colCompany;
    @FXML private TableColumn<String[], String> colRole;
    @FXML private TableColumn<String[], String> colDate;
    @FXML private TableColumn<String[], String> colStatus;
    @FXML private TableColumn<String[], String> colPriority;
    
    @FXML private TextField searchField;
    @FXML private ComboBox<String> statusFilter;
    @FXML private Label pageLabel;

    @FXML
    public void initialize() {
        statusFilter.setItems(FXCollections.observableArrayList("All Statuses", "APPLIED", "INTERVIEWING", "REJECTED"));
        
        colCompany.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        colRole.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        colDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
        colPriority.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4]));
        
        // Mock Data
        ObservableList<String[]> data = FXCollections.observableArrayList(
            new String[]{"Google", "Software Engineer", "2026-07-01", "INTERVIEWING", "HIGH"},
            new String[]{"Meta", "Backend Developer", "2026-07-03", "APPLIED", "MEDIUM"},
            new String[]{"Amazon", "Data Engineer", "2026-06-25", "REJECTED", "LOW"}
        );
        tableView.setItems(data);
    }
    
    @FXML private void handleAdd() { DialogHelper.showInformation("Add Application", "Open Application Form Dialog (UI Only)"); }
    @FXML private void handleEdit() { DialogHelper.showInformation("Edit", "Edit Selected Application"); }
    @FXML private void handleDelete() { DialogHelper.showConfirmation("Delete", "Are you sure you want to delete this application?"); }
    @FXML private void handlePrevPage() { }
    @FXML private void handleNextPage() { }
}
