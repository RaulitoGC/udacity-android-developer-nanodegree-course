package com.rguzman.popularmovie.data.repository.datasource.network;

public interface NetworkDataSource {
    void loadMovies(String path, MovieNetworkDataSource.MovieNetworkCallback callback);
}
