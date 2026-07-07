package com.jobhuntos;

/**
 * Launcher class to bypass Java Modules restrictions when running a JavaFX fat jar.
 * This is the entry point defined in pom.xml.
 */
public class Launcher {
    public static void main(String[] args) {
        Main.main(args);
    }
}
