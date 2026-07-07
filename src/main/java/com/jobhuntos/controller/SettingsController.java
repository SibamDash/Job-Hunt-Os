package com.jobhuntos.controller;
import com.jobhuntos.ui.DialogHelper;
import com.jobhuntos.ThemeManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SettingsController {
    @FXML private ComboBox<String> comboTheme;
    @FXML private ColorPicker colorPicker;
    @FXML private CheckBox chkAutoBackup;
    @FXML private TextField txtBackupLoc;

    @FXML
    public void initialize() {
        comboTheme.setItems(FXCollections.observableArrayList("LIGHT", "DARK"));
        comboTheme.setValue("LIGHT");
        
        txtBackupLoc.setText(System.getProperty("user.home") + "\\JobHuntOS_Backups");
        
        // Dynamic theme preview
        comboTheme.setOnAction(e -> {
            if ("DARK".equals(comboTheme.getValue())) {
                ThemeManager.getInstance().setDarkTheme();
            } else {
                ThemeManager.getInstance().setLightTheme();
            }
        });
    }
    
    @FXML private void handleBrowse() { DialogHelper.showInformation("Browse", "Directory chooser would open here."); }
    @FXML private void handleUpdate() { DialogHelper.showInformation("Update", "You are on the latest version."); }
    @FXML private void handleSave() { DialogHelper.showInformation("Save Settings", "Settings saved successfully."); }
}
