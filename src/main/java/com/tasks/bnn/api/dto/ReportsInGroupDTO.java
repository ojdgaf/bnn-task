package com.tasks.bnn.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportsInGroupDTO {
    @JsonProperty("value")
    private ReportDTO[] reports;
}
