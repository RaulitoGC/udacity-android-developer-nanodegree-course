package com.rguzman.popularmovie.domain.usecase;

import android.arch.lifecycle.LiveData;

public abstract class UseCase<P, R> {

    private boolean forceUpdate;

    public abstract void execute(boolean forceUpdate, P params, Callback<R> callback);

    public void execute(P params, Callback<R> callback) {
        this.execute(false, params, callback);
    }

    public void execute(Callback<R> callback) {
        this.execute(false, null, callback);
    }

    public void execute(P params) {
        this.execute(false, params, null);
    }

    public void execute(boolean forceUpdate, Callback<R> callback) {
        this.execute(forceUpdate, null, callback);
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public interface Callback<R> {
        void onNetworkResponse(LiveData<R> liveData);

        void onDiskResponse(LiveData<R> liveData);

        void onError(Exception exception);
    }
}
