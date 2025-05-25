package com.exercise.userapi.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public final class JWTGenerator {

  private final SecretKey secretKey;

    public JWTGenerator( @Value("${spring.application.jwt.secret-key}") String secret){
        if(StringUtils.isBlank(secret)) {
            this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            return;
        }
        this.secretKey = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(String username) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 3600_000; //1 hour

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(expMillis))
                .signWith(secretKey)
                .compact();
    }

}
