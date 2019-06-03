package com.tasks.bnn.services;

import com.tasks.bnn.config.PowerBiConfig;
import com.tasks.bnn.api.dto.JwtTokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ActiveDirectoryServiceImpl implements ActiveDirectoryService {
    private static final String AUTHORIZATION_URL = "https://login.microsoftonline.com/common/oauth2/token";

    private final PowerBiConfig config;

    public JwtTokenDTO getPowerBiAdminAccessToken() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("resource", PowerBiConfig.RESOURCE);
        body.add("client_id", config.getClientId());
        body.add("client_secret", config.getClientSecret());
        body.add("username", config.getAdminUsername());
        body.add("password", config.getAdminPassword());

        return restTemplate.postForObject(
                AUTHORIZATION_URL,
                new HttpEntity<>(body, headers),
                JwtTokenDTO.class
        );
    }
}
