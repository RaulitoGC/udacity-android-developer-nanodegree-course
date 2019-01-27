package com.rguzman.popularmovie.data.net.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract  class ApiCallback<T> implements Callback<T> {

    abstract void onSuccess(T data);
    abstract void onError(Exception exception);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()){
            onSuccess(response.body());
        }else{
            onError(new Exception(response.message()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onError(new Exception(t));
    }
}
