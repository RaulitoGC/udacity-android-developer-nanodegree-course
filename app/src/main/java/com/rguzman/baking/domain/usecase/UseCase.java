package com.rguzman.baking.domain.usecase;

import android.arch.lifecycle.LiveData;

public abstract class UseCase<P, R> {

    public abstract void execute(P params, Callback<R> callback);

    public void execute(Callback<R> callback) {
        this.execute(null, callback);
    }

    public interface Callback<R> {
        void onNetworkResponse(LiveData<R> liveData);

        void onError(Exception exception);
    }
}
