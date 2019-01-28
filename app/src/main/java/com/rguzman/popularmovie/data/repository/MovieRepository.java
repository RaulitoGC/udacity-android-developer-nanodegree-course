package com.rguzman.popularmovie.data.repository;


import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;
import com.rguzman.popularmovie.data.repository.datasource.disk.DiskDataSource;
import com.rguzman.popularmovie.data.repository.datasource.network.MovieNetworkDataSource;
import com.rguzman.popularmovie.data.repository.datasource.network.NetworkDataSource;
import com.rguzman.popularmovie.domain.model.Movie;

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
    public LiveData<List<Movie>> loadPopularMovies() {

        networkDataSource.loadPopularMovies(new MovieNetworkDataSource.MovieNetworkCallback() {
            @Override
            public void onResponse(LiveData<List<Movie>> liveData) {
                Timber.d(" LIST SAVE DATA FROM NETWORK TO DB");
                List<Movie> movies = liveData.getValue();
                diskDataSource.savePopularMovies(movies);
            }

            @Override
            public void onError(Exception exception) {

            }
        });

        return diskDataSource.loadPopularMovies();
    }

    @Override
    public LiveData<List<Movie>> loadTopRatedMovies() {
        networkDataSource.loadTopRatedMovies(new MovieNetworkDataSource.MovieNetworkCallback() {
            @Override
            public void onResponse(LiveData<List<Movie>> liveData) {
                Timber.d(" LIST SAVE DATA FROM NETWORK TO DB");
                List<Movie> movies = liveData.getValue();
                diskDataSource.saveTopRatedMovies(movies);
            }

            @Override
            public void onError(Exception exception) {

            }
        });

        return diskDataSource.loadTopRatedMovies();

    }

    @Override
    public LiveData<List<Movie>> loadFavoritesMovies() {
        return diskDataSource.loadFavoritesMovies();
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
