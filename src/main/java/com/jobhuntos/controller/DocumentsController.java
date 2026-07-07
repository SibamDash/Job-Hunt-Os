package com.jobhuntos.controller;
import com.jobhuntos.ui.DialogHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DocumentsController {
    @FXML private TableView<String[]> tableView;
    @FXML private TableColumn<String[], String> colName;
    @FXML private TableColumn<String[], String> colType;
    @FXML private ComboBox<String> categoryFilter;
    @FXML private Label lblPreviewName;

    @FXML
    public void initialize() {
        categoryFilter.setItems(FXCollections.observableArrayList("All Categories", "COVER_LETTER", "OFFER_LETTER", "PORTFOLIO", "OTHER"));
        
        colName.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[0]));
        colType.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()[1]));
        
        ObservableList<String[]> data = FXCollections.observableArrayList(
            new String[]{"Google_Cover_Letter.pdf", "COVER_LETTER"},
            new String[]{"Meta_Offer.pdf", "OFFER_LETTER"}
        );
        tableView.setItems(data);
        
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                lblPreviewName.setText("Previewing: " + newSel[0]);
            }
        });
    }
    
    @FXML private void handleUpload() { DialogHelper.showInformation("Upload", "File picker would open here."); }
}
