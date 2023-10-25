package com.spotify.exceptions;

public class MediaTypeNotSupportedException extends RuntimeException{

    public MediaTypeNotSupportedException() {
        super();
    }

    public MediaTypeNotSupportedException(String message) {
        super(message);
    }

    public MediaTypeNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MediaTypeNotSupportedException(Throwable cause) {
        super(cause);
    }
}
