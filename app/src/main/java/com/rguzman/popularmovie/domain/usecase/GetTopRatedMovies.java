package com.rguzman.popularmovie.domain.usecase;

import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;
import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;

import javax.inject.Inject;

public class GetTopRatedMovies extends UseCase<Void, List<Movie>> {

    private final MovieDataSource repository;

    @Inject
    public GetTopRatedMovies(MovieDataSource repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Void params, Callback<List<Movie>> callback) {
        this.repository.loadTopRatedMovies(new Callback<List<Movie>>() {
            @Override
            public void onResponse(LiveData<List<Movie>> liveData) {
                callback.onResponse(liveData);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }
}
