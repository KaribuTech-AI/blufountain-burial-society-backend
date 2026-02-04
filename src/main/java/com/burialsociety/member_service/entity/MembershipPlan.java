package com.burialsociety.member_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "membership_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "plan_name")
    private String planName;
    
    @Column(name = "plan_identifier")
    private String planId; // from frontend 'planId'

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "membershipPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanAddOn> addOns;

    public void setAddOns(List<PlanAddOn> addOns) {
        if (addOns != null) {
            addOns.forEach(a -> a.setMembershipPlan(this));
        }
        this.addOns = addOns;
    }
}
