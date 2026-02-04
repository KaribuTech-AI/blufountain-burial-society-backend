package com.burialsociety.member_service.dto;

import lombok.Data;

@Data
public class DocumentMetadataRequestDto {
    private String documentType;
    private String fileName;
    private String contentBase64;
}
