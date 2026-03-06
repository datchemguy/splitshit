package org.sol.splitshit.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.util.Date;

@Service
public class JwtService {
    private String secret;
    private long expiration;
    private Clock clock;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("604800000") long expiration,
                      Clock clock) {
        this.secret = secret;
        this.expiration = expiration;
        this.clock = clock;
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generate(@NonNull String username) {
        long now = clock.millis();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expiration))
                .encryptWith(key(), Jwts.ENC.A128CBC_HS256)
                .compact();
    }

    public String extractSubject(@NonNull String token) {
        return Jwts.parser()
                .decryptWith(key())
                .build()
                .parseEncryptedClaims(token)
                .getPayload()
                .getSubject();
    }
}
