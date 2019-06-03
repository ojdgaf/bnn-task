package com.tasks.bnn.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbedReportDTO {
    @JsonProperty
    private String groupId;

    @JsonProperty
    private String datasetId;

    @JsonProperty
    private String reportId;

    @JsonProperty
    private String embedTokenId;

    @JsonProperty
    private String embedToken;

    @JsonProperty
    private String embedUrl;

    @JsonProperty
    private String webUrl;
}
