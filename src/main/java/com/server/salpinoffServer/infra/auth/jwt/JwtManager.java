package com.server.salpinoffServer.infra.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtManager {

    @Value("${props.jwt.accessTokenExpirationPeriod}")
    private long accessTokenExpirationPeriod;

    @Value("${props.jwt.refreshTokenExpirationPeriod}")
    private long refreshTokenExpirationPeriod;

    @Value("${props.jwt.secretKey}")
    private String jwtSecretKey;


    public String createRefreshToken(Long memberId) {
        return createToken(memberId, refreshTokenExpirationPeriod);
    }

    public String createAccessToken(Long memberId) {
        return createToken(memberId, accessTokenExpirationPeriod);
    }

    private String createToken(Long memberId, long tokenExpirationPeriod) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(memberId.toString())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + tokenExpirationPeriod))
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public Long getUserId(String accessToken) {
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey.getBytes(StandardCharsets.UTF_8))
                .build().parseClaimsJws(accessToken).getBody();

        return Long.parseLong(claim.getSubject());
    }
}