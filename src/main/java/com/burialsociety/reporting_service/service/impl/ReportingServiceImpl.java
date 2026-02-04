package com.burialsociety.reporting_service.service.impl;

import com.burialsociety.claims_service.repository.ClaimRepository;
import com.burialsociety.member_service.repository.MemberRepository;
import com.burialsociety.reporting_service.dto.ChartDataDto;
import com.burialsociety.reporting_service.dto.DataSetDto;
import com.burialsociety.reporting_service.dto.ReportSummaryDto;
import com.burialsociety.reporting_service.service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportingServiceImpl implements ReportingService {

    private final MemberRepository memberRepository;
    private final ClaimRepository claimRepository;
    // Inject Billing Repos if needed, for now using basic counts

    @Override
    public ReportSummaryDto getDashboardSummary() {
        long memberCount = memberRepository.count();
        long claimCount = claimRepository.count();
        
        double claimsRatio = memberCount > 0 ? ((double) claimCount / memberCount) * 100 : 0;

        return ReportSummaryDto.builder()
                .activeMembers(memberCount)
                .claimsRatio(Math.round(claimsRatio * 100.0) / 100.0)
                .collectionRate(88.5) // Mocked for PVP simplicity as Payment Repo aggregation is heavy
                .avgClaimTurnaroundDays(3.2) // Mocked
                .build();
    }

    @Override
    public ChartDataDto getMemberGrowthData() {
        // Mocking Monthly Data for prototype visualization
        // In real world: GROUP BY MONTH(created_at)
        return ChartDataDto.builder()
                .labels(Arrays.asList("Jan", "Feb", "Mar", "Apr", "May"))
                .datasets(Arrays.asList(
                        DataSetDto.builder()
                                .label("New Registrations")
                                .data(Arrays.asList(10, 15, 8, 20, 25))
                                .borderColor("rgb(75, 192, 192)")
                                .backgroundColor("rgba(75, 192, 192, 0.5)")
                                .build()
                ))
                .build();
    }

    @Override
    public ChartDataDto getClaimsAnalysisData() {
        return ChartDataDto.builder()
                .labels(Arrays.asList("Harare", "Bulawayo", "Gweru"))
                .datasets(Arrays.asList(
                        DataSetDto.builder()
                                .label("Claims")
                                .data(Arrays.asList(5, 8, 2)) // Mocked
                                .backgroundColor("rgb(255, 99, 132)")
                                .build()
                ))
                .build();
    }
}
