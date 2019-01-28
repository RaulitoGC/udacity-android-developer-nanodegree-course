package com.rguzman.popularmovie.data.repository.datasource.network;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.rguzman.popularmovie.BuildConfig;
import com.rguzman.popularmovie.data.exception.GenericException;
import com.rguzman.popularmovie.data.exception.NetworkConnectionException;
import com.rguzman.popularmovie.data.net.ApiService;
import com.rguzman.popularmovie.data.net.response.MovieListResponse;
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.presentation.utils.NetworkUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public class MovieNetworkDataSource implements NetworkDataSource {

    private final ApiService apiService;
    private final Context context;

    @Inject
    public MovieNetworkDataSource(ApiService apiService, Context context) {
        this.apiService = apiService;
        this.context = context;
    }

    @Override
    public void loadPopularMovies(MovieNetworkCallback callback) {
        if (!NetworkUtils.isThereNetworkConnection(context)) {
            callback.onError(new NetworkConnectionException());
        } else {

            Call<MovieListResponse> call = apiService.loadPopularMovies(BuildConfig.MOVIE_API_KEY);
            call.enqueue(new Callback<MovieListResponse>() {
                @Override
                public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
                        Timber.d(" LIST GET DATA FROM NETWORK");
                        final MutableLiveData<List<Movie>> liveData = new MutableLiveData<>();
                        liveData.setValue(response.body().getResults());
                        callback.onResponse(liveData);
                    } else {
                        if (response.body() != null && response.body().getMessage() != null) {
                            callback.onError(new Exception(response.body().getMessage()));
                        } else {
                            callback.onError(new GenericException());
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieListResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.onError(new Exception(t));
                }
            });

        }
    }

    @Override
    public void loadTopRatedMovies(MovieNetworkCallback callback) {
        if (!NetworkUtils.isThereNetworkConnection(context)) {
            callback.onError(new NetworkConnectionException());
        } else {

            Call<MovieListResponse> call = apiService.loadTopRatedMovies(BuildConfig.MOVIE_API_KEY);
            call.enqueue(new Callback<MovieListResponse>() {
                @Override
                public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
                        Timber.d(" LIST GET DATA FROM NETWORK");
                        final MutableLiveData<List<Movie>> liveData = new MutableLiveData<>();
                        liveData.setValue(response.body().getResults());
                        callback.onResponse(liveData);
                    } else {
                        if (response.body() != null && response.body().getMessage() != null) {
                            callback.onError(new Exception(response.body().getMessage()));
                        } else {
                            callback.onError(new GenericException());
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieListResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.onError(new Exception(t));
                }
            });

        }
    }


    public interface MovieNetworkCallback {
        void onResponse(LiveData<List<Movie>> liveData);

        void onError(Exception exception);
    }

}
