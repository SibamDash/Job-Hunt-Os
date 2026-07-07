package com.jobhuntos.dto;
import java.util.LinkedHashMap;
import java.util.Map;
public class ChartDataDTO {
    public String title;
    public Map<String, Number> dataPoints = new LinkedHashMap<>();
}
