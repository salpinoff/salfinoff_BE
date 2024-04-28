package com.server.salpinoffServer.infra.auth.jwt.filter;

import com.server.salpinoffServer.infra.auth.dto.MemberInfo;
import com.server.salpinoffServer.infra.auth.jwt.JwtManager;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private final JwtManager jwtManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            try {
                final String accessToken = authorizationHeader.substring(7);
                MemberInfo memberInfo = new MemberInfo(jwtManager.getMemberId(accessToken), jwtManager.getAuthority(accessToken));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(memberInfo, null, memberInfo.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } catch (ExpiredJwtException e) {
                throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "토큰이 만료 되었습니다.");
            } catch (Exception e) {
                throw new JwtException("유효하지 않은 토큰 입니다. 다시 로그인 해주세요.");
            }
        }

        filterChain.doFilter(request, response);
    }
}
