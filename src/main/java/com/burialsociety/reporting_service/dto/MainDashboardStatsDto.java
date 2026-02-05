package com.burialsociety.reporting_service.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class MainDashboardStatsDto {
    private long totalBookings; // Member Count
    private BigDecimal revenue; // Payment Sum
    private long activeServices; // Active Plans
    private long pendingRequests; // Pending Claims
    
    private ChartDataDto bookingsOverTime;
    private ChartDataDto revenueByService;
    private ChartDataDto bookingStatus;
    private ChartDataDto addOnsPopularity;
}
