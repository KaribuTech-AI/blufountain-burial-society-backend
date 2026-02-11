package com.burialsociety.partner_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerResponseDto {
    private Long id;
    private String name;
    private String category;
    private String contactNumber;
    private String email;
    private double slaAdherence;
    private double rating;
    private String status;
}
