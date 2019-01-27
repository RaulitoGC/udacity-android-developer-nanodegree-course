package com.rguzman.popularmovie.domain.usecase;

public class DataWrapper<T> {
    private Exception exception;
    private T data;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
