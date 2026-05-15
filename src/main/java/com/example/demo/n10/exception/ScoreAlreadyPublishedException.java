package com.example.demo.n10.exception;

/**
 * Exception thrown when trying to modify a score that has been published or locked.
 * This is a business rule violation that prevents data corruption.
 */
public class ScoreAlreadyPublishedException extends RuntimeException {
    
    public ScoreAlreadyPublishedException(String message) {
        super(message);
    }

    public ScoreAlreadyPublishedException(String message, Throwable cause) {
        super(message, cause);
    }
}
