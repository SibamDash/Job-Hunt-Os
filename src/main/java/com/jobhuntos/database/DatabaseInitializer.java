package com.jobhuntos.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Handles database schema creation and initial data population.
 */
public class DatabaseInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    public static void initialize() {
        logger.info("Initializing database schema...");
        DatabaseManager dbManager = DatabaseManager.getInstance();
        
        try {
            Connection conn = dbManager.getConnection();
            
            // Execute table creation statements
            createTables(conn);
            
            // Create indexes
            createIndexes(conn);
            
            // Insert default settings
            insertDefaultSettings(conn);
            
            logger.info("Database schema initialized successfully.");
        } catch (SQLException e) {
            logger.error("Failed to initialize database schema", e);
        }
    }

    private static void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            logger.debug("Creating tables...");

            // 1. Settings
            stmt.execute("CREATE TABLE IF NOT EXISTS Settings (" +
                    "key TEXT PRIMARY KEY, " +
                    "value TEXT NOT NULL)");

            // 2. Companies
            stmt.execute("CREATE TABLE IF NOT EXISTS Companies (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL UNIQUE, " +
                    "website TEXT, " +
                    "glassdoor TEXT, " +
                    "package TEXT, " +
                    "tech_stack TEXT, " +
                    "notes TEXT)");

            // 3. Resumes
            stmt.execute("CREATE TABLE IF NOT EXISTS Resumes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "path TEXT NOT NULL, " +
                    "type TEXT, " +
                    "created_at TEXT DEFAULT CURRENT_TIMESTAMP)");

            // 4. Applications
            stmt.execute("CREATE TABLE IF NOT EXISTS Applications (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "company_id INTEGER NOT NULL, " +
                    "role TEXT NOT NULL, " +
                    "salary TEXT, " +
                    "location TEXT, " +
                    "portal TEXT, " +
                    "apply_date TEXT, " +
                    "status TEXT NOT NULL, " +
                    "priority TEXT, " +
                    "deadline TEXT, " +
                    "resume_id INTEGER, " +
                    "notes TEXT, " +
                    "created_at TEXT DEFAULT CURRENT_TIMESTAMP, " +
                    "updated_at TEXT DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (company_id) REFERENCES Companies(id) ON DELETE RESTRICT, " +
                    "FOREIGN KEY (resume_id) REFERENCES Resumes(id) ON DELETE SET NULL)");

            // 5. Recruiters
            stmt.execute("CREATE TABLE IF NOT EXISTS Recruiters (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "company_id INTEGER NOT NULL, " +
                    "name TEXT NOT NULL, " +
                    "linkedin TEXT, " +
                    "email TEXT, " +
                    "phone TEXT, " +
                    "connection_status TEXT, " +
                    "last_contact TEXT, " +
                    "follow_up_date TEXT, " +
                    "notes TEXT, " +
                    "FOREIGN KEY (company_id) REFERENCES Companies(id) ON DELETE CASCADE)");

            // 6. Interviews
            stmt.execute("CREATE TABLE IF NOT EXISTS Interviews (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "application_id INTEGER NOT NULL, " +
                    "type TEXT, " +
                    "date TEXT, " +
                    "result TEXT, " +
                    "feedback TEXT, " +
                    "questions TEXT, " +
                    "mistakes TEXT, " +
                    "FOREIGN KEY (application_id) REFERENCES Applications(id) ON DELETE CASCADE)");

            // 7. Tasks
            stmt.execute("CREATE TABLE IF NOT EXISTS Tasks (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "application_id INTEGER, " +
                    "title TEXT NOT NULL, " +
                    "description TEXT, " +
                    "due_date TEXT, " +
                    "priority TEXT, " +
                    "completed INTEGER DEFAULT 0, " +
                    "FOREIGN KEY (application_id) REFERENCES Applications(id) ON DELETE CASCADE)");

            // 8. Documents
            stmt.execute("CREATE TABLE IF NOT EXISTS Documents (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "application_id INTEGER NOT NULL, " +
                    "name TEXT NOT NULL, " +
                    "type TEXT, " +
                    "path TEXT NOT NULL, " +
                    "FOREIGN KEY (application_id) REFERENCES Applications(id) ON DELETE CASCADE)");

            // 9. Skills
            stmt.execute("CREATE TABLE IF NOT EXISTS Skills (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL UNIQUE, " +
                    "progress INTEGER DEFAULT 0)");

            // 10. Notifications
            stmt.execute("CREATE TABLE IF NOT EXISTS Notifications (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT NOT NULL, " +
                    "message TEXT, " +
                    "date TEXT DEFAULT CURRENT_TIMESTAMP, " +
                    "read INTEGER DEFAULT 0)");

            // 11. ActivityLog
            stmt.execute("CREATE TABLE IF NOT EXISTS ActivityLog (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "entity TEXT NOT NULL, " +
                    "entity_id INTEGER NOT NULL, " +
                    "action TEXT NOT NULL, " +
                    "timestamp TEXT DEFAULT CURRENT_TIMESTAMP)");

            // 12. Tags
            stmt.execute("CREATE TABLE IF NOT EXISTS Tags (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL UNIQUE)");

            // 13. ApplicationTags (Many-to-Many mapping)
            stmt.execute("CREATE TABLE IF NOT EXISTS ApplicationTags (" +
                    "application_id INTEGER NOT NULL, " +
                    "tag_id INTEGER NOT NULL, " +
                    "PRIMARY KEY (application_id, tag_id), " +
                    "FOREIGN KEY (application_id) REFERENCES Applications(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (tag_id) REFERENCES Tags(id) ON DELETE CASCADE)");
        }
    }

    private static void createIndexes(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            logger.debug("Creating indexes...");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_company_name ON Companies(name)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_app_status ON Applications(status)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_app_date ON Applications(apply_date)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_interview_date ON Interviews(date)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_recruiter_email ON Recruiters(email)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_task_due_date ON Tasks(due_date)");
        }
    }

    private static void insertDefaultSettings(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            logger.debug("Inserting default settings...");
            stmt.execute("INSERT OR IGNORE INTO Settings (key, value) VALUES ('Theme', 'Light')");
            stmt.execute("INSERT OR IGNORE INTO Settings (key, value) VALUES ('Auto Backup', 'false')");
            stmt.execute("INSERT OR IGNORE INTO Settings (key, value) VALUES ('Accent Color', 'Blue')");
            stmt.execute("INSERT OR IGNORE INTO Settings (key, value) VALUES ('Backup Location', '')");
        }
    }
}
