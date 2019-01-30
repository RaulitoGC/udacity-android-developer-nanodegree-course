package com.rguzman.popularmovie.domain.usecase;

import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;

import javax.inject.Inject;

public class GetReviewsByMovie {

    private final MovieDataSource repository;

    @Inject
    public GetReviewsByMovie(MovieDataSource repository) {
        this.repository = repository;
    }
}
