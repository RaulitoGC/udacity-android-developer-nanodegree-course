package com.rguzman.techstore.data.net;

import androidx.lifecycle.MutableLiveData;

public interface NetworkCallback<T> {

    void onResponse(MutableLiveData<T> liveData);

    void onError(Exception exception);
}
