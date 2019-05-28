package com.rguzman.techstore.domain.usecase;

import androidx.lifecycle.LiveData;

public interface UseCaseCallback<R> {
  void onNetworkResponse(LiveData<R> liveData);

  void onDiskResponse(LiveData<R> liveData);

  void onError(Exception exception);
}
