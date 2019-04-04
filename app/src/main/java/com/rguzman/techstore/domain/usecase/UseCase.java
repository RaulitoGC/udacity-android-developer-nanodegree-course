package com.rguzman.techstore.domain.usecase;

import androidx.lifecycle.LiveData;

public abstract class UseCase<P, R> {
    private boolean forceCache;

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

    public boolean isForceCache() {
        return forceCache;
    }

    public void setForceCache(boolean forceCache) {
        this.forceCache = forceCache;
    }

    public interface Callback<R> {
        void onNetworkResponse(LiveData<R> liveData);

        void onDiskResponse(LiveData<R> liveData);

        void onError(Exception exception);
    }
}
