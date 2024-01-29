package eu.wisercat.filter.controller.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ApiException> handleEntityNotFoundException(EntityNotFoundException ex) {
        return handleException(ex, NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiException> handleInternalException(Exception ex) {
        return handleException(ex, INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ApiException> handleException(Exception ex, HttpStatus status) {
        return new ResponseEntity<>(new ApiException(ex.getLocalizedMessage()), new HttpHeaders(), status);
    }
}
