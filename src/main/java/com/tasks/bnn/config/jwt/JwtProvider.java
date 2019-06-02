package com.tasks.bnn.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Date;
import java.util.Base64;

@Component
public class JwtProvider {
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTHORITIES_KEY = "authorities";

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expirationInMs}")
    private long expirationInMs;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(Map<String, Object> claims) {
        if (!checkClaims(claims))
            throw new JwtException("Could not create JWT token: invalid claims");

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + expirationInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    String extractToken(HttpServletRequest req) {
        String header = req.getHeader(AUTHORIZATION_HEADER_NAME);

        return header != null && header.startsWith(TOKEN_PREFIX) ?
                header.replace(TOKEN_PREFIX, "") : null;
    }

    String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    String[] extractAuthorityNames(String token) {
        String[] authorityNames = new String[0];

        try {
            Object element = extractClaims(token).get(AUTHORITIES_KEY);
            return (String[]) element;
        } catch (Exception e) {
            return authorityNames;
        }
    }

    void validateToken(String token) {
        extractClaims(token);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private boolean checkClaims(Map<String, Object> claims) {
        // claims must provide subject (username)
        if (!claims.containsKey("sub"))
            return false;

        // create empty array of authorities
        if (!claims.containsKey(AUTHORITIES_KEY))
            claims.put("authorities", new String[0]);

        Object subject = claims.get("sub");

        return subject instanceof String && !((String) subject).isEmpty();
    }
}
