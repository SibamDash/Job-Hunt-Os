package com.jobhuntos.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BackupService {
    private static final Logger logger = LoggerFactory.getLogger(BackupService.class);
    private static final BackupService instance = new BackupService();
    private BackupService() {}
    public static BackupService getInstance() { return instance; }

    public File createBackup(String targetDir) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
        String filename = "JobHuntingOS_" + dtf.format(LocalDateTime.now()) + ".zip";
        File zipFile = new File(targetDir, filename);
        
        Path dbPath = Paths.get("data", "jobhuntingos.db");
        if (!Files.exists(dbPath)) {
            throw new Exception("Database file not found. Nothing to backup.");
        }
        
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
             
            ZipEntry entry = new ZipEntry("jobhuntingos.db");
            zos.putNextEntry(entry);
            Files.copy(dbPath, zos);
            zos.closeEntry();
            
            logger.info("Backup created at: {}", zipFile.getAbsolutePath());
        }
        return zipFile;
    }
}
