package com.rguzman.popularmovie.presentation.detail;

import android.content.Context;

import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.model.Review;
import com.rguzman.popularmovie.domain.model.Video;

import java.util.List;

public interface MovieDetailView {

    Context context();

    void showError(String message);

    void showMovie(Movie movie);

    void loadVideosByMovie(List<Video> videos);

    void loadReviewByMovie(List<Review> reviews);

}
