package com.detwiler.hackernews;

/**
 * Base class for exceptions in this library.
 */
public class HnException extends Exception {

    /**
     * @param message Message describing the nature of the exception.
     */
    public HnException(final String message) {
        super(message);
    }
}
