package com.burialsociety.claims_service.dto;

import lombok.Builder;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Builder
public class DocumentDto {
    private Long id;
    private String documentType;
    private String fileName;
    private String url; // or download link
    private OffsetDateTime uploadDate;
}
