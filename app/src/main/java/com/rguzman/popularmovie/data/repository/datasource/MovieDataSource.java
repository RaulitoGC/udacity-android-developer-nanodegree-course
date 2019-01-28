package com.rguzman.popularmovie.data.repository.datasource;


import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;

public interface MovieDataSource {

    LiveData<List<Movie>> loadPopularMovies();

    LiveData<List<Movie>> loadTopRatedMovies();

    LiveData<List<Movie>> loadFavoritesMovies();

    void saveFavoriteMovie(Movie movie);

    void unSaveFavoriteMovie(Movie movie);

}
