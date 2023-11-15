package com.remcoil.skiron.advice;

import com.remcoil.skiron.exception.EntryDoesNotExistException;
import com.remcoil.skiron.exception.ExceptionDetails;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(EntryDoesNotExistException.class)
    public ResponseEntity<ExceptionDetails> entryDoesNotExistHandler(EntryDoesNotExistException e) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(e.getMessage());
        return ResponseEntity
                .status(HttpServletResponse.SC_NOT_FOUND)
                .body(exceptionDetails);
    }
}
