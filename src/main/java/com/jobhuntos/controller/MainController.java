package com.jobhuntos.controller;

import com.jobhuntos.ui.NavigationManager;
import com.jobhuntos.ui.ThemeManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @FXML private BorderPane mainLayout;
    @FXML private VBox sidebar;
    @FXML private TextField searchField;
    @FXML private Button themeToggleButton;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        logger.info("Initializing MainController");
        NavigationManager.setMainLayout(mainLayout);
        
        // Load default view
        showDashboard();
    }

    @FXML
    private void showDashboard() {
        NavigationManager.navigateTo("/fxml/DashboardView.fxml");
        statusLabel.setText("Ready - Dashboard");
    }

    @FXML
    private void showApplications() {
        NavigationManager.navigateTo("/fxml/ApplicationsView.fxml");
        statusLabel.setText("Ready - Applications");
    }

    @FXML
    private void showCompanies() {
        NavigationManager.navigateTo("/fxml/CompaniesView.fxml");
        statusLabel.setText("Ready - Companies");
    }

    @FXML
    private void showRecruiters() {
        NavigationManager.navigateTo("/fxml/RecruitersView.fxml");
        statusLabel.setText("Ready - Recruiters");
    }

    @FXML
    private void showInterviews() {
        NavigationManager.navigateTo("/fxml/InterviewsView.fxml");
        statusLabel.setText("Ready - Interviews");
    }

    @FXML
    private void showResumes() {
        NavigationManager.navigateTo("/fxml/ResumesView.fxml");
        statusLabel.setText("Ready - Resumes");
    }

    @FXML
    private void showAnalytics() {
        NavigationManager.navigateTo("/fxml/AnalyticsView.fxml");
        statusLabel.setText("Ready - Analytics");
    }

    @FXML
    private void showDocuments() {
        NavigationManager.navigateTo("/fxml/DocumentsView.fxml");
        statusLabel.setText("Ready - Documents");
    }

    @FXML
    private void showTasks() {
        NavigationManager.navigateTo("/fxml/TasksView.fxml");
        statusLabel.setText("Ready - Tasks");
    }

    @FXML
    private void showSettings() {
        NavigationManager.navigateTo("/fxml/SettingsView.fxml");
        statusLabel.setText("Ready - Settings");
    }

    @FXML
    private void toggleTheme() {
        ThemeManager.toggleTheme();
        if (ThemeManager.isDarkMode()) {
            themeToggleButton.setText("☀ Light Mode");
        } else {
            themeToggleButton.setText("🌙 Dark Mode");
        }
    }
}
