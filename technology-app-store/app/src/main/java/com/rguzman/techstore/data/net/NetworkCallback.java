package com.rguzman.techstore.data.net;

public interface NetworkCallback<T> {

    void onResponse(T data);

    void onError(Exception exception);
}
