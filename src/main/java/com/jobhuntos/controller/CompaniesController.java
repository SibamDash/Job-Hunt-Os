package com.jobhuntos.controller;
import com.jobhuntos.model.Company;
import com.jobhuntos.service.CompanyService;
import com.jobhuntos.ui.DialogHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;
import java.util.Optional;

public class CompaniesController {
    @FXML private TableView<Company> tableView;
    @FXML private TableColumn<Company, String> colName;
    @FXML private TableColumn<Company, String> colTech;
    @FXML private Label lblDetailName;
    @FXML private Label lblWebsite;
    @FXML private Label lblTech;
    @FXML private TextArea txtNotes;
    @FXML private TextField searchField;

    @FXML
    public void initialize() {
        colName.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getName()));
        colTech.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getTechStack()));
        
        loadData();
        
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                lblDetailName.setText(newSel.getName());
                lblTech.setText(newSel.getTechStack());
                lblWebsite.setText(newSel.getWebsite());
                txtNotes.setText(newSel.getNotes());
            }
        });
        
        searchField.textProperty().addListener((obs, oldText, newText) -> {
            try {
                List<Company> results = CompanyService.getInstance().search(newText, 100, 0);
                tableView.setItems(FXCollections.observableArrayList(results));
            } catch(Exception e) {}
        });
    }
    
    private void loadData() {
        try {
            List<Company> list = CompanyService.getInstance().getAll(100, 0);
            tableView.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            DialogHelper.showError("Error", "Failed to load companies: " + e.getMessage());
        }
    }

    @FXML private void handleAdd() { 
        Optional<String> result = DialogHelper.showInput("Add Company", "New Company", "Enter company name:");
        result.ifPresent(name -> {
            try {
                Company c = new Company();
                c.setName(name);
                CompanyService.getInstance().save(c);
                loadData();
            } catch (Exception e) {
                DialogHelper.showError("Validation Failed", e.getMessage());
            }
        });
    }
}
