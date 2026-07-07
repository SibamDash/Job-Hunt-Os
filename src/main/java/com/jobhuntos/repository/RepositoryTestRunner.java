package com.jobhuntos.repository;

import com.jobhuntos.model.Company;
import com.jobhuntos.database.DatabaseManager;
import com.jobhuntos.database.DatabaseInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

public class RepositoryTestRunner {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryTestRunner.class);

    public static void main(String[] args) {
        logger.info("Starting Repository Layer Tests...");
        try {
            // Ensure DB is initialized
            DatabaseManager.getInstance().getConnection();
            DatabaseInitializer.initialize();

            CompanyRepository companyRepo = new CompanyRepository();
            
            // Create
            Company c = new Company();
            c.setName("TestCorp " + System.currentTimeMillis());
            c.setWebsite("https://testcorp.example.com");
            c.setTechStack("Java, SQLite");
            c = companyRepo.save(c);
            logger.info("Saved company ID: {}", c.getId());

            // Read
            Optional<Company> fetched = companyRepo.findById(c.getId());
            fetched.ifPresent(comp -> logger.info("Fetched company: {}", comp.getName()));

            // Update
            c.setNotes("Updated notes");
            boolean updated = companyRepo.update(c);
            logger.info("Company updated: {}", updated);

            // Search
            List<Company> results = companyRepo.searchByTechStack("Java", 10, 0);
            logger.info("Found {} companies using Java", results.size());

            // Clean up
            boolean deleted = companyRepo.delete(c.getId());
            logger.info("Deleted company: {}", deleted);

            DatabaseManager.getInstance().closeConnection();
        } catch (Exception e) {
            logger.error("Test failed", e);
        }
    }
}
