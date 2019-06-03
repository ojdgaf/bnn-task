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
    private static final String EMBED_REQUEST_BODY_JSON = "{\"accessLevel\":\"View\",\"identities\":[{\"username\":\"%s\",\"roles\":[\"%s\"],\"datasets\":[\"%s\"]}]}";

    @Autowired
    private PowerBiConfig config;

    private JwtToken jwtToken;

    public void setJwtToken(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }

    public List<Tile> getTilesInDashboard() {
        String url = "https://api.powerbi.com/v1.0/myorg/dashboards/" + config.getDefaultDashboardId() + "/tiles";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(jwtToken.getAccessToken());

        TilesInDashboard tilesInDashboard = new RestTemplate().exchange(
                url, HttpMethod.GET, new HttpEntity(headers), TilesInDashboard.class
        ).getBody();

        return Arrays.asList(tilesInDashboard.getTiles());
    }

    public List<Report> getReportsInGroup() {
        String url = "https://api.powerbi.com/v1.0/myorg/groups/" + config.getDefaultGroupId() + "/reports";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(jwtToken.getAccessToken());

        ReportsInGroup tilesInDashboard = new RestTemplate().exchange(
                url, HttpMethod.GET, new HttpEntity(headers), ReportsInGroup.class
        ).getBody();

        return Arrays.asList(tilesInDashboard.getReports());
    }

    public EmbedToken getEmbedToken(Tile tile, String email) {
        String body = String.format(
                EMBED_REQUEST_BODY_JSON,
                email,
                config.getDefaultRole(),
                config.getDefaultDatasetId()
        );

        String url = "https://api.powerbi.com/v1.0/myorg/groups/" + config.getDefaultGroupId() + "/dashboards/" + config.getDefaultDashboardId() + "/tiles/" + tile.getId() + "/GenerateToken";

        return requestEmbedToken(url, body);
    }

    public EmbedToken getEmbedToken(Report report, String email) {
        String body = String.format(
                EMBED_REQUEST_BODY_JSON,
                email,
                config.getDefaultRole(),
                report.getDatasetId()
        );

        String url = "https://api.powerbi.com/v1.0/myorg/groups/" + config.getDefaultGroupId() + "/reports/" + report.getId() + "/GenerateToken";

        return requestEmbedToken(url, body);
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

    private EmbedToken requestEmbedToken(String url, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken.getAccessToken());

        return new RestTemplate().postForObject(
                url, new HttpEntity<>(body, headers), EmbedToken.class
        );
    }
}
