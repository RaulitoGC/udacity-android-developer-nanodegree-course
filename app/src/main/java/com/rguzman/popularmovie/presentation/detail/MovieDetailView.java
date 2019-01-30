package com.rguzman.popularmovie.presentation.detail;

import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.domain.model.Movie;

public interface MovieDetailView {

    void showMovie(Movie movie);

    void loadVideosByMovie();

    void loadReviewByMovie();

}
