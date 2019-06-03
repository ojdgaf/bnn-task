package com.tasks.bnn.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbedTileDTO {
    @JsonProperty
    private String groupId;

    @JsonProperty
    private String dashboardId;

    @JsonProperty
    private String datasetId;

    @JsonProperty
    private String tileId;

    @JsonProperty
    private String embedTokenId;

    @JsonProperty
    private String embedToken;
}
