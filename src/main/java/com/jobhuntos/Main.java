package com.jobhuntos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Main application class for Job Hunting OS.
 */
public class Main extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        try {
            logger.info("Starting Job Hunting OS...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = loader.load();

            mainScene = new Scene(root, 1024, 768);
            
            // Set base styles and default theme
            mainScene.getStylesheets().add(getClass().getResource("/styles/base.css").toExternalForm());
            mainScene.getStylesheets().add(getClass().getResource("/styles/light.css").toExternalForm());

            primaryStage.setTitle("Job Hunting OS");
            
            // Set app icon (ignore exception if not found yet)
            try {
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/app_icon.png")));
            } catch (Exception e) {
                logger.warn("App icon not found.");
            }

            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            
            primaryStage.setScene(mainScene);
            primaryStage.show();
            
        } catch (IOException e) {
            logger.error("Failed to load main view.", e);
        }
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
