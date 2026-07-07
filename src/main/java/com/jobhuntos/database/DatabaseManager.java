package com.jobhuntos.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Singleton manager for SQLite JDBC database connection.
 */
public class DatabaseManager {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
    private static DatabaseManager instance;
    private Connection connection;

    private static final String DATA_DIR = "data";
    private static final String DB_NAME = "jobhuntingos.db";
    private static final String URL = "jdbc:sqlite:" + DATA_DIR + "/" + DB_NAME;

    private DatabaseManager() {
        ensureDataDirectoryExists();
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void ensureDataDirectoryExists() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                logger.info("Created database directory: {}", DATA_DIR);
            } else {
                logger.error("Failed to create database directory: {}", DATA_DIR);
            }
        }
    }

    /**
     * Retrieves the active database connection. Opens it if closed.
     */
    public Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
                logger.info("Database connection opened.");
                enableForeignKeys();
            }
        } catch (SQLException e) {
            handleSqlException(e);
            throw e;
        }
        return connection;
    }

    private void enableForeignKeys() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
            logger.debug("SQLite foreign keys enabled.");
        }
    }

    /**
     * Closes the active database connection.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    logger.info("Database connection closed.");
                }
            } catch (SQLException e) {
                logger.error("Error closing database connection", e);
            }
        }
    }

    // Transaction Management
    public void beginTransaction() throws SQLException {
        getConnection().setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        Connection conn = getConnection();
        if (!conn.getAutoCommit()) {
            conn.commit();
            conn.setAutoCommit(true);
        }
    }

    public void rollbackTransaction() {
        try {
            Connection conn = getConnection();
            if (!conn.getAutoCommit()) {
                conn.rollback();
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logger.error("Error rolling back transaction", e);
        }
    }

    private void handleSqlException(SQLException e) {
        String state = e.getSQLState();
        int errorCode = e.getErrorCode();
        
        // SQLite error codes
        if (errorCode == 5) { // SQLITE_BUSY
            logger.error("Database is locked. Another process may be using it.");
        } else if (errorCode == 11) { // SQLITE_CORRUPT
            logger.error("Database file is corrupted.");
        } else if (errorCode == 14) { // SQLITE_CANTOPEN
            logger.error("Cannot open database file. Check file permissions.");
        } else {
            logger.error("Database connection failure. Error Code: {}, State: {}", errorCode, state, e);
        }
    }
}
