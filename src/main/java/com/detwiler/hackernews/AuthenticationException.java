package com.detwiler.hackernews;

public class AuthenticationException extends HnException {

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(final String message) {
        super(message);
    }

    public AuthenticationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
