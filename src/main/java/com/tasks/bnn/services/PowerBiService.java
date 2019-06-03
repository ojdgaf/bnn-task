package com.tasks.bnn.services;

import java.util.List;

import com.tasks.bnn.api.dto.EmbedReportDTO;
import com.tasks.bnn.api.dto.EmbedTileDTO;
import com.tasks.bnn.api.dto.JwtTokenDTO;

public interface PowerBiService {
    List<EmbedTileDTO> getEmbedTiles(JwtTokenDTO jwtToken, String username);

    List<EmbedReportDTO> getEmbedReports(JwtTokenDTO jwtToken, String username);
}
