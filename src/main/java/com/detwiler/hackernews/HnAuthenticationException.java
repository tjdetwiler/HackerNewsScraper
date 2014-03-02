package com.detwiler.hackernews;

public class HnAuthenticationException extends HnException {

    public HnAuthenticationException() {
        super();
    }

    public HnAuthenticationException(final String message) {
        super(message);
    }

    public HnAuthenticationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
