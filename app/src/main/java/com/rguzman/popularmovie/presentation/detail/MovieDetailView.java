package com.rguzman.popularmovie.presentation.detail;

import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.domain.model.Movie;

public interface MovieDetailView {

    void loadVideosByMovie();

    void loadReviewByMovie();

    void addObserver(LiveData<Movie> liveData);

    void removeObserver(LiveData<Movie> liveData);
}
