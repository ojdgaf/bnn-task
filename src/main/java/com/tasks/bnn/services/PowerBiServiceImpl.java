package com.tasks.bnn.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.tasks.bnn.api.dto.*;
import com.tasks.bnn.config.PowerBiConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PowerBiServiceImpl implements PowerBiService {
    private static final String EMBED_REQUEST_BODY_JSON = "{\"accessLevel\":\"View\",\"identities\":[{\"username\":\"%s\",\"roles\":[\"%s\"],\"datasets\":[\"%s\"]}]}";

    private final PowerBiConfig config;

    private JwtTokenDTO jwtToken;

    public void setJwtToken(JwtTokenDTO jwtToken) {
        this.jwtToken = jwtToken;
    }

    public List<EmbedTileDTO> getEmbedTiles(JwtTokenDTO jwtToken, String username) {
        List<EmbedTileDTO> embedTiles = new ArrayList<>();

        setJwtToken(jwtToken);

        for (TileDTO tile : getTilesInDashboard()) {
            EmbedTokenDTO embedToken = getEmbedToken(tile, username);

            embedTiles.add(createEmbedTile(tile, embedToken));
        }

        return embedTiles;
    }

    public List<EmbedReportDTO> getEmbedReports(JwtTokenDTO jwtToken, String username) {
        List<EmbedReportDTO> embedReports = new ArrayList<>();

        setJwtToken(jwtToken);

        for (ReportDTO report : getReportsInGroup()) {
            EmbedTokenDTO embedToken = getEmbedToken(report, username);

            embedReports.add(createEmbedReport(report, embedToken));
        }

        return embedReports;
    }

    private List<TileDTO> getTilesInDashboard() {
        String url = "https://api.powerbi.com/v1.0/myorg/dashboards/" + config.getDefaultDashboardId() + "/tiles";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(jwtToken.getAccessToken());

        TilesInDashboardDTO tilesInDashboard = new RestTemplate().exchange(
                url, HttpMethod.GET, new HttpEntity(headers), TilesInDashboardDTO.class
        ).getBody();

        return Arrays.asList(tilesInDashboard.getTiles());
    }

    private List<ReportDTO> getReportsInGroup() {
        String url = "https://api.powerbi.com/v1.0/myorg/groups/" + config.getDefaultGroupId() + "/reports";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(jwtToken.getAccessToken());

        ReportsInGroupDTO tilesInDashboard = new RestTemplate().exchange(
                url, HttpMethod.GET, new HttpEntity(headers), ReportsInGroupDTO.class
        ).getBody();

        return Arrays.asList(tilesInDashboard.getReports());
    }

    private EmbedTokenDTO getEmbedToken(TileDTO tile, String email) {
        String body = String.format(
                EMBED_REQUEST_BODY_JSON,
                email,
                config.getDefaultRole(),
                config.getDefaultDatasetId()
        );

        String url = "https://api.powerbi.com/v1.0/myorg/groups/" + config.getDefaultGroupId() + "/dashboards/" + config.getDefaultDashboardId() + "/tiles/" + tile.getId() + "/GenerateToken";

        return requestEmbedToken(url, body);
    }

    private EmbedTokenDTO getEmbedToken(ReportDTO report, String email) {
        String body = String.format(
                EMBED_REQUEST_BODY_JSON,
                email,
                config.getDefaultRole(),
                report.getDatasetId()
        );

        String url = "https://api.powerbi.com/v1.0/myorg/groups/" + config.getDefaultGroupId() + "/reports/" + report.getId() + "/GenerateToken";

        return requestEmbedToken(url, body);
    }

    private EmbedTileDTO createEmbedTile(TileDTO tile, EmbedTokenDTO embedToken) {
        return new EmbedTileDTO(
                config.getDefaultGroupId(),
                config.getDefaultDashboardId(),
                tile.getDatasetId(),
                tile.getId(),
                embedToken.getId(),
                embedToken.getValue()
        );
    }

    private EmbedReportDTO createEmbedReport(ReportDTO report, EmbedTokenDTO embedToken) {
        return new EmbedReportDTO(
                config.getDefaultGroupId(),
                config.getDefaultDashboardId(),
                report.getId(),
                embedToken.getId(),
                embedToken.getValue(),
                report.getEmbedUrl(),
                report.getWebUrl()
        );
    }

    private EmbedTokenDTO requestEmbedToken(String url, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken.getAccessToken());

        return new RestTemplate().postForObject(
                url, new HttpEntity<>(body, headers), EmbedTokenDTO.class
        );
    }
}
