package com.burialsociety.audit_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs", schema = "audit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String username; // 'user' is reserved keyword in some DBs
    private String action; // CREATE, UPDATE, DELETE
    private String entityName; // MEMBER, CLAIM, etc

    @Column(columnDefinition = "TEXT")
    private String changes; // JSON string of before/after
}
