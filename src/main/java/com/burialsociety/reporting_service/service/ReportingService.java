package com.burialsociety.reporting_service.service;

import com.burialsociety.partner_service.entity.Partner;
import com.burialsociety.reporting_service.dto.ChartDataDto;
import com.burialsociety.reporting_service.dto.ReportSummaryDto;

import java.util.List;

public interface ReportingService {
    ReportSummaryDto getDashboardSummary();
    ChartDataDto getMemberGrowthData();
    ChartDataDto getClaimsAnalysisData();

    ChartDataDto getArrearsAgingData();

    List<Partner> getPartnerPerformance();

    com.burialsociety.reporting_service.dto.MainDashboardStatsDto getMainDashboardStats();
}
