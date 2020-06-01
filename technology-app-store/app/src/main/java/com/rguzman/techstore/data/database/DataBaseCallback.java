package com.rguzman.techstore.data.database;

import androidx.lifecycle.MutableLiveData;

public interface DataBaseCallback<T> {
    void onResponse(T data);

    void onError(Exception exception);
}
