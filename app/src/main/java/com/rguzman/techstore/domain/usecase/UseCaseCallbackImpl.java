package com.rguzman.techstore.domain.usecase;

public class UseCaseCallbackImpl<R> implements UseCaseCallback<R> {
    @Override
    public void onNetworkResponse(R data) {

    }

    @Override
    public void onDiskResponse(R data) {

    }

    @Override
    public void onError(Exception exception) {

    }
}
