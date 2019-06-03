package com.tasks.bnn.services;

import com.tasks.bnn.api.dto.JwtTokenDTO;

public interface ActiveDirectoryService {
    public JwtTokenDTO getPowerBiAdminAccessToken();
}
