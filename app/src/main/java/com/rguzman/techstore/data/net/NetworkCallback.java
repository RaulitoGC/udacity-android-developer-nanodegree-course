package com.rguzman.techstore.data.net;

import androidx.lifecycle.LiveData;

public interface NetworkCallback<T> {

    void onResponse(LiveData<T> liveData);

    void onError(Exception exception);
}
