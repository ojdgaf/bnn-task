package com.tasks.bnn.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbedTile {
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

    public EmbedTile(String groupId, String dashboardId, String datasetId, String tileId, String embedTokenId, String embedToken) {
        this.groupId = groupId;
        this.dashboardId = dashboardId;
        this.datasetId = datasetId;
        this.tileId = tileId;
        this.embedTokenId = embedTokenId;
        this.embedToken = embedToken;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(String dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public String getTileId() {
        return tileId;
    }

    public void setTileId(String tileId) {
        this.tileId = tileId;
    }

    public String getEmbedTokenId() {
        return embedTokenId;
    }

    public void setEmbedTokenId(String embedTokenId) {
        this.embedTokenId = embedTokenId;
    }

    public String getEmbedToken() {
        return embedToken;
    }

    public void setEmbedToken(String embedToken) {
        this.embedToken = embedToken;
    }
}
