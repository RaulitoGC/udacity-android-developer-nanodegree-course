package com.rguzman.popularmovie.data.repository.datasource;


import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.model.Review;
import com.rguzman.popularmovie.domain.model.Video;
import com.rguzman.popularmovie.domain.usecase.GetPopularMovies;
import com.rguzman.popularmovie.domain.usecase.GetReviewsByMovie;
import com.rguzman.popularmovie.domain.usecase.GetTopRatedMovies;
import com.rguzman.popularmovie.domain.usecase.GetVideosByMovie;

import java.util.List;

public interface MovieDataSource {

    void loadPopularMovies(boolean forceUpdate, GetPopularMovies.Callback<List<Movie>> callback);

    void loadTopRatedMovies(boolean forceUpdate, GetTopRatedMovies.Callback<List<Movie>> callback);

    LiveData<List<Movie>> loadFavoritesMovies();

    LiveData<Movie> loadMovieById(int movieId);

    void markMovieAsFavorite(int movieId);

    void unmarkMovieAsFavorite(int movieId);

    void loadVideosByMovie(int id, GetVideosByMovie.Callback<List<Video>> callback);

    void loadReviewsByMovie(int id, GetReviewsByMovie.Callback<List<Review>> callback);

}
