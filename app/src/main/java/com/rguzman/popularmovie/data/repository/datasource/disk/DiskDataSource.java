package com.rguzman.popularmovie.data.repository.datasource.disk;

import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.usecase.GetPopularMovies;

import java.util.List;

public interface DiskDataSource {

    void savePopularMovies(List<Movie> movies);

    void saveTopRatedMovies(List<Movie> movies);

    LiveData<List<Movie>> loadPopularMovies();

    LiveData<List<Movie>> loadTopRatedMovies();

    LiveData<List<Movie>> loadFavoritesMovies();

    void saveFavoriteMovie(Movie movie);

    void unSaveFavoriteMovie(Movie movie);

}
