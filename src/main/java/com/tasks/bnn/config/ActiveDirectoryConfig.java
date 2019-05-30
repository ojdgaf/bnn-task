package com.tasks.bnn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ActiveDirectoryConfig {
    @Value("${azure.activedirectory.powerbi.client-id}")
    private String clientId;

    @Value("${azure.activedirectory.powerbi.client-secret}")
    private String clientSecret;

    @Value("${azure.activedirectory.powerbi.username}")
    private String powerBiProAccountUsername;

    @Value("${azure.activedirectory.powerbi.password}")
    private String powerBiProAccountPassword;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getPowerBiProAccountUsername() {
        return powerBiProAccountUsername;
    }

    public String getPowerBiProAccountPassword() {
        return powerBiProAccountPassword;
    }
}
