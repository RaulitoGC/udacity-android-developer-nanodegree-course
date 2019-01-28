package com.rguzman.popularmovie.domain.usecase;

import android.arch.lifecycle.LiveData;

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
    public LiveData<List<Movie>> execute(Void params) {
        return repository.loadFavoritesMovies();
    }
}