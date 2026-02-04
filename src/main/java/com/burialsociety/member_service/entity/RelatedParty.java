package com.burialsociety.member_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "related_parties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatedParty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "relationship_type")
    private String relationshipType; // SPOUSE, NEXT OF KIN, BENEFICIARY

    private String firstname;
    private String lastname;
    
    // Additional fields might be needed like ID number, Contact, etc.
    // For now assuming basic details based on 'PartnershipDetails'
    
    @Column(name = "same_address")
    private Boolean sameAddress;
    
    @Column(name = "same_employer")
    private Boolean sameEmployer;
    
    @Column(name = "is_existing_customer")
    private String isAnExistingCustomer;
}
