package com.tasks.bnn.config.jwt;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider provider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws ServletException, IOException {
        try {
            String token = provider.extractToken(req);

            if (token != null) {
                provider.validateToken(token);
                SecurityContextHolder.getContext().setAuthentication(createAuthentication(token));
            }
        } catch (JwtException | IllegalArgumentException e) {
            SecurityContextHolder.clearContext();
            createJsonErrorResponse(req, res, e);
            return;
        }

        chain.doFilter(req, res);
    }

    private Authentication createAuthentication(String token) {
        String username = provider.extractUsername(token);
        String[] authorityNames = provider.extractAuthorityNames(token);
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(authorityNames);

        return new UsernamePasswordAuthenticationToken(username, "", authorities);
    }

    /*
        @TODO Use Spring default exception handler
     */
    private void createJsonErrorResponse(
            HttpServletRequest req,
            HttpServletResponse res,
            Exception e
    ) throws IOException {
        ObjectNode body = new ObjectMapper().createObjectNode();
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        body.put("code", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", e.getMessage());
        body.put("path", req.getRequestURI());

        res.setStatus(status.value());
        res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        res.getWriter().write(body.toString());
    }
}
