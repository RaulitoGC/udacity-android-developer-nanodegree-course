package com.rguzman.popularmovie.data.repository.datasource.network;

import com.rguzman.popularmovie.data.net.NetworkCallback;
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.model.Review;
import com.rguzman.popularmovie.domain.model.Video;

import java.util.List;

public interface NetworkDataSource {

    void loadPopularMovies(NetworkCallback<List<Movie>> callback);

    void loadTopRatedMovies(NetworkCallback<List<Movie>> callback);

    void loadVideosByMovie(int id, NetworkCallback<List<Video>> callback);

    void loadReviewsByMovie(int id, NetworkCallback<List<Review>> callback);

}

