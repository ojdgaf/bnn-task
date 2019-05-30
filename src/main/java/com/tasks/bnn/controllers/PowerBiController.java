package com.tasks.bnn.controllers;

import com.tasks.bnn.config.PowerBiConfig;
import com.tasks.bnn.dto.EmbedToken;
import com.tasks.bnn.dto.JwtToken;
import com.tasks.bnn.dto.Tile;
import com.tasks.bnn.dto.EmbedTile;
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

    @GetMapping("/")
    public List<EmbedTile> getEmbedTileTokensForUser(@RequestParam String email) {
        List<EmbedTile> response = new ArrayList<>();

        JwtToken jwtToken = activeDirectoryService.getAccessToken(PowerBiConfig.RESOURCE);

        for (Tile tile : powerBiService.getTilesForDashboard(jwtToken)) {
            EmbedToken embedToken = powerBiService.getEmbedTokenForTile(tile, jwtToken, email);

            response.add(powerBiService.createEmbedTile(tile, embedToken));
        }

        return response;
    }
}
