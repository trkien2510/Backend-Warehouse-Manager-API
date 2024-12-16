package com.trkien.WarehouseManager.utils;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class JwtUtils {

    public String extractUsername(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            return claims.getSubject();

        } catch (ParseException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
}
