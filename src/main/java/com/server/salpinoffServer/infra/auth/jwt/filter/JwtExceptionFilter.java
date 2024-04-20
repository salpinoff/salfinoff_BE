package com.server.salpinoffServer.infra.auth.jwt.filter;

import com.server.salpinoffServer.infra.exception.ui.dto.ExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e);
        } catch (JwtException e) {
            setErrorResponse(HttpStatus.FORBIDDEN, response, e);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ExceptionResponse jwtExceptionResponse = new ExceptionResponse(status.name(), e.getMessage());
        response.getWriter().write(jwtExceptionResponse.convertToJson());
    }
}
