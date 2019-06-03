package com.tasks.bnn.api.controllers;

import java.util.List;
import com.tasks.bnn.api.dto.EmbedReportDTO;
import com.tasks.bnn.api.dto.EmbedTileDTO;
import com.tasks.bnn.api.dto.JwtTokenDTO;
import com.tasks.bnn.services.ActiveDirectoryService;
import com.tasks.bnn.services.PowerBiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/powerbi")
@RequiredArgsConstructor
public class PowerBiController {
    private final ActiveDirectoryService activeDirectoryService;

    private final PowerBiService powerBiService;

    @GetMapping("embed/tiles")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<EmbedTileDTO> getEmbedTileTokensForUser(Authentication auth) {
        JwtTokenDTO token = activeDirectoryService.getPowerBiAdminAccessToken();

        return powerBiService.getEmbedTiles(token, auth.getPrincipal().toString());
    }

    @GetMapping("embed/reports")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<EmbedReportDTO> getEmbedReportTokensForUser(Authentication auth) {
        JwtTokenDTO token = activeDirectoryService.getPowerBiAdminAccessToken();

        return powerBiService.getEmbedReports(token, auth.getPrincipal().toString());
    }
}
