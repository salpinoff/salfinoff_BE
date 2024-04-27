package com.server.salpinoffServer.infra.exception.ui;

import com.server.salpinoffServer.infra.exception.NotFoundException;
import com.server.salpinoffServer.infra.exception.ui.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException e) {
        return createExceptionResponseEntity(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e) {
        return createExceptionResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
    }

    private ResponseEntity<ExceptionResponse> createExceptionResponseEntity(HttpStatus s, String e) {
        return ResponseEntity.status(s).body(new ExceptionResponse(s.name(), e));
    }
}
