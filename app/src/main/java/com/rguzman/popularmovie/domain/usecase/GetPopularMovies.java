package com.rguzman.popularmovie.domain.usecase;

import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;
import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;

import javax.inject.Inject;

public class GetPopularMovies extends UseCase<Void, List<Movie>> {

    private final MovieDataSource repository;

    @Inject
    public GetPopularMovies(MovieDataSource repository) {
        this.repository = repository;
    }

    @Override
    public void execute(boolean forceUpdate,Void params, Callback<List<Movie>> callback) {
        this.setForceUpdate(forceUpdate);
        this.repository.loadPopularMovies(isForceUpdate(),new Callback<List<Movie>>() {
            @Override
            public void onNetworkResponse(LiveData<List<Movie>> liveData) {
                callback.onNetworkResponse(liveData);
            }

            @Override
            public void onDiskResponse(LiveData<List<Movie>> liveData) {
                callback.onDiskResponse(liveData);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }
}
