package com.burialsociety.partner_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partners", schema = "partner")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "partner_category")
    private String category; // FUNERAL_PARLOUR, TRANSPORTER, TOMBSTONE_VENDOR

    private String contactNumber;
    private String email;

    private double slaAdherence; // 0-100
    private double rating; // 0-5.0
    private String status; // ACTIVE, PENDING
}
