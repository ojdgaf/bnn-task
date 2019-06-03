package com.tasks.bnn.config.jwt;

import java.net.URI;

import com.tasks.bnn.config.ActiveDirectoryConfig;
import com.tasks.bnn.config.PowerBiConfig;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.VerificationJwkSelector;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ActiveDirectoryJwtValidator {
    private final ActiveDirectoryConfig activeDirectoryConfig;

    private final PowerBiConfig powerBiConfig;

    private final JwtProvider provider;

    private String token;

    public void validate(String token) {
        this.token = token;

        if (!validateStructure())
            throw new MalformedJwtException("Invalid JWT structure");

        if (!validateSignature())
            throw new SignatureException("Invalid JWT signature");

        // also validate "exp" claim
        Claims claims = provider.extractClaims(token);

        if (!validateAudience(claims.getAudience()))
            throw new JwtException("Invalid JWT audience");

        if (!validateUsername(provider.extractActiveDirectoryUsername(token)))
            throw new UnsupportedJwtException("Unsupported user's domain");
    }

    private boolean validateStructure() {
        return token != null && token.split("\\.").length == 3;
    }

    private boolean validateSignature() {
        try {
            JsonWebKeySet publicKeySet = new JsonWebKeySet(
                    new RestTemplate().getForObject(new URI(getAzurePublicKeysUri()), String.class)
            );

            JsonWebSignature signature = new JsonWebSignature();
            signature.setAlgorithmConstraints(
                    new AlgorithmConstraints(
                            AlgorithmConstraints.ConstraintType.WHITELIST,
                            AlgorithmIdentifiers.RSA_USING_SHA256
                    )
            );
            signature.setCompactSerialization(token);

            VerificationJwkSelector publicKeySelector = new VerificationJwkSelector();
            JsonWebKey jsonWebKey = publicKeySelector.select(signature, publicKeySet.getJsonWebKeys());
            signature.setKey(jsonWebKey.getKey());

            return signature.verifySignature();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateAudience(String audience) {
        return audience.equals(powerBiConfig.getClientId());
    }

    private boolean validateUsername(String username) {
        return username.endsWith(activeDirectoryConfig.getDirectoryDomain());
    }

    private String getAzurePublicKeysUri() {
        return "https://login.microsoftonline.com/" + activeDirectoryConfig.getDirectoryId() + "/discovery/keys";
    }
}
