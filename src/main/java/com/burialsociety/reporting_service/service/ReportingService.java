package com.burialsociety.reporting_service.service;

import com.burialsociety.reporting_service.dto.ChartDataDto;
import com.burialsociety.reporting_service.dto.ReportSummaryDto;

public interface ReportingService {
    ReportSummaryDto getDashboardSummary();
    ChartDataDto getMemberGrowthData();
    ChartDataDto getClaimsAnalysisData();

    ChartDataDto getArrearsAgingData();

    List<com.burialsociety.partner_service.entity.Partner> getPartnerPerformance();

    com.burialsociety.reporting_service.dto.MainDashboardStatsDto getMainDashboardStats();
}
