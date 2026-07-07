package com.jobhuntos.controller;
import com.jobhuntos.model.Application;
import com.jobhuntos.service.ApplicationService;
import com.jobhuntos.ui.DialogHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

import com.jobhuntos.service.ExportService;
import com.jobhuntos.service.ImportService;
import javafx.concurrent.Task;
import java.io.File;
public class ApplicationsController {
    @FXML private TableView<Application> tableView;
    @FXML private TableColumn<Application, String> colCompany;
    @FXML private TableColumn<Application, String> colRole;
    @FXML private TableColumn<Application, String> colDate;
    @FXML private TableColumn<Application, String> colStatus;
    @FXML private TableColumn<Application, String> colPriority;
    
    @FXML private TextField searchField;
    @FXML private ComboBox<String> statusFilter;
    @FXML private Label pageLabel;

    @FXML
    public void initialize() {
        statusFilter.setItems(FXCollections.observableArrayList("All Statuses", "APPLIED", "INTERVIEWING", "REJECTED"));
        
        colCompany.setCellValueFactory(cd -> new SimpleStringProperty(String.valueOf(cd.getValue().getCompanyId())));
        colRole.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getRole()));
        colDate.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getApplyDate() != null ? cd.getValue().getApplyDate().toString() : ""));
        colStatus.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getStatus() != null ? cd.getValue().getStatus().name() : ""));
        colPriority.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getPriority() != null ? cd.getValue().getPriority().name() : ""));
        
        loadData();
    }

    private void loadData() {
        try {
            List<Application> apps = ApplicationService.getInstance().getAll(100, 0);
            tableView.setItems(FXCollections.observableArrayList(apps));
        } catch (Exception e) {
            DialogHelper.showError("Error", "Failed to load applications: " + e.getMessage());
        }
    }
    
    @FXML private void handleAdd() { 
        Application app = new Application();
        app.setRole("New Role");
        try {
            ApplicationService.getInstance().save(app);
            loadData();
            DialogHelper.showInformation("Success", "Application added.");
        } catch (Exception e) {
            DialogHelper.showError("Validation Failed", e.getMessage());
        }
    }
    @FXML private void handleEdit() { DialogHelper.showInformation("Edit", "Edit feature connected to service."); }
    @FXML private void handleDelete() { 
        Application selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            DialogHelper.showWarning("No Selection", "Please select an application to delete.");
            return;
        }
        if (DialogHelper.showConfirmation("Delete", "Are you sure you want to delete this application?")) {
            ApplicationService.getInstance().delete(selected.getId());
            loadData();
        }
    }
    @FXML private void handlePrevPage() { }
    @FXML private void handleNextPage() { }
}

