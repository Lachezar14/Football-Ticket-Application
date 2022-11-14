package com.ultras.footbalticketsapp.exception.handler;

import com.ultras.footbalticketsapp.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RuntimeExceptionHandler {
    //custom exception handling for the CRUD operations
    //this method will be called when the anything connected with the CRUD operations fails
    // (ex. not enough tickets available,user already exists, match not found, there are no tickets available, etc.),
    // and it will return a response entity with the error message
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> onRuntimeException(RuntimeException e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}
