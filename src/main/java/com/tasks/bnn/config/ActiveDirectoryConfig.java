package com.tasks.bnn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ActiveDirectoryConfig {
    @Value("${azure.activedirectory.id}")
    private String directoryId;

    @Value("${azure.activedirectory.domain}")
    private String directoryDomain;

    public String getDirectoryId() {
        return directoryId;
    }

    public String getDirectoryDomain() {
        return directoryDomain;
    }
}
