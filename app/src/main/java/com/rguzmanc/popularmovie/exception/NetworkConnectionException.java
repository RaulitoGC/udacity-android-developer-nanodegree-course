package com.rguzmanc.popularmovie.exception;

public class NetworkConnectionException extends Exception {
    public NetworkConnectionException() {
    }

    public NetworkConnectionException(String message) {
        super(message);
    }

    public NetworkConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
