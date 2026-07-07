package com.jobhuntos.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to manage navigation between views in the main application area.
 */
public class NavigationManager {
    private static final Logger logger = LoggerFactory.getLogger(NavigationManager.class);
    
    private static BorderPane mainLayout;
    private static final Map<String, Parent> viewCache = new HashMap<>();

    public static void setMainLayout(BorderPane layout) {
        mainLayout = layout;
    }

    public static void navigateTo(String fxmlPath) {
        if (mainLayout == null) {
            logger.error("Main layout is not set in NavigationManager.");
            return;
        }

        try {
            Parent view = viewCache.get(fxmlPath);
            if (view == null) {
                logger.info("Loading view: {}", fxmlPath);
                FXMLLoader loader = new FXMLLoader(NavigationManager.class.getResource(fxmlPath));
                view = loader.load();
                viewCache.put(fxmlPath, view);
            } else {
                logger.debug("Loading view from cache: {}", fxmlPath);
            }
            
            mainLayout.setCenter(view);
            
        } catch (IOException e) {
            logger.error("Failed to load view: {}", fxmlPath, e);
        }
    }
}
