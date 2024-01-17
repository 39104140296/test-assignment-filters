package com.example.backend.controller.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ApiException> handleException(Exception ex, WebRequest request) {
        final HttpHeaders headers = new HttpHeaders();
        if (ex instanceof EntityNotFoundException exception) {
            final HttpStatus status = HttpStatus.NOT_FOUND;
            return handleEntityNotFoundException(exception, headers, status, request);
        } else {
            final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleInternalException(ex, null, headers, status, request);
        }
    }

    protected ResponseEntity<ApiException> handleEntityNotFoundException(
            EntityNotFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleInternalException(ex, new ApiException(ex.getMessage()), headers, status, request);
    }

    protected ResponseEntity<ApiException> handleInternalException(
            Exception ex,
            ApiException body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
