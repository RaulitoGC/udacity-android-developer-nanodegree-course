package com.rguzman.popularmovie.domain.usecase;

import com.rguzman.popularmovie.data.repository.MovieRepository;

import javax.inject.Inject;

public class GetVideosByMovie {

    private final MovieRepository repository;

    @Inject
    public GetVideosByMovie(MovieRepository repository) {
        this.repository = repository;
    }

}
