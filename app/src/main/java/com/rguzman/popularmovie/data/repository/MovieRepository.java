package com.rguzman.popularmovie.data.repository;


import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.data.net.NetworkCallback;
import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;
import com.rguzman.popularmovie.data.repository.datasource.disk.DiskDataSource;
import com.rguzman.popularmovie.data.repository.datasource.network.NetworkDataSource;
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.model.Review;
import com.rguzman.popularmovie.domain.model.Video;
import com.rguzman.popularmovie.domain.usecase.GetPopularMovies;
import com.rguzman.popularmovie.domain.usecase.GetReviewsByMovie;
import com.rguzman.popularmovie.domain.usecase.GetTopRatedMovies;
import com.rguzman.popularmovie.domain.usecase.GetVideosByMovie;
import com.rguzman.popularmovie.domain.usecase.UseCase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;


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
    public void loadPopularMovies(boolean forceUpdate, GetPopularMovies.Callback<List<Movie>> callback) {
        Timber.d(" force update" + forceUpdate);
        if (forceUpdate) {
            callback.onDiskResponse(diskDataSource.loadPopularMovies());
        }

        networkDataSource.loadPopularMovies(new NetworkCallback<List<Movie>>() {

            @Override
            public void onResponse(LiveData<List<Movie>> liveData) {
                callback.onNetworkResponse(liveData);
                List<Movie> movies = liveData.getValue();
                diskDataSource.savePopularMovies(movies);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }

    @Override
    public void loadTopRatedMovies(boolean forceUpdate, GetTopRatedMovies.Callback<List<Movie>> callback) {

        if (forceUpdate) {
            callback.onDiskResponse(diskDataSource.loadTopRatedMovies());
        }

        networkDataSource.loadTopRatedMovies(new NetworkCallback<List<Movie>>() {
            @Override
            public void onResponse(LiveData<List<Movie>> liveData) {
                callback.onNetworkResponse(liveData);
                List<Movie> movies = liveData.getValue();
                diskDataSource.saveTopRatedMovies(movies);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
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

    @Override
    public void loadVideosByMovie(int id, GetVideosByMovie.Callback<List<Video>> callback) {
        this.networkDataSource.loadVideosByMovie(id, new NetworkCallback<List<Video>>() {
            @Override
            public void onResponse(LiveData<List<Video>> liveData) {
                callback.onNetworkResponse(liveData);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }

    @Override
    public void loadReviewsByMovie(int id, GetReviewsByMovie.Callback<List<Review>> callback) {
        this.networkDataSource.loadReviewsByMovie(id, new NetworkCallback<List<Review>>() {
            @Override
            public void onResponse(LiveData<List<Review>> liveData) {
                callback.onNetworkResponse(liveData);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }
}
