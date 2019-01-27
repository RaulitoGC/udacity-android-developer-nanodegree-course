package com.rguzman.popularmovie.domain.usecase;

import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;
import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class GetMoviesUseCase extends UseCase<String, List<Movie>> {

    private final MovieDataSource repository;

    @Inject
    public GetMoviesUseCase(MovieDataSource repository) {
        this.repository = repository;
    }


    @Override
    public LiveData<List<Movie>> execute(String params) {
        return repository.getMovies(params);
    }
}
