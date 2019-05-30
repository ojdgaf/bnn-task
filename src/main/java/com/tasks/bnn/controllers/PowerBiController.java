package com.tasks.bnn.controllers;

import com.tasks.bnn.config.PowerBiConfig;
import com.tasks.bnn.dto.*;
import com.tasks.bnn.services.ActiveDirectoryService;
import com.tasks.bnn.services.PowerBiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PowerBiController {
    @Autowired
    private ActiveDirectoryService activeDirectoryService;

    @Autowired
    private PowerBiService powerBiService;

    @GetMapping("/powerbi/embed/tiles")
    public List<EmbedTile> getEmbedTileTokensForUser(@RequestParam String email) {
        List<EmbedTile> response = new ArrayList<>();

        powerBiService.setJwtToken(activeDirectoryService.getAccessToken(PowerBiConfig.RESOURCE));

        for (Tile tile : powerBiService.getTilesInDashboard()) {
            EmbedToken embedToken = powerBiService.getEmbedToken(tile, email);

            response.add(powerBiService.createEmbedTile(tile, embedToken));
        }

        return response;
    }

    @GetMapping("/powerbi/embed/reports")
    public List<EmbedReport> getEmbedReportTokensForUser(@RequestParam String email) {
        List<EmbedReport> response = new ArrayList<>();

        powerBiService.setJwtToken(activeDirectoryService.getAccessToken(PowerBiConfig.RESOURCE));

        for (Report report : powerBiService.getReportsInGroup()) {
            // TODO remove filter
            if (report.getId().equals("d82652f0-0e3d-4625-bc1e-1b8f79b0d463"))
                continue;

            EmbedToken embedToken = powerBiService.getEmbedToken(report, email);

            response.add(powerBiService.createEmbedReport(report, embedToken));
        }

        return response;
    }
}
