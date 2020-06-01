package com.rguzman.baking.data.net;

import android.arch.lifecycle.LiveData;

public interface NetworkCallback<T> {
    void onResponse(LiveData<T> liveData);

    void onError(Exception exception);
}
