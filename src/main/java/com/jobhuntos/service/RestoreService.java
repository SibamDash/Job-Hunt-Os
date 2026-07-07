package com.jobhuntos.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RestoreService {
    private static final Logger logger = LoggerFactory.getLogger(RestoreService.class);
    private static final RestoreService instance = new RestoreService();
    private RestoreService() {}
    public static RestoreService getInstance() { return instance; }

    public void restoreBackup(File zipFile) throws Exception {
        logger.info("Restoring backup from: {}", zipFile.getAbsolutePath());
        
        // Disconnect DB in real scenario before overwrite
        // DatabaseManager.getInstance().close();
        
        try (FileInputStream fis = new FileInputStream(zipFile);
             ZipInputStream zis = new ZipInputStream(fis)) {
             
            ZipEntry entry = zis.getNextEntry();
            while(entry != null) {
                if (entry.getName().equals("jobhuntingos.db")) {
                    Path target = Paths.get("data", "jobhuntingos.db");
                    Files.copy(zis, target, StandardCopyOption.REPLACE_EXISTING);
                    logger.info("Database file restored.");
                }
                entry = zis.getNextEntry();
            }
        }
    }
}
