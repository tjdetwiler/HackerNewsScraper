package com.detwiler.hackernews;

/**
 * Exception thrown when performing any authentication operation.
 */
public class HnAuthenticationException extends HnException {

    /**
     * Constructs a new exception with the given message.
     *
     * @param message Error message.
     */
    public HnAuthenticationException(final String message) {
        super(message);
    }
}
