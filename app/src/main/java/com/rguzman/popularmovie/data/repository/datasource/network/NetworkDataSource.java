package com.rguzman.popularmovie.data.repository.datasource.network;

import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;

public interface NetworkDataSource {

    void loadPopularMovies(MovieNetworkDataSource.MovieNetworkCallback callback);

    void loadTopRatedMovies(MovieNetworkDataSource.MovieNetworkCallback callback);
}
