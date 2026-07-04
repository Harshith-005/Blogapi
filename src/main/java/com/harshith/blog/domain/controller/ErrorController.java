package com.harshith.blog.domain.controller;

import com.harshith.blog.domain.dto.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorController {

    // Handles all unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        log.error("Caught exception", e);

        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Unexpected Error Occurred")
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handles invalid JSON or request body parsing errors
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {

        log.error("Invalid request body", e);

        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Invalid request body")
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handles IllegalStateException
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalStateException(
            IllegalStateException e) {

        log.error("Illegal state exception", e);

        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(
            BadCredentialsException e) {

        log.error("Bad Credentials", e);

        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Invalid Credentials")
                .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


}