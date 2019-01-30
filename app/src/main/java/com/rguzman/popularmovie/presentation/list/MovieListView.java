package com.rguzman.popularmovie.presentation.list;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;

public interface MovieListView {

    Context context();

    void loadMovies(List<Movie> movies);

    void showError(String message);

    void addObserver(LiveData<List<Movie>> liveData, String message);

    void removeObserver(LiveData<List<Movie>> liveData);
}
