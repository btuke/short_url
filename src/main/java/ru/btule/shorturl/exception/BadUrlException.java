package ru.btule.shorturl.exception;

public class BadUrlException extends RuntimeException{

    public BadUrlException() {
    }

    public BadUrlException(String message) {
        super(message);
    }

    public BadUrlException(String message, Throwable cause) {
        super(message, cause);
    }
}
