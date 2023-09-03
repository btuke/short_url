package ru.btule.shorturl.exception;

public class EmptyParamException extends RuntimeException {

    public EmptyParamException() {
    }

    public EmptyParamException(String message) {
        super(message);
    }

    public EmptyParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
