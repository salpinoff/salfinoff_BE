package com.server.salpinoffServer.infra.exception.ui;

import com.server.salpinoffServer.infra.exception.NotFoundException;
import com.server.salpinoffServer.infra.exception.ui.dto.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandleController {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.warn("AccessDeniedException msg: {}", e.getMessage());
        return createExceptionResponseEntity(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e) {
        log.warn("NotFoundException msg: {}", e.getMessage());
        return createExceptionResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAnotherException(Exception e) {
        log.warn("Exception msg: {}", e.getMessage());
        return createExceptionResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private ResponseEntity<ExceptionResponse> createExceptionResponseEntity(HttpStatus s, String e) {
        return ResponseEntity.status(s).body(new ExceptionResponse(s.name(), e));
    }
}
