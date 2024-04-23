package com.server.salpinoffServer.infra.auth.jwt;

import com.server.salpinoffServer.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtManager {

    @Value("${props.jwt.accessTokenExpirationPeriod}")
    private long accessTokenExpirationPeriod;

    @Value("${props.jwt.refreshTokenExpirationPeriod}")
    private long refreshTokenExpirationPeriod;

    @Value("${props.jwt.secretKey}")
    private String jwtSecretKey;

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

    public Long getUserId(String accessToken) {
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey.getBytes(StandardCharsets.UTF_8))
                .build().parseClaimsJws(accessToken).getBody();

        return Long.parseLong(claim.getSubject());
    }

    public String getAuthority(String accessToken) {
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey.getBytes(StandardCharsets.UTF_8))
                .build().parseClaimsJws(accessToken).getBody();

        return claim.get("authority", String.class);
    }
}