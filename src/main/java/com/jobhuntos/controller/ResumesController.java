package com.jobhuntos.controller;
import com.jobhuntos.ui.DialogHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ResumesController {
    @FXML private TableView<String[]> tableView;
    @FXML private TableColumn<String[], String> colName;
    @FXML private TableColumn<String[], String> colType;
    @FXML private Label lblPreviewName;

    @FXML
    public void initialize() {
        colName.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[0]));
        colType.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[1]));
        
        ObservableList<String[]> data = FXCollections.observableArrayList(
            new String[]{"Software_Engineer_Resume_2026.pdf", "TECH"},
            new String[]{"Data_Scientist_Resume.pdf", "DATA"}
        );
        tableView.setItems(data);
        
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                lblPreviewName.setText("Previewing: " + newSel[0]);
            }
        });
    }
    
    @FXML private void handleUpload() { DialogHelper.showInformation("Upload", "File picker would open here."); }
    @FXML private void handleReplace() { DialogHelper.showInformation("Replace", "File picker would open here."); }
}
