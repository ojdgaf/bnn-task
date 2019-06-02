package com.tasks.bnn.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtProvider {
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String USERNAME_KEY = "unique_name";

    String extractToken(HttpServletRequest req) {
        String header = req.getHeader(AUTHORIZATION_HEADER_NAME);

        return header != null && header.startsWith(TOKEN_PREFIX) ?
                header.replace(TOKEN_PREFIX, "") : null;
    }

    public Claims extractClaims(String token) {
        int i = token.lastIndexOf('.');
        String tokenWithoutSignature = token.substring(0, i + 1);
        return Jwts.parser().parseClaimsJwt(tokenWithoutSignature).getBody();
    }

    public Claims extractClaims(String token, String key) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    public String extractActiveDirectoryUsername(String token) {
        return (String) extractClaims(token).get(USERNAME_KEY);
    }
}
