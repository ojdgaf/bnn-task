package com.tasks.bnn.api.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportDTO {
    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String embedUrl;

    @JsonProperty
    private String webUrl;

    @JsonProperty
    private String datasetId;
}
