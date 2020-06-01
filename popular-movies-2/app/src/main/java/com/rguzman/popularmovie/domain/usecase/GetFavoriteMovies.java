package com.rguzman.popularmovie.domain.usecase;

import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;
import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;

import javax.inject.Inject;

public class GetFavoriteMovies extends UseCase<Void, List<Movie>> {

    private final MovieDataSource repository;

    @Inject
    public GetFavoriteMovies(MovieDataSource repository) {
        this.repository = repository;
    }

    @Override
    public void execute(boolean forceUpdate, Void params, Callback<List<Movie>> callback) {
        callback.onDiskResponse(this.repository.loadFavoritesMovies());
    }
}