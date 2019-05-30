package com.tasks.bnn.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbedReport {
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

    public EmbedReport(String groupId, String datasetId, String reportId, String embedTokenId, String embedToken, String embedUrl, String webUrl) {
        this.groupId = groupId;
        this.datasetId = datasetId;
        this.reportId = reportId;
        this.embedTokenId = embedTokenId;
        this.embedToken = embedToken;
        this.embedUrl = embedUrl;
        this.webUrl = webUrl;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
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

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
