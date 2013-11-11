package com.detwiler.hackernews;

public class HnException extends Exception {

    public HnException() {
        super();
    }

    public HnException(final String message) {
        super(message);
    }

    public HnException(final String message, Throwable cause) {
        super(message, cause);
    }
}
