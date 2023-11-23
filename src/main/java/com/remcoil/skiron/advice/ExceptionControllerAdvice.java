package com.remcoil.skiron.advice;

import com.remcoil.skiron.exception.*;
import com.remcoil.skiron.model.response.ExceptionDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(EntryDoesNotExistException.class)
    public ResponseEntity<ExceptionDetails> entryDoesNotExistHandler(EntryDoesNotExistException e, HttpServletRequest request) {
        return buildResponse(HttpServletResponse.SC_NOT_FOUND, e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({
            BadCredentialsException.class,
            ForbiddenRoleException.class,
            LockedProductException.class,
            UnlockedProductException.class,
            InactiveProductException.class,
            InvalidPasswordException.class,
            EntryAlreadyExistException.class
    })
    public ResponseEntity<ExceptionDetails> runTimeHandler(Exception e, HttpServletRequest request) {
        return buildResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), request.getRequestURI());
    }

    private ResponseEntity<ExceptionDetails> buildResponse(int statusCode, String message, String path) {
        return ResponseEntity
                .status(statusCode)
                .body(new ExceptionDetails(statusCode, message, path));
    }
}
