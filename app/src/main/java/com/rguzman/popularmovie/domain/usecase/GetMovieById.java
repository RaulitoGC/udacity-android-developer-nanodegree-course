package com.rguzman.popularmovie.domain.usecase;

import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;
import com.rguzman.popularmovie.domain.model.Movie;

import javax.inject.Inject;

public class GetMovieById extends UseCase<Integer, Movie> {

    private final MovieDataSource repository;

    @Inject
    public GetMovieById(MovieDataSource repository) {
        this.repository = repository;
    }

    @Override
    public void execute(boolean forceUpdate, Integer params, Callback<Movie> callback) {
        callback.onDiskResponse(repository.loadMovieById(params));
    }
}
