package com.rguzman.popularmovie.data.repository;


import android.arch.lifecycle.LiveData;

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
    public LiveData<List<Movie>> loadFavoritesMovies() {
        return diskDataSource.loadFavoritesMovies();
    }

    @Override
    public LiveData<Movie> loadMovieById(int movieId) {
        return this.diskDataSource.loadMovieById(movieId);
    }

    @Override
    public void markMovieAsFavorite(int movieId) {
        this.diskDataSource.markMovieAsFavorite(movieId);
    }

    @Override
    public void unmarkMovieAsFavorite(int movieId) {
        this.diskDataSource.unmarkMovieAsFavorite(movieId);
    }
}
