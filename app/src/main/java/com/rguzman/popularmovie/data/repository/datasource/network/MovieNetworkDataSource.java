package com.rguzman.popularmovie.data.repository.datasource.network;


import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.rguzman.popularmovie.BuildConfig;
import com.rguzman.popularmovie.data.exception.GenericException;
import com.rguzman.popularmovie.data.exception.NetworkConnectionException;
import com.rguzman.popularmovie.data.net.ApiService;
import com.rguzman.popularmovie.data.net.NetworkCallback;
import com.rguzman.popularmovie.data.net.response.GetReviewsByMovieResponse;
import com.rguzman.popularmovie.data.net.response.GetVideosByMovieResponse;
import com.rguzman.popularmovie.data.net.response.MovieListResponse;
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.model.Review;
import com.rguzman.popularmovie.domain.model.Video;
import com.rguzman.popularmovie.presentation.utils.NetworkUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void loadPopularMovies(NetworkCallback<List<Movie>> callback) {
        if (!NetworkUtils.isThereNetworkConnection(context)) {
            callback.onError(new NetworkConnectionException());
        } else {

            Call<MovieListResponse> call = apiService.loadPopularMovies(BuildConfig.MOVIE_API_KEY);
            call.enqueue(new Callback<MovieListResponse>() {
                @Override
                public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
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
    public void loadTopRatedMovies(NetworkCallback<List<Movie>> callback) {
        if (!NetworkUtils.isThereNetworkConnection(context)) {
            callback.onError(new NetworkConnectionException());
        } else {

            Call<MovieListResponse> call = apiService.loadTopRatedMovies(BuildConfig.MOVIE_API_KEY);
            call.enqueue(new Callback<MovieListResponse>() {
                @Override
                public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
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
    public void loadVideosByMovie(int id, NetworkCallback<List<Video>> callback) {
        if (!NetworkUtils.isThereNetworkConnection(context)) {
            callback.onError(new NetworkConnectionException());
        } else {
            Call<GetVideosByMovieResponse> call = apiService.loadVideosByMovie(id, BuildConfig.MOVIE_API_KEY);
            call.enqueue(new Callback<GetVideosByMovieResponse>() {
                @Override
                public void onResponse(Call<GetVideosByMovieResponse> call, Response<GetVideosByMovieResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
                        final MutableLiveData<List<Video>> liveData = new MutableLiveData<>();
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
                public void onFailure(Call<GetVideosByMovieResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.onError(new Exception(t));
                }
            });

        }
    }

    @Override
    public void loadReviewsByMovie(int id, NetworkCallback<List<Review>> callback) {
        if (!NetworkUtils.isThereNetworkConnection(context)) {
            callback.onError(new NetworkConnectionException());
        } else {
            Call<GetReviewsByMovieResponse> call = apiService.loadReviewsByMovie(id, BuildConfig.MOVIE_API_KEY);
            call.enqueue(new Callback<GetReviewsByMovieResponse>() {
                @Override
                public void onResponse(Call<GetReviewsByMovieResponse> call, Response<GetReviewsByMovieResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
                        final MutableLiveData<List<Review>> liveData = new MutableLiveData<>();
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
                public void onFailure(Call<GetReviewsByMovieResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.onError(new Exception(t));
                }
            });

        }
    }

}
