package com.glqdlt.pm6.thumbnailextract.api;

/**
 * Date 2019-11-11
 *
 * @author glqdlt
 */
public class AsyncExtractError extends ExtractError {

    public AsyncExtractError(String message, Throwable cause, String id) {
        super(message, cause);
        this.id = id;
    }

    private String id;

    public AsyncExtractError(String message, String id) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
