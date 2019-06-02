package com.tasks.bnn.config.jwt;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.VerificationJwkSelector;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URI;

@Component
public class AzureJwtFilter extends OncePerRequestFilter {
    private final static String AZURE_PUBLIC_KEYS_URL = "https://login.microsoftonline.com/ojdgaf.onmicrosoft.com/discovery/keys";

    @Autowired
    private JwtProvider provider;

    private String token;

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws ServletException, IOException {
        try {
            token = provider.extractToken(req);

//                throw new Exception("Missing or Invalid Authorization Token in Request");

            if (verifyToken() && verifySignature() && verifyClaims())
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("", "", AuthorityUtils.NO_AUTHORITIES));
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            return;
        }

        chain.doFilter(req, res);
    }

    private boolean verifyToken() {
        return token == null || token.split("\\.").length != 3;
    }

    private boolean verifySignature() {
        try {
            String publicKeySetJson = new RestTemplate().getForObject(
                    new URI(AZURE_PUBLIC_KEYS_URL),
                    String.class
            );
            JsonWebKeySet publicKeySet = new JsonWebKeySet(publicKeySetJson);

            JsonWebSignature signature = new JsonWebSignature();
            signature.setAlgorithmConstraints(new AlgorithmConstraints(
                    AlgorithmConstraints.ConstraintType.WHITELIST,
                    AlgorithmIdentifiers.RSA_USING_SHA256
            ));
            signature.setCompactSerialization(token);

            VerificationJwkSelector publicKeySelector = new VerificationJwkSelector();
            JsonWebKey jsonWebKey = publicKeySelector.select(signature, publicKeySet.getJsonWebKeys());
            signature.setKey(jsonWebKey.getKey());

            return signature.verifySignature();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean verifyClaims() {

    }
}
