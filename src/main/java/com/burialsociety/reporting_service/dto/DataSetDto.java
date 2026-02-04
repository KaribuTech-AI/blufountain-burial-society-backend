package com.burialsociety.reporting_service.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class DataSetDto {
    private String label;
    private List<Number> data;
    private String borderColor;
    private String backgroundColor;
}
