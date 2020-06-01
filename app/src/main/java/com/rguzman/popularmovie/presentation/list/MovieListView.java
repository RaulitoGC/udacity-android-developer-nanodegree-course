package com.rguzman.popularmovie.presentation.list;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;

public interface MovieListView {

    void loadList(List<Movie> movies);

    Context context();

    void showError(String message);

    void showEmptyList();
}
