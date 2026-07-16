package com.ouremr.product.dto;

import java.util.List;
import java.util.Map;

public class PatientStatsDTO {
    private Long totalPatients;
    private List<Map<String, Object>> weeklyData;

    public Long getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(Long totalPatients) {
        this.totalPatients = totalPatients;
    }

    public List<Map<String, Object>> getWeeklyData() {
        return weeklyData;
    }

    public void setWeeklyData(List<Map<String, Object>> weeklyData) {
        this.weeklyData = weeklyData;
    }
}
