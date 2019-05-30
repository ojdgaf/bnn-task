package com.tasks.bnn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PowerBiConfig {
    public static final String RESOURCE = "https://analysis.windows.net/powerbi/api";

    @Value("${api.powerbi.default-group-id}")
    private String defaultGroupId;

    @Value("${api.powerbi.default-dashboard-id}")
    private String defaultDashboardId;

    @Value("${api.powerbi.default-dataset-id}")
    private String defaultDatasetId;

    @Value("${api.powerbi.default-role}")
    private String defaultRole;

    public String getDefaultGroupId() {
        return defaultGroupId;
    }

    public String getDefaultDashboardId() {
        return defaultDashboardId;
    }

    public String getDefaultDatasetId() {
        return defaultDatasetId;
    }

    public String getDefaultRole() {
        return defaultRole;
    }
}
