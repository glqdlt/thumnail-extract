package com.glqdlt.pm6.thumbnail;

public class ThumbnailCliError extends RuntimeException {

    public ThumbnailCliError(String message) {
        super(message);
    }

    public ThumbnailCliError(String message, Throwable cause) {
        super(message, cause);
    }
}
