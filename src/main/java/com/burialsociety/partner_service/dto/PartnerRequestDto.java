package com.burialsociety.partner_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRequestDto {
    @NotBlank(message = "Partner name is required")
    private String name;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;

    private String email;
    private double slaAdherence;
    private double rating;
    private String status;
}
