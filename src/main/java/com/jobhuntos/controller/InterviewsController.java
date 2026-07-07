package com.jobhuntos.controller;
import com.jobhuntos.ui.DialogHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class InterviewsController {
    @FXML private TableView<String[]> tableView;
    @FXML private TableColumn<String[], String> colCompany;
    @FXML private TableColumn<String[], String> colType;
    @FXML private TableColumn<String[], String> colDate;
    @FXML private TableColumn<String[], String> colResult;

    @FXML
    public void initialize() {
        colCompany.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[0]));
        colType.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[1]));
        colDate.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[2]));
        colResult.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[3]));
        
        ObservableList<String[]> data = FXCollections.observableArrayList(
            new String[]{"Google", "TECHNICAL", "2026-07-15 10:00", "PENDING"},
            new String[]{"Meta", "HR_SCREEN", "2026-07-10 14:00", "PASSED"}
        );
        tableView.setItems(data);
    }
    
    @FXML private void handleAdd() { DialogHelper.showInput("Add Interview", "New Interview", "Enter interview details:"); }
}
