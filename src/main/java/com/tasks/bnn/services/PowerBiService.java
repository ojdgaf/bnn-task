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

    public List<Tile> getTilesInDashboard(JwtToken token) {
        String url = "https://api.powerbi.com/v1.0/myorg/dashboards/" + config.getDefaultDashboardId() + "/tiles";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(token.getAccessToken());

        TilesInDashboard tilesInDashboard = new RestTemplate().exchange(
                url, HttpMethod.GET, new HttpEntity(headers), TilesInDashboard.class
        ).getBody();

        return Arrays.asList(tilesInDashboard.getTiles());
    }

    public List<Report> getReportsInGroup(JwtToken token) {
        String url = "https://api.powerbi.com/v1.0/myorg/groups/" + config.getDefaultGroupId() + "/reports";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(token.getAccessToken());

        ReportsInGroup tilesInDashboard = new RestTemplate().exchange(
                url, HttpMethod.GET, new HttpEntity(headers), ReportsInGroup.class
        ).getBody();

        return Arrays.asList(tilesInDashboard.getReports());
    }

    public EmbedToken getEmbedToken(Tile tile, JwtToken jwtToken, String email) {
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

    public EmbedToken getEmbedToken(Report report, JwtToken jwtToken, String email) {
        String rawBody = "{\"accessLevel\":\"View\",\"identities\":[{\"username\":\"%s\",\"roles\":[\"identity\"],\"datasets\":[\"%s\"]}]}";
        String body = String.format(rawBody, email, report.getDatasetId());

        String url = "https://api.powerbi.com/v1.0/myorg/groups/" + config.getDefaultGroupId() + "/reports/" + report.getId() + "/GenerateToken";

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

    public EmbedReport createEmbedReport(Report report, EmbedToken embedToken) {
        return new EmbedReport(
                config.getDefaultGroupId(),
                config.getDefaultDashboardId(),
                report.getId(),
                embedToken.getId(),
                embedToken.getValue(),
                report.getEmbedUrl(),
                report.getWebUrl()
        );
    }
}
