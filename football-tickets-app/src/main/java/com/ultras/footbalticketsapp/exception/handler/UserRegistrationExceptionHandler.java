package com.ultras.footbalticketsapp.exception.handler;

import com.ultras.footbalticketsapp.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class UserRegistrationExceptionHandler {
    //TODO make exception handling for runtime exceptions and illegalstateexceptions
    //custom exception handling for the user registration
    //this method will be called when the user registration validation fails,
    // and it will return a response entity with the error message
    @ExceptionHandler
    ResponseEntity<ErrorResponse> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse error = new ErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.setMessage(fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(error);
    }
}
