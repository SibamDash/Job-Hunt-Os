package com.jobhuntos.controller;
import com.jobhuntos.ui.DialogHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CompaniesController {
    @FXML private TableView<String[]> tableView;
    @FXML private TableColumn<String[], String> colName;
    @FXML private TableColumn<String[], String> colTech;
    @FXML private Label lblDetailName;
    @FXML private Label lblWebsite;
    @FXML private Label lblTech;
    @FXML private TextArea txtNotes;

    @FXML
    public void initialize() {
        colName.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[0]));
        colTech.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[1]));
        
        ObservableList<String[]> data = FXCollections.observableArrayList(
            new String[]{"Google", "Java, C++, Python", "https://google.com", "Great perks"},
            new String[]{"OpenAI", "Python, React", "https://openai.com", "AI focus"}
        );
        tableView.setItems(data);
        
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                lblDetailName.setText(newSel[0]);
                lblTech.setText(newSel[1]);
                lblWebsite.setText(newSel[2]);
                txtNotes.setText(newSel[3]);
            }
        });
    }
    
    @FXML private void handleAdd() { DialogHelper.showInput("Add Company", "New Company", "Enter company name:"); }
}
