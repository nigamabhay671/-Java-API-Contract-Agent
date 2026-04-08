package com.example.ordermanagementapi.exception;

/**
 * Exception thrown when a requested resource is not found.
 *
 * This is a runtime exception that will be caught by the global exception handler.
 *
 * @author Java API Contract Agent
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
