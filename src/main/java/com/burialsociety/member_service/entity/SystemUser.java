package com.burialsociety.member_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Represents system users (administrators, staff) for the application.
 * This is separate from the Member entity which represents society members.
 */
@Entity
@Table(name = "system_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role; // ADMIN, CLAIMS_OFFICER, FINANCE_CLERK, etc.

    @Column(name = "mfa_enabled")
    private Boolean mfaEnabled;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(nullable = false)
    private String status; // ACTIVE, INACTIVE, LOCKED
}
