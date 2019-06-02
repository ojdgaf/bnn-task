package com.tasks.bnn.controllers;

import java.util.Map;
import java.util.HashMap;

import com.tasks.bnn.dto.JwtToken;
import com.tasks.bnn.config.jwt.JwtProvider;
import com.tasks.bnn.services.ActiveDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private ActiveDirectoryService activeDirectoryService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("access_token")
    public JwtToken getJwtToken(
            @RequestParam String email,
            @RequestParam String authorizationCode
    ) {
        // grant access to our API if user can get access to Power BI API
        activeDirectoryService.getPowerBiUserAccessToken(authorizationCode);

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", email);

        return new JwtToken(jwtProvider.createToken(claims));
    }
}
