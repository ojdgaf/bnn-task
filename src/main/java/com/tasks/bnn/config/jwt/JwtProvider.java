package com.tasks.bnn.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtProvider {
    private final static String AUTHORIZATION_HEADER_NAME = "Authorization";

    private final static String TOKEN_PREFIX = "Bearer ";

    String extractToken(HttpServletRequest req) {
        String header = req.getHeader(AUTHORIZATION_HEADER_NAME);

        return header != null && header.startsWith(TOKEN_PREFIX) ?
                header.replace(TOKEN_PREFIX, "") : null;
    }

    String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    void validateToken(String token) {
        extractClaims(token);
    }

    public Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
