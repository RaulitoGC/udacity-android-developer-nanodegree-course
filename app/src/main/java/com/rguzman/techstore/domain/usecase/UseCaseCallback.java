package com.rguzman.techstore.domain.usecase;

public interface UseCaseCallback<R> {
    void onNetworkResponse(R data);

    void onDiskResponse(R data);

    void onError(Exception exception);
}
