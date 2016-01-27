package com.rln.acme.exception;


public class InvalidFileException extends Exception {

    public InvalidFileException(final String message) {
        super(message);
    }

    public InvalidFileException(final String message, final Throwable e) {
        super(message,e);
    }

}
