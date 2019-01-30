package com.rguzman.popularmovie.data.repository.datasource;


import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.usecase.GetFavoriteMovies;
import com.rguzman.popularmovie.domain.usecase.GetMovieById;
import com.rguzman.popularmovie.domain.usecase.GetPopularMovies;
import com.rguzman.popularmovie.domain.usecase.GetTopRatedMovies;

import java.util.List;

public interface MovieDataSource {

    void loadPopularMovies(GetPopularMovies.Callback<List<Movie>> callback);

    void loadTopRatedMovies(GetTopRatedMovies.Callback<List<Movie>> callback);

    LiveData<List<Movie>> loadFavoritesMovies();

    LiveData<Movie> loadMovieById(int movieId);

    void markMovieAsFavorite(int movieId);

    void unmarkMovieAsFavorite(int movieId);

}
