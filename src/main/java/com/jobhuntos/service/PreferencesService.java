package com.jobhuntos.service;

import java.util.prefs.Preferences;

public class PreferencesService {
    private static final Preferences prefs = Preferences.userNodeForPackage(PreferencesService.class);
    
    public static void saveWindowBounds(double width, double height, boolean maximized) {
        prefs.putDouble("window_width", width);
        prefs.putDouble("window_height", height);
        prefs.putBoolean("window_maximized", maximized);
    }
    
    public static double getWindowWidth() { return prefs.getDouble("window_width", 1280); }
    public static double getWindowHeight() { return prefs.getDouble("window_height", 800); }
    public static boolean isWindowMaximized() { return prefs.getBoolean("window_maximized", false); }
    
    public static void saveTheme(String theme) { prefs.put("theme", theme); }
    public static String getTheme() { return prefs.get("theme", "LIGHT"); }
}
