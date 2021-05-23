package com.glqdlt.pm6.commons;

/**
 * @author glqdlt
 */
public class SimpleRuntimeException extends RuntimeException {
    public SimpleRuntimeException(String message) {
        super(message);
    }

    public SimpleRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SimpleRuntimeException(Throwable cause) {
        super(cause);
    }

    public SimpleRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
