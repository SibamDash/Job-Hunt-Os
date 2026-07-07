package com.jobhuntos.controller;
import com.jobhuntos.ui.DialogHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RecruitersController {
    @FXML private TableView<String[]> tableView;
    @FXML private TableColumn<String[], String> colName;
    @FXML private TableColumn<String[], String> colCompany;
    @FXML private TableColumn<String[], String> colStatus;
    
    @FXML private Label lblName;
    @FXML private Label lblCompany;
    @FXML private Label lblEmail;
    @FXML private Label lblLinkedIn;
    @FXML private Label lblFollowUp;

    @FXML
    public void initialize() {
        colName.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[0]));
        colCompany.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[1]));
        colStatus.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[2]));
        
        ObservableList<String[]> data = FXCollections.observableArrayList(
            new String[]{"Jane Doe", "Google", "CONNECTED", "jane@google.com", "linkedin.com/in/jane", "2026-07-10"},
            new String[]{"John Smith", "Meta", "PENDING", "john@meta.com", "linkedin.com/in/john", "2026-07-15"}
        );
        tableView.setItems(data);
        
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                lblName.setText(newSel[0]);
                lblCompany.setText(newSel[1]);
                lblEmail.setText(newSel[3]);
                lblLinkedIn.setText(newSel[4]);
                lblFollowUp.setText(newSel[5]);
            }
        });
    }
    
    @FXML private void handleAdd() { DialogHelper.showInput("Add Recruiter", "New Recruiter", "Enter recruiter name:"); }
}
