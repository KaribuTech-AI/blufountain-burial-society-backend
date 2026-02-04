package com.burialsociety.member_service.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Builder
@Table(name = "members")
@Getter // Using @Getter/@Setter from Lombok is cleaner
@Setter // than @Data for entities.
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "case_number")
    private String caseNumber;

    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp // Automatically sets this on creation
    @Column(name = "creation_date",updatable = false)
    private OffsetDateTime creationDate;


// --- THIS IS THE NEW PART ---
    /**
     * This is the "owning" side of the relationship.
     * mappedBy = "member" tells JPA that the 'PersonalDetails' class
     * has a field named 'member' that handles the @JoinColumn.

     * CascadeType.ALL: When we save a Member, save its PersonalDetails.
     * When we delete a Member, delete its PersonalDetails.
     * orphanRemoval = true: If we set member.setPersonalDetails(null),
     * it will delete the old record from the database.
     */
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private PersonalDetails personalDetails;

    // Helper method to link both sides of the relationship
    public void setPersonalDetails(PersonalDetails details) {
        if (details != null) {
            details.setMember(this); // Set the back-reference
        }
        this.personalDetails = details;
    }
}
