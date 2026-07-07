package com.jobhuntos.ui;

import com.jobhuntos.Main;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to manage light and dark themes.
 */
public class ThemeManager {
    private static final Logger logger = LoggerFactory.getLogger(ThemeManager.class);
    
    private static final String LIGHT_THEME = "/styles/light.css";
    private static final String DARK_THEME = "/styles/dark.css";
    
    private static boolean isDarkMode = false;

    public static void toggleTheme() {
        Scene scene = Main.getMainScene();
        if (scene == null) return;
        
        isDarkMode = !isDarkMode;
        
        // Remove both to be safe
        scene.getStylesheets().remove(ThemeManager.class.getResource(LIGHT_THEME).toExternalForm());
        scene.getStylesheets().remove(ThemeManager.class.getResource(DARK_THEME).toExternalForm());
        
        // Add the new one
        String newTheme = isDarkMode ? DARK_THEME : LIGHT_THEME;
        scene.getStylesheets().add(ThemeManager.class.getResource(newTheme).toExternalForm());
        
        logger.info("Theme switched to: {}", isDarkMode ? "Dark" : "Light");
    }
    
    public static boolean isDarkMode() {
        return isDarkMode;
    }
}
