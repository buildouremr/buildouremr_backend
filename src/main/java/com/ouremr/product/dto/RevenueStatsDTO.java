package com.ouremr.product.dto;

import java.util.List;
import java.util.Map;

public class RevenueStatsDTO {
    private List<Map<String, Object>> dailyData;
    private Long maxAmount;

    public List<Map<String, Object>> getDailyData() {
        return dailyData;
    }

    public void setDailyData(List<Map<String, Object>> dailyData) {
        this.dailyData = dailyData;
    }

    public Long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Long maxAmount) {
        this.maxAmount = maxAmount;
    }
}
