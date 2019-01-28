package com.rguzman.popularmovie.data.repository.datasource.network;

import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.usecase.GetPopularMovies;

import java.util.List;

public interface NetworkDataSource {

    void loadPopularMovies(GetPopularMovies.Callback<List<Movie>> callback);

    void loadTopRatedMovies(GetPopularMovies.Callback<List<Movie>> callback);
}
