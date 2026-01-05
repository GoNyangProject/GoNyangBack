package com.example.tossback.config.jwt.util;


import com.example.tossback.common.enums.JwtTokenType;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JWTUtil {

    @Value("${access-expiration-hours}")
    private Long accessExpirationHours;

    @Value("${refresh-expiration-hours}")
    private Long refreshExpirationHours;

    private final SecretKey secretKey;

    public JWTUtil(@Value("${secret-key}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUserId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userId", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class); // 토큰 생성 시 "role"로 저장했을 경우
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT 만료됨", e);
        } catch (JwtException e) {
            log.warn("유효하지 않은 JWT", e);
        }

        return false;
    }
    public String createToken(String userId, String role, JwtTokenType type) {
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = currentTimeMillis +
                (type == JwtTokenType.ACCESS
                        ? accessExpirationHours
                        : refreshExpirationHours) * 60 * 60 * 1000L;

        JwtBuilder builder = Jwts.builder()
                .claim("userId", userId)
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(expirationTimeMillis))
                .signWith(secretKey);

        // Access 토큰일 경우에만 role 포함
        if (type == JwtTokenType.ACCESS) {
            builder.claim("role", role);
        }

        return builder.compact();
    }

}
