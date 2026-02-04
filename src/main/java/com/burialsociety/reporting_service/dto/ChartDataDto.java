package com.burialsociety.reporting_service.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ChartDataDto {
    private List<String> labels;
    private List<DataSetDto> datasets;
}
