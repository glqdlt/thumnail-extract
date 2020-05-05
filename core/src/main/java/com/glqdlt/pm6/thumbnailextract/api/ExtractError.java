package com.glqdlt.pm6.thumbnailextract.api;

/**
 * Date 2019-11-11
 *
 * @author glqdlt
 */
public class ExtractError extends RuntimeException {
    public ExtractError(String message) {
        super(message);
    }

    public ExtractError(String message, Throwable cause) {
        super(message, cause);
    }
}
