package com.cardanoj.api.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * A global exception handler for the Cardano J API application.
 * <p>
 * This class handles exceptions that occur within the application and provides custom responses for different types of exceptions.
 * It uses the {@link ControllerAdvice} annotation to handle exceptions globally across all controllers.
 * </p>
 */
@ControllerAdvice
public class CardanoJGlobalExceptionHandler {

    /**
     * Handles {@link NoHandlerFoundException} which is thrown when no handler is found for a given URL.
     * <p>
     * Returns a custom error message with HTTP status 404 (Not Found) to indicate that the requested URL does not exist.
     * </p>
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error message and HTTP status
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        String errorMessage = "The URL you have entered is incorrect. Please check and try again.";
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
