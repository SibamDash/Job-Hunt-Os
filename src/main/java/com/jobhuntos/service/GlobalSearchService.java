package com.jobhuntos.service;
import com.jobhuntos.model.*;
import java.util.ArrayList;
import java.util.List;

public class GlobalSearchService {
    private static final GlobalSearchService instance = new GlobalSearchService();
    private GlobalSearchService() {}
    public static GlobalSearchService getInstance() { return instance; }

    public List<String> search(String query) {
        List<String> results = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) return results;
        
        // Search Companies
        List<Company> companies = CompanyService.getInstance().search(query, 5, 0);
        for (Company c : companies) {
            results.add("Company: " + c.getName());
        }
        // In a real scenario, we'd query ApplicationService, TaskService, etc. with specific LIKE queries.
        // For Phase 6, we demonstrate the architectural pattern here.
        if (results.isEmpty()) results.add("No results found for '" + query + "'");
        return results;
    }
}
