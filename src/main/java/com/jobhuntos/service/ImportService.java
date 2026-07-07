package com.jobhuntos.service;
import com.jobhuntos.model.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class ImportService {
    private static final Logger logger = LoggerFactory.getLogger(ImportService.class);
    private static final ImportService instance = new ImportService();
    private ImportService() {}
    public static ImportService getInstance() { return instance; }

    public String importCSV(File file) throws Exception {
        logger.info("Importing CSV: {}", file.getAbsolutePath());
        List<String> lines = Files.readAllLines(file.toPath());
        int success = 0, skipped = 0;
        
        for (int i = 1; i < lines.size(); i++) { // Skip header
            String line = lines.get(i);
            if(line == null || line.trim().isEmpty()) continue;
            
            try {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    Application app = new Application();
                    app.setRole(parts[1]);
                    app.setCompanyId(1L); // Default fallback
                    ApplicationService.getInstance().save(app);
                    success++;
                } else {
                    skipped++;
                }
            } catch (Exception e) {
                logger.warn("Failed to import line {}: {}", i, e.getMessage());
                skipped++;
            }
        }
        return String.format("Import complete. Successfully imported: %d, Skipped invalid rows: %d", success, skipped);
    }
}
