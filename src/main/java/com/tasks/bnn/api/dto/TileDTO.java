package com.tasks.bnn.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TileDTO {
    @JsonProperty
    private String id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String embedUrl;

    @JsonProperty
    private String reportId;

    @JsonProperty
    private String datasetId;
}
