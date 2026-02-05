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
    private final com.burialsociety.partner_service.repository.PartnerRepository partnerRepository;
    private final com.burialsociety.member_service.repository.MembershipPlanRepository membershipPlanRepository;
    private final com.burialsociety.member_service.repository.PlanAddOnRepository planAddOnRepository;
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

    @Override
    public ChartDataDto getArrearsAgingData() {
        // Mocked Aging Data
        return ChartDataDto.builder()
                .labels(Arrays.asList("Current", "30 Days", "60 Days", "90 Days", "120+ Days"))
                .datasets(Arrays.asList(
                        DataSetDto.builder()
                                .label("Outstanding Amount ($)")
                                .data(Arrays.asList(12000, 5000, 3000, 1500, 800))
                                .backgroundColor("rgba(75, 192, 192, 0.7)")
                                .build()
                ))
                .build();
    }

    @Override
    public List<com.burialsociety.partner_service.entity.Partner> getPartnerPerformance() {
        // Fetch real partners from DB
        return partnerRepository.findAll();
    }

    @Override
    public com.burialsociety.reporting_service.dto.MainDashboardStatsDto getMainDashboardStats() {
        long totalMembers = memberRepository.count();
        long pendingClaims = claimRepository.count(); // TODO: Filter by status='LOGGED'

        // Mock Charts for now (Skeleton)
        ChartDataDto bookingsChart = ChartDataDto.builder()
                .labels(Arrays.asList("Jan", "Feb", "Mar", "Apr"))
                .datasets(Arrays.asList(DataSetDto.builder().label("Registrations").data(Arrays.asList(12, 19, 3, 5)).build()))
                .build();

        // Revenue by Service (Plan)
        // Group MembershipPlans by name and sum totalPrice
        java.util.Map<String, java.math.BigDecimal> revenueMap = new java.util.HashMap<>();
        membershipPlanRepository.findAll().forEach(plan -> {
            String name = plan.getPlanName() != null ? plan.getPlanName() : "Unknown";
            java.math.BigDecimal price = plan.getTotalPrice() != null ? plan.getTotalPrice() : java.math.BigDecimal.ZERO;
            revenueMap.put(name, revenueMap.getOrDefault(name, java.math.BigDecimal.ZERO).add(price));
        });

        ChartDataDto revenueChart = ChartDataDto.builder()
                .labels(new ArrayList<>(revenueMap.keySet()))
                .datasets(Arrays.asList(DataSetDto.builder()
                        .label("Revenue")
                        .data(revenueMap.values().stream().map(java.math.BigDecimal::doubleValue).collect(java.util.stream.Collectors.toList()))
                        .backgroundColor("rgba(54, 162, 235, 0.7)") // Default color
                        .build()))
                .build();

        // Add-ons Popularity
        // Group PlanAddOns by name and count
        java.util.Map<String, Long> addOnCountMap = new java.util.HashMap<>();
        planAddOnRepository.findAll().forEach(addOn -> {
            String name = addOn.getName() != null ? addOn.getName() : "Unknown";
            addOnCountMap.put(name, addOnCountMap.getOrDefault(name, 0L) + 1);
        });

        ChartDataDto addOnsChart = ChartDataDto.builder()
                .labels(new ArrayList<>(addOnCountMap.keySet()))
                .datasets(Arrays.asList(DataSetDto.builder()
                        .label("Bookings")
                        .data(new ArrayList<>(addOnCountMap.values()))
                        .backgroundColor("rgba(255, 206, 86, 0.7)")
                        .build()))
                .build();
        
        // Total Revenue Calculation
        java.math.BigDecimal totalRevenue = revenueMap.values().stream().reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);


        return com.burialsociety.reporting_service.dto.MainDashboardStatsDto.builder()
                .totalBookings(totalMembers)
                .revenue(totalRevenue)
                .activeServices(totalMembers) // Assuming all active
                .pendingRequests(pendingClaims)
                .bookingsOverTime(bookingsChart)
                .revenueByService(revenueChart)
                .addOnsPopularity(addOnsChart)
                .build();
    }
}
