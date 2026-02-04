package com.burialsociety.reporting_service.controller;

import com.burialsociety.reporting_service.dto.ChartDataDto;
import com.burialsociety.reporting_service.dto.ReportSummaryDto;
import com.burialsociety.reporting_service.service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReportingController {

    private final ReportingService reportingService;

    @GetMapping("/summary")
    public ResponseEntity<ReportSummaryDto> getSummary() {
        return ResponseEntity.ok(reportingService.getDashboardSummary());
    }

    @GetMapping("/member-growth")
    public ResponseEntity<ChartDataDto> getMemberGrowth() {
        return ResponseEntity.ok(reportingService.getMemberGrowthData());
    }

    @GetMapping("/claims-analysis")
    public ResponseEntity<ChartDataDto> getClaimsAnalysis() {
        return ResponseEntity.ok(reportingService.getClaimsAnalysisData());
    }
}
