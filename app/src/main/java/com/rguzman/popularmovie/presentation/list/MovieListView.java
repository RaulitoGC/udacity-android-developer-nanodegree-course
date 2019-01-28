package com.rguzman.popularmovie.presentation.list;

import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;

public interface MovieListView {

    void loadMovies(List<Movie> movies);

    void addObserver(LiveData<List<Movie>> liveData);

    void removeObserver(LiveData<List<Movie>> liveData);
}
