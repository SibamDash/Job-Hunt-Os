package com.jobhuntos.controller;
import com.jobhuntos.ui.DialogHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TasksController {
    @FXML private TableView<String[]> tableView;
    @FXML private TableColumn<String[], String> colTitle;
    @FXML private TableColumn<String[], String> colDue;
    @FXML private TableColumn<String[], String> colPriority;
    @FXML private TableColumn<String[], String> colStatus;

    @FXML
    public void initialize() {
        colTitle.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[0]));
        colDue.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[1]));
        colPriority.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[2]));
        colStatus.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[3]));
        
        ObservableList<String[]> data = FXCollections.observableArrayList(
            new String[]{"Update Resume", "2026-07-10", "HIGH", "TODO"},
            new String[]{"Prepare for Google", "2026-07-14", "HIGH", "IN_PROGRESS"},
            new String[]{"Apply to Meta", "2026-07-01", "NORMAL", "DONE"}
        );
        tableView.setItems(data);
    }
    
    @FXML private void handleAdd() { DialogHelper.showInput("Add Task", "New Task", "Enter task title:"); }
}
