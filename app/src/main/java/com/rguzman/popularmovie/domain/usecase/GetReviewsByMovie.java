package com.rguzman.popularmovie.domain.usecase;

import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;
import com.rguzman.popularmovie.domain.model.Review;

import java.util.List;

import javax.inject.Inject;

public class GetReviewsByMovie extends UseCase<Integer, List<Review>> {

    private final MovieDataSource repository;

    @Inject
    public GetReviewsByMovie(MovieDataSource repository) {
        this.repository = repository;
    }

    @Override
    public void execute(boolean forceUpdate, Integer params, Callback<List<Review>> callback) {
        this.repository.loadReviewsByMovie(params, new Callback<List<Review>>() {
            @Override
            public void onNetworkResponse(LiveData<List<Review>> liveData) {
                callback.onNetworkResponse(liveData);
            }

            @Override
            public void onDiskResponse(LiveData<List<Review>> liveData) {

            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }
}
