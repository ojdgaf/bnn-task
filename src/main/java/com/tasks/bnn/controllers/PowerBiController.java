package com.tasks.bnn.controllers;

import java.util.List;
import java.util.ArrayList;

import com.tasks.bnn.dto.*;
import com.tasks.bnn.services.ActiveDirectoryService;
import com.tasks.bnn.services.PowerBiService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("powerbi")
public class PowerBiController {
    @Autowired
    private ActiveDirectoryService activeDirectoryService;

    @Autowired
    private PowerBiService powerBiService;

    @GetMapping("embed/tiles")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<EmbedTile> getEmbedTileTokensForUser(Authentication auth) {
        List<EmbedTile> response = new ArrayList<>();

        powerBiService.setJwtToken(activeDirectoryService.getPowerBiAdminAccessToken());

        for (Tile tile : powerBiService.getTilesInDashboard()) {
            EmbedToken embedToken = powerBiService.getEmbedToken(tile, auth.getPrincipal().toString());

            response.add(powerBiService.createEmbedTile(tile, embedToken));
        }

        return response;
    }

    @GetMapping("embed/reports")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<EmbedReport> getEmbedReportTokensForUser(Authentication auth) {
        List<EmbedReport> response = new ArrayList<>();

        powerBiService.setJwtToken(activeDirectoryService.getPowerBiAdminAccessToken());

        for (Report report : powerBiService.getReportsInGroup()) {
            EmbedToken embedToken = powerBiService.getEmbedToken(report, auth.getPrincipal().toString());

            response.add(powerBiService.createEmbedReport(report, embedToken));
        }

        return response;
    }
}
