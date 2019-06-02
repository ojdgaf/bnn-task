package com.tasks.bnn.config.jwt;

import java.io.IOException;

import io.jsonwebtoken.JwtException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ActiveDirectoryJwtFilter extends OncePerRequestFilter {
    @Autowired
    private ActiveDirectoryJwtValidator validator;

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

            validator.validate(token);

            String username = (String) provider.extractClaims(token).get("unique_name");

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(username, "", AuthorityUtils.NO_AUTHORITIES)
            );
        } catch (JwtException e) {
            SecurityContextHolder.clearContext();
            throw e;
        }

        chain.doFilter(req, res);
    }
}
