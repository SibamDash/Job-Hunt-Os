package com.jobhuntos.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Window;
import java.util.Optional;

public class DialogHelper {
    
    private static void applyTheme(Alert alert) {
        Window window = alert.getDialogPane().getScene().getWindow();
        if (com.jobhuntos.Main.getMainScene() != null) {
            alert.getDialogPane().getStylesheets().addAll(com.jobhuntos.Main.getMainScene().getStylesheets());
            alert.getDialogPane().getStyleClass().add("card");
        }
    }

    public static void showInformation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        applyTheme(alert);
        alert.showAndWait();
    }

    public static void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("An Error Occurred");
        alert.setContentText(content);
        applyTheme(alert);
        alert.showAndWait();
    }

    public static void showWarning(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        applyTheme(alert);
        alert.showAndWait();
    }

    public static boolean showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        applyTheme(alert);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public static Optional<String> showInput(String title, String header, String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(prompt);
        if (com.jobhuntos.Main.getMainScene() != null) {
            dialog.getDialogPane().getStylesheets().addAll(com.jobhuntos.Main.getMainScene().getStylesheets());
            dialog.getDialogPane().getStyleClass().add("card");
        }
        return dialog.showAndWait();
    }
}
