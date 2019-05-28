package com.rguzman.techstore.domain.usecase;

import androidx.lifecycle.LiveData;

public class UseCaseCallbackImpl<R> implements UseCaseCallback<R> {
  @Override
  public void onNetworkResponse(LiveData<R> liveData) {

  }

  @Override
  public void onDiskResponse(LiveData<R> liveData) {

  }

  @Override
  public void onError(Exception exception) {

  }
}
