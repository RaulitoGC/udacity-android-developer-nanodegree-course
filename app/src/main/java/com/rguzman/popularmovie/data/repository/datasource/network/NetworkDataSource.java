package com.rguzman.popularmovie.data.repository.datasource.network;

import com.rguzman.popularmovie.data.net.NetworkCallback;
import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;

public interface NetworkDataSource {

    void loadPopularMovies(NetworkCallback<List<Movie>> callback);

    void loadTopRatedMovies(NetworkCallback<List<Movie>> callback);

}

