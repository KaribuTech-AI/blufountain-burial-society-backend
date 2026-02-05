package com.burialsociety.member_service.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Builder
@Table(name = "members", schema = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "case_number")
    private String caseNumber;

    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp // Automatically sets this on creation
    @Column(name = "creation_date", updatable = false)
    private OffsetDateTime creationDate;

    /**
     * This is the "owning" side of the relationship.
     * mappedBy = "member" tells JPA that the 'PersonalDetails' class
     * has a field named 'member' that handles the @JoinColumn.
     *
     * CascadeType.ALL: When we save a Member, save its PersonalDetails.
     * When we delete a Member, delete its PersonalDetails.
     * orphanRemoval = true: If we set member.setPersonalDetails(null),
     * it will delete the old record from the database.
     */
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private PersonalDetails personalDetails;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ContactDetails contactDetails;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private java.util.List<AddressDetails> addressDetails;

    // Added for Phase 2: Billing
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private com.burialsociety.billing_service.entity.BillingAccount billingAccount;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Citizenship citizenship;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private EmploymentDetails employmentDetails;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private java.util.List<RelatedParty> relatedParties;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private MembershipPlan membershipPlan;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private java.util.List<DocumentMetadata> documents;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Preferences preferences;

    // Helper methods to link both sides of the relationship
    public void setPersonalDetails(PersonalDetails details) {
        if (details != null) {
            details.setMember(this);
        }
        this.personalDetails = details;
    }

    public void setContactDetails(ContactDetails details) {
        if (details != null) {
            details.setMember(this);
        }
        this.contactDetails = details;
    }

    public void setAddressDetails(java.util.List<AddressDetails> details) {
        if (details != null) {
            details.forEach(d -> d.setMember(this));
        }
        this.addressDetails = details;
    }

    public void setCitizenship(Citizenship citizenship) {
        if (citizenship != null) {
            citizenship.setMember(this);
        }
        this.citizenship = citizenship;
    }

    public void setEmploymentDetails(EmploymentDetails details) {
        if (details != null) {
            details.setMember(this);
        }
        this.employmentDetails = details;
    }

    public void setRelatedParties(java.util.List<RelatedParty> parties) {
        if (parties != null) {
            parties.forEach(p -> p.setMember(this));
        }
        this.relatedParties = parties;
    }

    public void setMembershipPlan(MembershipPlan plan) {
        if (plan != null) {
            plan.setMember(this);
        }
        this.membershipPlan = plan;
    }

    public void setDocuments(java.util.List<DocumentMetadata> docs) {
        if (docs != null) {
            docs.forEach(d -> d.setMember(this));
        }
        this.documents = docs;
    }

    public void setPreferences(Preferences prefs) {
        if (prefs != null) {
            prefs.setMember(this);
        }
        this.preferences = prefs;
    }
}
