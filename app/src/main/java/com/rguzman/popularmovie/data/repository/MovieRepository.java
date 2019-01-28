package com.rguzman.popularmovie.data.repository;


import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.data.exception.EmptyMovieListException;
import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;
import com.rguzman.popularmovie.data.repository.datasource.disk.DiskDataSource;
import com.rguzman.popularmovie.data.repository.datasource.network.NetworkDataSource;
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.usecase.GetFavoriteMovies;
import com.rguzman.popularmovie.domain.usecase.GetPopularMovies;
import com.rguzman.popularmovie.domain.usecase.GetTopRatedMovies;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class MovieRepository implements MovieDataSource {

    private final NetworkDataSource networkDataSource;
    private final DiskDataSource diskDataSource;


    @Inject
    public MovieRepository(NetworkDataSource networkDataSource, DiskDataSource diskDataSource) {
        this.networkDataSource = networkDataSource;
        this.diskDataSource = diskDataSource;
    }

    @Override
    public void loadPopularMovies(GetPopularMovies.Callback<List<Movie>> callback) {
        networkDataSource.loadPopularMovies(new GetPopularMovies.Callback<List<Movie>>() {
            @Override
            public void onResponse(LiveData<List<Movie>> liveData) {
                List<Movie> movies = liveData.getValue();
                diskDataSource.savePopularMovies(movies);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });

        callback.onResponse(diskDataSource.loadPopularMovies());
    }

    @Override
    public void loadTopRatedMovies(GetTopRatedMovies.Callback<List<Movie>> callback) {
        networkDataSource.loadTopRatedMovies(new GetTopRatedMovies.Callback<List<Movie>>() {
            @Override
            public void onResponse(LiveData<List<Movie>> liveData) {
                List<Movie> movies = liveData.getValue();
                diskDataSource.saveTopRatedMovies(movies);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
        callback.onResponse(diskDataSource.loadTopRatedMovies());
    }

    @Override
    public void loadFavoritesMovies(GetFavoriteMovies.Callback<List<Movie>> callback) {
        LiveData<List<Movie>> liveData = diskDataSource.loadFavoritesMovies();
        if (liveData != null && liveData.getValue() != null) {
            if (liveData.getValue().isEmpty()) {
                callback.onError(new EmptyMovieListException("Favorites"));
            } else {
                callback.onResponse(liveData);
            }
        } else {
            callback.onError(new EmptyMovieListException("Favorites"));
        }
    }

    @Override
    public void saveFavoriteMovie(Movie movie) {
        diskDataSource.saveFavoriteMovie(movie);
    }

    @Override
    public void unSaveFavoriteMovie(Movie movie) {
        diskDataSource.unSaveFavoriteMovie(movie);
    }
}
