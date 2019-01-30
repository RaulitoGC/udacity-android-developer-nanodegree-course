package com.rguzman.popularmovie.domain.usecase;

import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;

import javax.inject.Inject;

public class MarkMovieAsFavorite extends UseCase<Integer, Void> {

    private final MovieDataSource repository;

    @Inject
    public MarkMovieAsFavorite(MovieDataSource repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Integer params, Callback<Void> callback) {
        this.repository.markMovieAsFavorite(params);
    }
}
