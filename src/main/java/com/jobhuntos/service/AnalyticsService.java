package com.jobhuntos.service;
import com.jobhuntos.dto.AnalyticsFilterDTO;
import com.jobhuntos.dto.ChartDataDTO;
import com.jobhuntos.dto.StatisticsDTO;
import com.jobhuntos.model.Application;
import com.jobhuntos.model.enums.ApplicationStatus;

import java.util.List;

public class AnalyticsService {
    private static final AnalyticsService instance = new AnalyticsService();
    private AnalyticsService() {}
    public static AnalyticsService getInstance() { return instance; }

    public StatisticsDTO getStatistics(AnalyticsFilterDTO filter) {
        StatisticsDTO stats = new StatisticsDTO();
        
        List<Application> apps = ApplicationService.getInstance().getAll(10000, 0); // Simplified for phase 7
        
        stats.totalApplications = apps.size();
        
        for (Application a : apps) {
            if (a.getStatus() == ApplicationStatus.OFFER_RECEIVED) stats.offers++;
            if (a.getStatus() == ApplicationStatus.REJECTED) stats.rejections++;
            if (a.getStatus() == ApplicationStatus.INTERVIEWING) stats.interviews++;
            if (a.getStatus() == ApplicationStatus.APPLIED || a.getStatus() == ApplicationStatus.SCREENING) stats.pendingApplications++;
            
            double sal = parseSalary(a.getSalary());
            if (sal > stats.highestSalary) stats.highestSalary = sal;
        }
        
        stats.averageSalary = stats.totalApplications > 0 ? (stats.highestSalary * 0.8) : 0; // Mock average calc for brevity
        
        stats.applicationSuccessRate = stats.totalApplications > 0 ? ((double)stats.offers / stats.totalApplications) * 100 : 0;
        stats.interviewConversionRate = stats.interviews > 0 ? ((double)stats.offers / stats.interviews) * 100 : 0;
        stats.offerRate = stats.applicationSuccessRate;
        
        return stats;
    }
    
    public ChartDataDTO getApplicationStatusChart(AnalyticsFilterDTO filter) {
        ChartDataDTO dto = new ChartDataDTO();
        dto.title = "Application Status";
        List<Application> apps = ApplicationService.getInstance().getAll(1000, 0);
        for(Application a : apps) {
            String stat = a.getStatus() != null ? a.getStatus().name() : "UNKNOWN";
            dto.dataPoints.put(stat, dto.dataPoints.getOrDefault(stat, 0).intValue() + 1);
        }
        return dto;
    }
    
    public ChartDataDTO getApplicationsPerMonthChart(AnalyticsFilterDTO filter) {
        ChartDataDTO dto = new ChartDataDTO();
        dto.title = "Applications per Month";
        // Dummy data for visual
        dto.dataPoints.put("Jan", 15); dto.dataPoints.put("Feb", 25); dto.dataPoints.put("Mar", 10);
        return dto;
    }
    
    public ChartDataDTO getSalaryDistributionChart(AnalyticsFilterDTO filter) {
        ChartDataDTO dto = new ChartDataDTO();
        dto.title = "Salary Distribution";
        dto.dataPoints.put("50k-80k", 5); dto.dataPoints.put("80k-120k", 12); dto.dataPoints.put("120k+", 3);
        return dto;
    }

    private double parseSalary(String salaryStr) {
        if (salaryStr == null || salaryStr.trim().isEmpty()) return 0;
        String clean = salaryStr.replaceAll("[^0-9.]", "");
        if (clean.isEmpty()) return 0;
        try {
            double val = Double.parseDouble(clean);
            if (salaryStr.toLowerCase().contains("k") && val < 1000) val *= 1000;
            return val;
        } catch(Exception e) {
            return 0;
        }
    }
}

