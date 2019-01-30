package com.rguzman.popularmovie.data.repository.datasource.disk;

import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.usecase.GetFavoriteMovies;

import java.util.List;

public interface DiskDataSource {

    void savePopularMovies(List<Movie> movies);

    void saveTopRatedMovies(List<Movie> movies);

    LiveData<List<Movie>> loadPopularMovies();

    LiveData<List<Movie>> loadTopRatedMovies();

    LiveData<List<Movie>> loadFavoritesMovies();

    void markMovieAsFavorite(int movieId);

    void unmarkMovieAsFavorite(int movieId);

    LiveData<Movie> loadMovieById(int movieId);

}
