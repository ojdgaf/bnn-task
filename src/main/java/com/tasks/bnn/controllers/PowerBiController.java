package com.tasks.bnn.controllers;

import com.tasks.bnn.config.PowerBiConfig;
import com.tasks.bnn.dto.*;
import com.tasks.bnn.services.ActiveDirectoryService;
import com.tasks.bnn.services.PowerBiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("powerbi")
public class PowerBiController {
    @Autowired
    private ActiveDirectoryService activeDirectoryService;

    @Autowired
    private PowerBiService powerBiService;

    @GetMapping("embed/tiles")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<EmbedTile> getEmbedTileTokensForUser(@RequestParam String email) {
        List<EmbedTile> response = new ArrayList<>();

        powerBiService.setJwtToken(activeDirectoryService.getAccessToken(PowerBiConfig.RESOURCE));

        for (Tile tile : powerBiService.getTilesInDashboard()) {
            EmbedToken embedToken = powerBiService.getEmbedToken(tile, email);

            response.add(powerBiService.createEmbedTile(tile, embedToken));
        }

        return response;
    }

    @GetMapping("embed/reports")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<EmbedReport> getEmbedReportTokensForUser(@RequestParam String email) {
        List<EmbedReport> response = new ArrayList<>();

        powerBiService.setJwtToken(activeDirectoryService.getAccessToken(PowerBiConfig.RESOURCE));

        for (Report report : powerBiService.getReportsInGroup()) {
            EmbedToken embedToken = powerBiService.getEmbedToken(report, email);

            response.add(powerBiService.createEmbedReport(report, embedToken));
        }

        return response;
    }
}
