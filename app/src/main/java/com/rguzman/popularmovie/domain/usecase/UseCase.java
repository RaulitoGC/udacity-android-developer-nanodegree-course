package com.rguzman.popularmovie.domain.usecase;

import android.arch.lifecycle.LiveData;

public abstract class UseCase<P, R> {

    public abstract void execute(P params, Callback<R> callback);

    public interface Callback<R> {
        void onResponse(LiveData<R> liveData);

        void onError(Exception exception);
    }
}
