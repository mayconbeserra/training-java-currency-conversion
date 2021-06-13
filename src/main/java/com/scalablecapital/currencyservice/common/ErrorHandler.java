package com.scalablecapital.currencyservice.common;

import java.time.LocalDateTime;

import javax.validation.ConstraintViolationException;

import com.scalablecapital.currencyservice.services.CurrencyNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ErrorHandler {
    private static final String HTTP_STATUS_5XX_ERROR = "Unfortunately, an unexpected error has occurred! Please, try it again soon";

    @ExceptionHandler(value = CurrencyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCurrencyNotFoundException(CurrencyNotFoundException exception) {
        ErrorResponse error = ErrorResponse.of(
            exception.getMessage(),
            HttpStatus.NOT_FOUND,
            LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleBadFormating(NumberFormatException exception) {
        ErrorResponse error = ErrorResponse.of(
            exception.getMessage(),
            HttpStatus.BAD_REQUEST,
            LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintValidation(ConstraintViolationException exception) {
        ErrorResponse error = ErrorResponse.of(
            exception.getMessage(), 
            HttpStatus.BAD_REQUEST,
            LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(RuntimeException exception) {
        ErrorResponse error = ErrorResponse.of(
            HTTP_STATUS_5XX_ERROR,
            HttpStatus.INTERNAL_SERVER_ERROR,
            LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
