package com.tasks.bnn.services;

import com.tasks.bnn.config.ActiveDirectoryConfig;
import com.tasks.bnn.dto.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ActiveDirectoryService {
    @Autowired
    private ActiveDirectoryConfig config;

    private String authorizationURL = "https://login.microsoftonline.com/common/oauth2/token";

    public JwtToken getAccessToken(String resource) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("resource", resource);
        body.add("client_id", config.getClientId());
        body.add("client_secret", config.getClientSecret());
        body.add("username", config.getPowerBiProAccountUsername());
        body.add("password", config.getPowerBiProAccountPassword());

        return restTemplate.postForObject(
                authorizationURL,
                new HttpEntity<>(body, headers),
                JwtToken.class
        );
    }
}
