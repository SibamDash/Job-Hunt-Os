package com.jobhuntos.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Handles database schema versions and migrations.
 */
public class DatabaseMigration {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigration.class);
    
    // The current schema version required by the application code
    private static final int TARGET_SCHEMA_VERSION = 1;

    public static void migrate() {
        logger.info("Checking database migrations...");
        DatabaseManager dbManager = DatabaseManager.getInstance();
        
        try {
            Connection conn = dbManager.getConnection();
            ensureSchemaVersionTable(conn);
            
            int currentVersion = getCurrentVersion(conn);
            logger.info("Current schema version: {}, Target version: {}", currentVersion, TARGET_SCHEMA_VERSION);
            
            if (currentVersion < TARGET_SCHEMA_VERSION) {
                // Execute incremental migrations here when needed.
                // For example:
                // if (currentVersion == 1) { migrateToVersion2(conn); currentVersion = 2; }
                
                // For Phase 2, initial setup is Version 1.
                // The DatabaseInitializer will create everything if tables don't exist.
                
                updateVersion(conn, TARGET_SCHEMA_VERSION);
                logger.info("Database migrated to version {}", TARGET_SCHEMA_VERSION);
            }
        } catch (SQLException e) {
            logger.error("Migration failed.", e);
        }
    }

    private static void ensureSchemaVersionTable(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS SchemaVersion (" +
                    "id INTEGER PRIMARY KEY CHECK (id = 1), " +
                    "version INTEGER NOT NULL)");
        }
    }

    private static int getCurrentVersion(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT version FROM SchemaVersion WHERE id = 1")) {
            if (rs.next()) {
                return rs.getInt("version");
            } else {
                return 0;
            }
        }
    }

    private static void updateVersion(Connection conn, int version) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT OR REPLACE INTO SchemaVersion (id, version) VALUES (1, " + version + ")");
        }
    }
}
