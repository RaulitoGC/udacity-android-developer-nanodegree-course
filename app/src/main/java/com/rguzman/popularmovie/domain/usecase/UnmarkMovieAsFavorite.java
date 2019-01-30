package com.rguzman.popularmovie.domain.usecase;

import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;

import javax.inject.Inject;

public class UnmarkMovieAsFavorite extends UseCase<Integer, Void> {

    private final MovieDataSource repository;

    @Inject
    public UnmarkMovieAsFavorite(MovieDataSource repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Integer params, Callback<Void> callback) {
        this.repository.unmarkMovieAsFavorite(params);
    }
}
