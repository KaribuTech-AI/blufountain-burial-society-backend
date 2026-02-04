package com.burialsociety.reporting_service.service;

import com.burialsociety.claims_service.repository.ClaimRepository;
import com.burialsociety.member_service.repository.MemberRepository;
import com.burialsociety.reporting_service.dto.ReportSummaryDto;
import com.burialsociety.reporting_service.service.impl.ReportingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ReportingServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ClaimRepository claimRepository;

    @InjectMocks
    private ReportingServiceImpl reportingService;

    @Test
    void getDashboardSummary_ShouldCalculateRatioCorrectly() {
        when(memberRepository.count()).thenReturn(100L);
        when(claimRepository.count()).thenReturn(5L);

        ReportSummaryDto summary = reportingService.getDashboardSummary();

        // Ratio = (5 / 100) * 100 = 5.0
        assertEquals(5.0, summary.getClaimsRatio());
        assertEquals(100L, summary.getActiveMembers());
    }

    @Test
    void getDashboardSummary_ShouldHandleZeroMembers() {
        when(memberRepository.count()).thenReturn(0L);
        when(claimRepository.count()).thenReturn(0L);

        ReportSummaryDto summary = reportingService.getDashboardSummary();

        assertEquals(0.0, summary.getClaimsRatio());
        assertEquals(0L, summary.getActiveMembers());
    }
}
