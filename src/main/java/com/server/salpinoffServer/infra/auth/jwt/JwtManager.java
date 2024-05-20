package com.server.salpinoffServer.infra.auth.jwt;

import com.server.salpinoffServer.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtManager {

    private final long accessTokenExpirationPeriod;

    private final long refreshTokenExpirationPeriod;

    private final String jwtSecretKey;

    public JwtManager(@Value("${props.jwt.accessTokenExpirationPeriod}") long accessTokenExpirationPeriod,
                      @Value("${props.jwt.refreshTokenExpirationPeriod}") long refreshTokenExpirationPeriod,
                      @Value("${props.jwt.secretKey}") String jwtSecretKey) {
        this.accessTokenExpirationPeriod = accessTokenExpirationPeriod;
        this.refreshTokenExpirationPeriod = refreshTokenExpirationPeriod;
        this.jwtSecretKey = jwtSecretKey;
    }

    public String createRefreshToken(Member member) {
        return createToken(member.getId(), member.getAuthority(), refreshTokenExpirationPeriod);
    }

    public String createAccessToken(Member member) {
        return createToken(member.getId(), member.getAuthority(), accessTokenExpirationPeriod);
    }

    private String createToken(Long memberId, Member.Authority authority, long tokenExpirationPeriod) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(memberId.toString())
                .addClaims(Map.of("authority", authority.name()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + tokenExpirationPeriod))
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public Long getMemberId(String accessToken) {
        Claims claim = getClaims(accessToken);

        return Long.parseLong(claim.getSubject());
    }

    public String getAuthority(String accessToken) {
        Claims claim = getClaims(accessToken);

        return claim.get("authority", String.class);
    }

    public boolean isExpired(String token) {
        try {
            getClaims(token);
        } catch (ExpiredJwtException e) {
            return true;
        }
        return false;
    }

    private Claims getClaims(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey.getBytes(StandardCharsets.UTF_8))
                .build().parseClaimsJws(accessToken).getBody();
    }
}