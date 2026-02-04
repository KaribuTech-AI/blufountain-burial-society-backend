package com.burialsociety.member_service.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CitizenshipRequestDto {
    private String country;
    private String status;
    private LocalDate dateOfAcquisition;
}
