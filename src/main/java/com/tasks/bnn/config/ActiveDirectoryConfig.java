package com.tasks.bnn.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ActiveDirectoryConfig {
    @Value("${azure.activedirectory.id}")
    private String directoryId;

    @Value("${azure.activedirectory.domain}")
    private String directoryDomain;
}
