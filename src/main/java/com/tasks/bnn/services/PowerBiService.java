package com.tasks.bnn.services;

import java.util.Arrays;
import java.util.List;

import com.tasks.bnn.config.PowerBiConfig;
import com.tasks.bnn.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PowerBiService {
    @Autowired
    private PowerBiConfig config;

    public List<Tile> getTilesForDashboard(JwtToken token) {
        String url = "https://api.powerbi.com/v1.0/myorg/dashboards/" + config.getDefaultDashboardId() + "/tiles";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(token.getAccessToken());

        DashboardTiles dashboardTiles = new RestTemplate().exchange(
                url, HttpMethod.GET, new HttpEntity(headers), DashboardTiles.class
        ).getBody();

        return Arrays.asList(dashboardTiles.getTiles());
    }

    public EmbedToken getEmbedTokenForTile(Tile tile, JwtToken jwtToken, String email) {
        String rawBody = "{\"accessLevel\":\"View\",\"identities\":[{\"username\":\"%s\",\"roles\":[\"identity\"],\"datasets\":[\"e9c9ce92-1507-468b-a917-c0a2792467d4\"]}]}";
        String body = String.format(rawBody, email);

        String url = "https://api.powerbi.com/v1.0/myorg/groups/" + config.getDefaultGroupId() + "/dashboards/" + config.getDefaultDashboardId() + "/tiles/" + tile.getId() + "/GenerateToken";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken.getAccessToken());

        return new RestTemplate().postForObject(
                url, new HttpEntity<>(body, headers), EmbedToken.class
        );
    }

    public EmbedTile createEmbedTile(Tile tile, EmbedToken embedToken) {
        return new EmbedTile(
                config.getDefaultGroupId(),
                config.getDefaultDashboardId(),
                tile.getDatasetId(),
                tile.getId(),
                embedToken.getId(),
                embedToken.getValue()
        );
    }
}
